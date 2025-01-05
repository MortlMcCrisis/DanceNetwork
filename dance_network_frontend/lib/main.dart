import 'package:dance_network_frontend/side_menu.dart';
import 'package:dance_network_frontend/time_table.dart';
import 'package:dance_network_frontend/top_bar.dart';
import 'package:dance_network_frontend/util/theme.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

import 'event/event_list.dart';

void main() async {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Dance network',
      theme: AppThemes.defaultTheme,
      locale: const Locale('en'),
      localizationsDelegates: AppLocalizations.localizationsDelegates,
      supportedLocales: AppLocalizations.supportedLocales,
      home: const MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _selectedIndex = 1;
  String _searchQuery = '';

  final List<Widget> _pages = [
    const TimetableScreen(),
    const CardListPage(),
    const Center(child: Text('Page 3: Yet Another Content')),
  ];

  void _onMenuItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
    if (Scaffold.of(context).isDrawerOpen) {
      Navigator.of(context).pop();
    }
  }

  void _onSearch(String query) {
    setState(() {
      _searchQuery = query;
    });
  }

  Widget _buildBottomNavigationBar() {
    return BottomNavigationBar(
      items: const <BottomNavigationBarItem>[
        BottomNavigationBarItem(icon: Icon(Icons.dynamic_feed), label: 'Newsfeed'),
        BottomNavigationBarItem(icon: Icon(Icons.festival), label: 'Events'),
        BottomNavigationBarItem(icon: Icon(Icons.confirmation_num), label: 'Tickets'),
      ],
      currentIndex: _selectedIndex,
      selectedItemColor: Colors.deepPurple,
      onTap: _onMenuItemTapped,
    );
  }

  @override
  Widget build(BuildContext context) {
    final isWideScreen = MediaQuery.of(context).size.width > 600;

    return Scaffold(
      appBar: AppBarWithSearch(
        title: 'Dance Network',
        onSearch: _onSearch,
      ),
      body: Row(
        children: [
          if (isWideScreen)
            NavigationRailComponent(
              selectedIndex: _selectedIndex,
              onDestinationSelected: _onMenuItemTapped,
            ),
          Expanded(
            child: Center(
              child: _pages[_selectedIndex],
            ),
          ),
        ],
      ),
      bottomNavigationBar: isWideScreen ? null : _buildBottomNavigationBar(),
    );
  }
}
