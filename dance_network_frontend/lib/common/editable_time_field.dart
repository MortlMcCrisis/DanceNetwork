import 'package:dance_network_frontend/theme.dart';
import 'package:flutter/material.dart';

class TimePickerLabel extends StatefulWidget {
  final TimeOfDay? initialTime;
  final ValueChanged<TimeOfDay> onSubmitted;
  final TextStyle? style;
  final bool active;

  const TimePickerLabel({
    super.key,
    required this.initialTime,
    required this.onSubmitted,
    required this.active,
    this.style,
  });

  @override
  State<TimePickerLabel> createState() => _TimePickerLabelState();
}

class _TimePickerLabelState extends State<TimePickerLabel> {
  late TimeOfDay selectedTime;
  bool _isHovering = false;

  @override
  void initState() {
    super.initState();
    selectedTime = widget.initialTime ?? TimeOfDay.now();
  }

  Future<void> _selectTime() async {
    final picked = await showTimePicker(
      context: context,
      initialTime: selectedTime,
      initialEntryMode: TimePickerEntryMode.dial,
      builder: (context, child) {
        return MediaQuery(
          data: MediaQuery.of(context).copyWith(alwaysUse24HourFormat: true),
          child: child!,
        );
      },
    );

    if (picked != null && picked != selectedTime) {
      setState(() => selectedTime = picked);
      widget.onSubmitted(picked);
    }
  }

  @override
  Widget build(BuildContext context) {
    final timeText = selectedTime.format(context);

    Widget textField = Text(
      timeText,
      style: widget.style ?? Theme.of(context).textTheme.bodyMedium,
    );

    if(!widget.active){
      return textField;
    }

    return MouseRegion(
      onEnter: (_) => setState(() => _isHovering = true),
      onExit: (_) => setState(() => _isHovering = false),
      child: GestureDetector(
        onTap: _selectTime,
        child: Container(
          padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 2),
          decoration: BoxDecoration(
            color: _isHovering ? AppThemes.generateGradient(AppThemes.black)[6].withOpacity(0.1) : Colors.transparent,
            borderRadius: BorderRadius.circular(4),
          ),
          child: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              textField,
              const SizedBox(width: 6),
              Icon(Icons.edit, size: 18, color: AppThemes.generateGradient(AppThemes.black)[7]),
            ],
          ),
        ),
      ),
    );
  }
}
