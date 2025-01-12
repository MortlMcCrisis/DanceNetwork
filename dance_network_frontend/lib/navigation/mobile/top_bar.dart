import 'package:dance_network_frontend/navigation/top_bar.dart';
import 'package:flutter/material.dart';

class MobileTopBar extends StatelessWidget {
  final String title;
  final SearchField searchField;
  final AuthIcon authIcon;

  const MobileTopBar({
    super.key,
    required this.title,
    required this.searchField,
    required this.authIcon,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Expanded(
          child: SizedBox(
            height: 40.0,
            child: searchField,
          ),
        ),
        const SizedBox(width: 8.0),
        authIcon,
      ],
    );
  }
}
