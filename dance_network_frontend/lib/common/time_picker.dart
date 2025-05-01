import 'package:dance_network_frontend/util/device_utils.dart';
import 'package:flutter/material.dart';

class TimePickerUtils {
  static Future<TimeOfDay?> pickTime({
    required BuildContext context,
    TimeOfDay? initialTime,
  }) {
    return showTimePicker(
      context: context,
      initialTime: initialTime ?? TimeOfDay.now(),
      initialEntryMode: DeviceUtils.isMobileDevice()
          ? TimePickerEntryMode.dial
          : TimePickerEntryMode.inputOnly,
      builder: (BuildContext context, Widget? child) {
        return MediaQuery(
          data: MediaQuery.of(context).copyWith(alwaysUse24HourFormat: true),
          child: child!,
        );
      },
    );
  }
}