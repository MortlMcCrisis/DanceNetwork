import 'package:flutter/material.dart';

class CustomLoadingBuilder extends StatelessWidget {
  final Widget child;
  final ImageChunkEvent? loadingProgress;

  const CustomLoadingBuilder({
    super.key,
    required this.child,
    required this.loadingProgress,
  });

  @override
  Widget build(BuildContext context) {
    if (loadingProgress == null) {
      return child; // Image loaded successfully
    }
    return const SizedBox(
      width: 120,
      height: 120,
      child: Center(
        child: CircularProgressIndicator(),
      ),
    );
  }
}

class CustomErrorBuilder extends StatelessWidget {
  const CustomErrorBuilder({
    super.key,
  });

  @override
  Widget build(BuildContext context) {
    return const SizedBox(
      width: 120,
      height: 120,
      child: Center(
        child: Icon(Icons.error, color: Colors.red),
      ),
    );
  }
}
