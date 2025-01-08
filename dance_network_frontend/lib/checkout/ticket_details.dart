import 'package:flutter/material.dart';
import 'package:dance_network_frontend/util/theme.dart';

class TicketDetailsWidget extends StatefulWidget {
  final int ticketIndex;
  final String ticketTypeName;
  final ValueNotifier<List<Map<String, dynamic>>> ticketDetailsNotifier;
  final Function(int, Map<String, dynamic>) onUpdateTicket;

  const TicketDetailsWidget({
    super.key,
    required this.ticketIndex,
    required this.ticketTypeName,
    required this.ticketDetailsNotifier,
    required this.onUpdateTicket,
  });

  @override
  TicketDetailsWidgetState createState() => TicketDetailsWidgetState();
}

class TicketDetailsWidgetState extends State<TicketDetailsWidget> {
  String? selectedGender;
  String? selectedRole;

  TextEditingController firstNameController = TextEditingController();
  TextEditingController lastNameController = TextEditingController();
  TextEditingController addressController = TextEditingController();
  TextEditingController countryController = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController phoneController = TextEditingController();

  @override
  void initState() {
    super.initState();
    if (widget.ticketIndex >= 0 && widget.ticketIndex < widget.ticketDetailsNotifier.value.length) {
      var ticket = widget.ticketDetailsNotifier.value[widget.ticketIndex];
      firstNameController.text = ticket['firstName'] ?? '';
      lastNameController.text = ticket['lastName'] ?? '';
      addressController.text = ticket['address'] ?? '';
      countryController.text = ticket['country'] ?? '';
      emailController.text = ticket['email'] ?? '';
      phoneController.text = ticket['phone'] ?? '';
      selectedGender = ticket['gender'];
      selectedRole = ticket['role'];
    }
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
              Text(
                'Details für Ticket: ${widget.ticketTypeName}',
                style: Theme.of(context).textTheme.titleLarge,
              ),
              const SizedBox(height: 16.0),
              TextFormField(
                controller: firstNameController,
                decoration: const InputDecoration(
                  labelText: 'First name',
                  border: OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 16.0),
              TextFormField(
                controller: lastNameController,
                decoration: const InputDecoration(
                  labelText: 'Last name',
                  border: OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 16.0),
              TextFormField(
                controller: addressController,
                decoration: InputDecoration(
                  labelText: 'Address',
                  border: OutlineInputBorder(
                    borderRadius: BorderRadius.circular(AppThemes.borderRadiusSmall),
                  ),
                ),
              ),
              const SizedBox(height: 16.0),
              TextFormField(
                controller: countryController,
                decoration: const InputDecoration(
                  labelText: 'Country',
                  border: OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 16.0),

              // Gender Radio Buttons
              const Text(
                'Gender',
                style: TextStyle(
                  fontSize: 16.0,
                  fontWeight: FontWeight.bold,
                ),
              ),
              Column(
                children: [
                  RadioListTile<String>(
                    value: 'male',
                    groupValue: selectedGender,
                    title: const Text('Male'),
                    activeColor: Colors.green, // Farbe des ausgewählten Radio-Buttons
                    onChanged: (value) {
                      setState(() {
                        selectedGender = value;
                      });
                    },
                  ),
                  RadioListTile<String>(
                    value: 'female',
                    groupValue: selectedGender,
                    title: const Text('Female'),
                    onChanged: (value) {
                      setState(() {
                        selectedGender = value;
                      });
                    },
                  ),
                  RadioListTile<String>(
                    value: 'other',
                    groupValue: selectedGender,
                    title: const Text('Other'),
                    onChanged: (value) {
                      setState(() {
                        selectedGender = value;
                      });
                    },
                  ),
                ],
              ),
              const SizedBox(height: 16.0),
              // Role Radio Buttons
              const Text(
                'Role',
                style: TextStyle(
                  fontSize: 16.0,
                  fontWeight: FontWeight.bold,
                ),
              ),
              Column(
                children: [
                  RadioListTile<String>(
                    value: 'leader',
                    groupValue: selectedRole,
                    title: const Text('Leader'),
                    onChanged: (value) {
                      setState(() {
                        selectedRole = value;
                      });
                    },
                  ),
                  RadioListTile<String>(
                    value: 'follower',
                    groupValue: selectedRole,
                    title: const Text('Follower'),
                    onChanged: (value) {
                      setState(() {
                        selectedRole = value;
                      });
                    },
                  ),
                  RadioListTile<String>(
                    value: 'both',
                    groupValue: selectedRole,
                    title: const Text('Both'),
                    onChanged: (value) {
                      setState(() {
                        selectedRole = value;
                      });
                    },
                  ),
                ],
              ),
              const SizedBox(height: 16.0),
              TextFormField(
                controller: emailController,
                decoration: const InputDecoration(
                  labelText: 'E-Mail',
                  border: OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 16.0),
              TextFormField(
                controller: phoneController,
                decoration: const InputDecoration(
                  labelText: 'Phone',
                  border: OutlineInputBorder(),
                ),
              ),
              const SizedBox(height: 16.0),
              ElevatedButton(
                onPressed: () {

                  final updatedTicket = {
                    'firstName': firstNameController.text,
                    'lastName': lastNameController.text,
                    'address': addressController.text,
                    'country': countryController.text,
                    'gender': selectedGender,
                    'role': selectedRole,
                    'email': emailController.text,
                    'phone': phoneController.text,
                  };

                  widget.onUpdateTicket(widget.ticketIndex, updatedTicket);

                  Navigator.pop(context);
                },
                child: const Text('Speichern'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
