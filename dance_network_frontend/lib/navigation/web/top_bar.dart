import 'package:dance_network_frontend/navigation/top_bar.dart';
import 'package:flutter/material.dart';

class WebTopBar extends StatelessWidget {
  final String title;
  final SearchField searchField;
  final AuthIcon authIcon;

  const WebTopBar({
    super.key,
    required this.title,
    required this.searchField,
    required this.authIcon,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.start,
      children: [
        Expanded(
          child: Text(
            title,
            style: const TextStyle(
                fontSize: 20.0,
                fontWeight: FontWeight.bold
            ),
          ),
        ),
        Align(
          alignment: Alignment.center,
          child: SizedBox(
            width: MediaQuery.of(context).size.width * 0.45,
            height: 35.0,
            child: searchField,
          ),
        ),
        Expanded(
          child: Align(
            alignment: Alignment.centerRight,
            child: authIcon,
          ),
        ),
      ],
    );
  }
}
