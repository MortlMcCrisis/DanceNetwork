import 'package:dance_network_frontend/theme.dart';
import 'package:flutter/material.dart';
import 'package:intl/intl.dart';

class DatePickerLabel extends StatefulWidget {
  final DateTime? initialDate;
  final ValueChanged<DateTime> onSubmitted;
  final TextStyle? style;

  const DatePickerLabel({
    super.key,
    required this.initialDate,
    required this.onSubmitted,
    this.style,
  });

  @override
  State<DatePickerLabel> createState() => _DatePickerLabelState();
}

class _DatePickerLabelState extends State<DatePickerLabel> {
  late DateTime selectedDate;
  bool _isHovering = false;

  @override
  void initState() {
    super.initState();
    selectedDate = widget.initialDate ?? DateTime.now();
  }

  Future<void> _selectDate() async {
    final picked = await showDatePicker(
      context: context,
      initialDate: selectedDate,
      firstDate: DateTime(1900),
      lastDate: DateTime(2100),
    );

    if (picked != null && picked != selectedDate) {
      setState(() => selectedDate = picked);
      widget.onSubmitted(picked);
    }
  }

  @override
  Widget build(BuildContext context) {
    final dateText = DateFormat.yMMMMd().format(selectedDate);

    return MouseRegion(
      onEnter: (_) => setState(() => _isHovering = true),
      onExit: (_) => setState(() => _isHovering = false),
      child: GestureDetector(
        onTap: _selectDate,
        child: Container(
          padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 2),
          decoration: BoxDecoration(
            color: _isHovering
                ? AppThemes.generateGradient(AppThemes.black)[6].withOpacity(0.1)
                : Colors.transparent,
            borderRadius: BorderRadius.circular(4),
          ),
          child: Row(
            mainAxisSize: MainAxisSize.min,
            children: [
              Text(
                dateText,
                style: widget.style ?? Theme.of(context).textTheme.bodyMedium,
              ),
              const SizedBox(width: 6),
              Icon(Icons.edit, size: 18, color: AppThemes.generateGradient(AppThemes.black)[7]),
            ],
          ),
        ),
      )
    );
  }
}
