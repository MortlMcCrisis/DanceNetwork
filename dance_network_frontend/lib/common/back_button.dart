import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class CustomBackButton extends StatelessWidget {
  final String route;

  const CustomBackButton({super.key, required this.route});

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.only(left: 16.0, top: 12.0),
      child: Container(
        width: 56.0,
        height: 56.0,
        decoration: BoxDecoration(
          color: Colors.black.withOpacity(0.5),
          shape: BoxShape.circle,
        ),
        child: Center(
          child: IconButton(
            icon: const Icon(Icons.arrow_back),
            color: Colors.white,
            iconSize: 24.0,
            onPressed: () {
              context.go(route);
            },
          ),
        ),
      ),
    );
  }
}
