import 'package:dance_network_frontend/navigation/top_bar.dart';
import 'package:flutter/material.dart';

class WebTopBar extends StatelessWidget {
  final SearchField searchField;
  final AuthIcon authIcon;

  const WebTopBar({
    super.key,
    required this.searchField,
    required this.authIcon,
  });

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.start,
      children: [
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
