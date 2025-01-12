import 'package:dance_network_frontend/util/device_utils.dart';
import 'package:flutter/material.dart';

class ResponsiveSwitch extends StatelessWidget {
  final Widget Function(BoxConstraints constraints) mobileWidgetBuilder;
  final Widget Function(BoxConstraints constraints) webWidgetBuilder;
  final bool Function(BuildContext context, BoxConstraints constraints) breakpoint;

  const ResponsiveSwitch({
    super.key,
    required this.mobileWidgetBuilder,
    required this.webWidgetBuilder,
    this.breakpoint = _defaultBreakpoint,
  });

  static bool _defaultBreakpoint(BuildContext context, BoxConstraints constraints) {
    return DeviceUtils.isWideScreen(context);
  }

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (context, constraints) {
        if(breakpoint(context, constraints)) {
          return Container(
            child: webWidgetBuilder(constraints),
          );
        }
        return Container(
          child: mobileWidgetBuilder(constraints),
        );
      },
    );
  }
}
