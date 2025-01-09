import 'package:dance_network_frontend/config/theme.dart';
import 'package:flutter/material.dart';

class NavigationRailComponent extends StatelessWidget {
  final int selectedIndex;
  final ValueChanged<int> onDestinationSelected;

  const NavigationRailComponent({
    super.key,
    required this.selectedIndex,
    required this.onDestinationSelected,
  });

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 230,
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          _buildMenuItem(
            icon: Icons.newspaper,
            label: 'Newsfeed',
            index: 0,
          ),
          _buildMenuItem(
            icon: Icons.event,
            label: 'Events',
            index: 1,
          ),
          _buildMenuItem(
            icon: Icons.confirmation_num,
            label: 'Tickets',
            index: 2,
          ),
        ],
      ),
    );
  }

  Widget _buildMenuItem({required IconData icon, required String label, required int index}) {
    return InkWell(
      onTap: () => onDestinationSelected(index),
      child: Container(
        padding: const EdgeInsets.symmetric(vertical: 16.0, horizontal: 20.0),
        child: Row(
          children: [
            Icon(icon, size: 24, color: selectedIndex == index ? AppThemes.black : AppThemes.generateGradient(AppThemes.black)[4]),
            const SizedBox(width: 16),
            Text(
              label,
              style: TextStyle(
                fontWeight: selectedIndex == index ? FontWeight.bold : FontWeight.normal,
                fontSize: 16,
                color: selectedIndex == index ? AppThemes.black : AppThemes.generateGradient(AppThemes.black)[4],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
