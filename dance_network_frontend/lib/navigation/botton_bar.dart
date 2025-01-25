import 'package:dance_network_frontend/main.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class CustomBottomNavigationBar extends StatelessWidget {
  final ValueChanged<int> onItemTapped;

  const CustomBottomNavigationBar({
    super.key,
    required this.onItemTapped,
  });

  @override
  Widget build(BuildContext context) {
    final navigationState = Provider.of<NavigationState>(context);

    return BottomNavigationBar(
      items: const <BottomNavigationBarItem>[
        BottomNavigationBarItem(icon: Icon(Icons.dynamic_feed), label: 'Newsfeed'),
        BottomNavigationBarItem(icon: Icon(Icons.festival), label: 'Events'),
        BottomNavigationBarItem(icon: Icon(Icons.confirmation_num), label: 'Tickets'),
      ],
      currentIndex: navigationState.selectedIndex,
      selectedItemColor: AppThemes.primary,
      unselectedItemColor: AppThemes.generateGradient(AppThemes.primary)[5],
      showSelectedLabels: false,
      showUnselectedLabels: false,
      onTap: (index) {
        navigationState.updateIndex(index);
        onItemTapped(index);
      },
    );
  }
}
