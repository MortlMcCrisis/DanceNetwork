import 'package:dance_network_frontend/util/screen_utils.dart';
import 'package:flutter/material.dart';

class MaxSizedContainer extends StatelessWidget {
  final Widget Function(BuildContext context, BoxConstraints constraints) builder;

  const MaxSizedContainer({
    super.key,
    required this.builder,
  });

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (context, constraints) {
        return Center(
          child: SizedBox(
            width: constraints.maxWidth > ScreenUtils.centerContainerMaxSize
                ? ScreenUtils.centerContainerMaxSize
                : constraints.maxWidth,
            child: Padding(
              padding: const EdgeInsets.all(8.0),
              child: builder(context, constraints),
            ),
          ),
        );
      },
    );
  }
}

