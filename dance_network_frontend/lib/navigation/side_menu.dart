import 'package:dance_network_frontend/main.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class NavigationRailComponent extends StatelessWidget {
  final ValueChanged<int> onDestinationSelected;

  const NavigationRailComponent({
    super.key,
    required this.onDestinationSelected,
  });

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 230,
        child: Container(
        decoration: BoxDecoration(
          border: Border(
            right: BorderSide(
              color: AppThemes.generateGradient(AppThemes.black)[8], // Farbe des Randes
              width: 1.0, // Breite des Randes
            ),
          ),
        ),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Padding(
              padding: const EdgeInsets.only(left: 16, top: 16, bottom: 16),
              child: Row(
                children: [
                  Image.asset(
                    'assets/logo.png',
                    height: 120,
                    width: 60,
                  ),
                  Expanded(
                    child: Text(
                      "Dance Connect",
                      textAlign: TextAlign.center,
                      style: Theme.of(context).textTheme.titleLarge,
                      softWrap: true,
                    ),
                  ),
                ],
              ),
            ),
            const Divider(),
            _buildMenuItem(
              context: context,
              icon: Icons.dynamic_feed,
              label: 'Newsfeed',
              index: 0,
            ),
            _buildMenuItem(
              context: context,
              icon: Icons.festival,
              label: 'Events',
              index: 1,
            ),
            /*_buildMenuItem(
              context: context,
              icon: Icons.add_circle,
              label: 'Create event',
              index: 2,
            ),*/
            _buildMenuItem(
              context: context,
              icon: Icons.confirmation_num,
              label: 'Tickets',
              index: 3,
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildMenuItem({required BuildContext context, required IconData icon, required String label, required int index}) {
    final navigationState = Provider.of<NavigationState>(context);

    return InkWell(
      onTap: () {
        navigationState.updateIndex(index);
        onDestinationSelected(index);
      },
      child: Container(
        padding: const EdgeInsets.symmetric(vertical: 16.0, horizontal: 20.0),
        child: Row(
          children: [
            Icon(icon, size: 24, color: navigationState.selectedIndex == index ? AppThemes.black : AppThemes.generateGradient(AppThemes.black)[4]),
            const SizedBox(width: 16),
            Text(
              label,
              style: TextStyle(
                fontWeight: navigationState.selectedIndex == index ? FontWeight.bold : FontWeight.normal,
                fontSize: 14,
                color: navigationState.selectedIndex == index ? AppThemes.black : AppThemes.generateGradient(AppThemes.black)[4],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
