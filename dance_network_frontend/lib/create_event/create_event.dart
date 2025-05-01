import 'package:dance_network_frontend/common/time_picker.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:dance_network_frontend/util/api_service.dart';
import 'package:dance_network_frontend/util/token_storage.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';
import 'package:intl/intl.dart';

import '../model/event.dart';

class CreateEventPage extends StatefulWidget {

  const CreateEventPage({
    super.key,
  });

  @override
  CreateEventPageState createState() => CreateEventPageState();
}

class CreateEventPageState extends State<CreateEventPage> {
  DateTime? selectedStartDate;
  DateTime? selectedEndDate;
  TimeOfDay? selectedTime;
  bool multipleDays = false;

  TextEditingController nameController = TextEditingController();
  TextEditingController startDateController = TextEditingController();
  TextEditingController timeController = TextEditingController();
  TextEditingController endDateController = TextEditingController();

  Future<void> _selectStartDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: selectedStartDate ?? DateTime.now(),
      firstDate: DateTime(2000),
      lastDate: DateTime(2101),
    );
    if (picked != null && picked != selectedStartDate) {
      setState(() {
        selectedStartDate = picked;
        startDateController.text = DateFormat('yyyy-MM-dd').format(picked);
      });
    }
  }

  Future<void> _selectEndDate(BuildContext context) async {
    final DateTime? picked = await showDatePicker(
      context: context,
      initialDate: selectedEndDate ?? DateTime.now(),
      firstDate: DateTime(2000),
      lastDate: DateTime(2101),
    );
    if (picked != null && picked != selectedEndDate) {
      setState(() {
        selectedEndDate = picked;
        endDateController.text = DateFormat('yyyy-MM-dd').format(picked);
      });
    }
  }

  Future<void> _selectTime(BuildContext context) async {
    final TimeOfDay? picked = await TimePickerUtils.pickTime(
        context: context,
        initialTime: selectedTime ?? TimeOfDay.now());

    if (picked != null && picked != selectedTime) {
      setState(() {
        selectedTime = picked;
        timeController.text = picked.format(context);
      });
    }
  }

  Future<Event> createEvent() async {
    final token = await TokenStorage().getToken();

    final event = await ApiService().post(
      endpoint: ApiService.eventClosedEndpoint,
      content: {
        'name': nameController.text,
        'startDate': startDateController.text,
        'startTime': convertTo24HourFormat(timeController.text),
        if (multipleDays) 'endDate': endDateController.text,
        'published': true
      },
      typeMapper: (json) => Event.fromMap(json),
      token: token,
    );

    debugPrint('$event');

    return event;
  }

  String convertTo24HourFormat(String time) {
    final inputFormat = DateFormat.jm(); // 12-hour format (e.g., 7:30 PM)
    final outputFormat = DateFormat.Hm(); // 24-hour format (e.g., 19:30)

    final parsedTime = inputFormat.parse(time); // Parse to DateTime
    return outputFormat.format(parsedTime); // Format as 24-hour time string
  }

  @override
  Widget build(BuildContext context) {
    return FractionallySizedBox(
      child: Container(
        padding: const EdgeInsets.all(16.0),
        decoration: BoxDecoration(
          color: AppThemes.white,
          borderRadius: const BorderRadius.vertical(
            top: Radius.circular(AppThemes.borderRadius),
          ),
          boxShadow: [
            BoxShadow(
              color: AppThemes.generateGradient(AppThemes.black)[2],
              blurRadius: 10.0,
            ),
          ],
        ),
        child: SingleChildScrollView(
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              const SizedBox(height: 32.0),
              Text(
                'New event',
                style: Theme
                    .of(context)
                    .textTheme
                    .titleLarge,
              ),
              const SizedBox(height: 16.0),
              TextFormField(
                controller: nameController,
                decoration: const InputDecoration(
                  labelText: 'Name*',
                  border: OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 16.0),
              GestureDetector(
                onTap: () => _selectStartDate(context),
                child: AbsorbPointer(
                  child: TextFormField(
                    controller: startDateController,
                    readOnly: true,
                    decoration: const InputDecoration(
                      labelText: 'Start date*',
                      border: OutlineInputBorder(),
                      suffixIcon: Icon(Icons.calendar_today),
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 16.0),
              GestureDetector(
                onTap: () => _selectTime(context),
                child: AbsorbPointer(
                  child: TextFormField(
                    controller: timeController,
                    readOnly: true,
                    decoration: const InputDecoration(
                      labelText: 'Start time*',
                      border: OutlineInputBorder(),
                      suffixIcon: Icon(Icons.access_time),
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 16.0),
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  const Text('Multiple days'),
                  Switch(
                    value: multipleDays,
                    onChanged: (bool value) {
                      setState(() {
                        multipleDays = value;
                        if (!multipleDays) {
                          endDateController.clear();
                          selectedEndDate = null;
                        }
                      });
                    },
                  ),
                ],
              ),
              const SizedBox(height: 16.0),
              GestureDetector(
                onTap: multipleDays ? () => _selectEndDate(context) : null,
                child: AbsorbPointer(
                  child: TextFormField(
                    controller: endDateController,
                    readOnly: true,
                    enabled: multipleDays,
                    decoration: const InputDecoration(
                      labelText: 'End date*',
                      border: OutlineInputBorder(),
                      suffixIcon: Icon(Icons.calendar_today),
                    ),
                  ),
                ),
              ),
              const SizedBox(height: 16.0),
              ElevatedButton(
                onPressed: () async {
                  final localContext = context;
                  final response = await createEvent();
                  debugPrint('$response');
                  if (!mounted){
                    debugPrint('abort');
                    return;
                  }

                  debugPrint('/events/${response.eventId}');
                  localContext.go('/events/${response.eventId}');
                },
                child: const Text('Create'),
              ),
            ],
          ),
        ),
      )
    );
  }
}
