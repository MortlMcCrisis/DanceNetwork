import 'package:dance_network_frontend/util/theme.dart';
import 'package:flutter/material.dart';

class CustomBottomNavigationBar extends StatelessWidget {
  final int currentIndex;
  final ValueChanged<int> onItemTapped;

  const CustomBottomNavigationBar({
    super.key,
    required this.currentIndex,
    required this.onItemTapped,
  });

  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      items: const <BottomNavigationBarItem>[
        BottomNavigationBarItem(icon: Icon(Icons.dynamic_feed), label: 'Newsfeed'),
        BottomNavigationBarItem(icon: Icon(Icons.festival), label: 'Events'),
        BottomNavigationBarItem(icon: Icon(Icons.confirmation_num), label: 'Tickets'),
        BottomNavigationBarItem(icon: Icon(Icons.account_circle), label: 'Profile'),
      ],
      currentIndex: currentIndex,
      selectedItemColor: AppThemes.primary,
      unselectedItemColor: AppThemes.generateGradient(AppThemes.primary)[5],
      showSelectedLabels: false,
      showUnselectedLabels: false,
      onTap: onItemTapped,
    );
  }
}
