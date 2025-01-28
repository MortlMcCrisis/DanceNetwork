import 'package:dance_network_frontend/util/device_utils.dart';
import 'package:flutter/material.dart';

class OnMobile extends StatelessWidget {
  final Widget mobileChild;

  const OnMobile(
      this.mobileChild, {
        super.key,
      });

  @override
  Widget build(BuildContext context) {
    return DeviceUtils.isMobileDevice() ? mobileChild : const SizedBox.shrink();
  }
}
