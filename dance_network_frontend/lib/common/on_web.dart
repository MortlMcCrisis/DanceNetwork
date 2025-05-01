import 'package:dance_network_frontend/util/device_utils.dart';
import 'package:flutter/material.dart';

class OnWeb extends StatelessWidget {
  final Widget webChild;

  const OnWeb(
      this.webChild, {
        super.key,
      });

  @override
  Widget build(BuildContext context) {
    return DeviceUtils.isMobileDevice() ? const SizedBox.shrink() : webChild;
  }
}
