import 'package:flutter/material.dart';

class MaxSizedContainer extends StatelessWidget {
  final Widget Function(BuildContext context, BoxConstraints constraints) builder;
  final EdgeInsetsGeometry padding;

  const MaxSizedContainer({
    super.key,
    required this.builder,
    this.padding = const EdgeInsets.all(8.0),
  });

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(
      builder: (context, constraints) {
        return Center(
          child: SizedBox(
            width: constraints.maxWidth > 900 ? 900 : constraints.maxWidth,
            child: Padding(
              padding: padding,
              child: builder(context, constraints),
            ),
          ),
        );
      },
    );
  }
}

