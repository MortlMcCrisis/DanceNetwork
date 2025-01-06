import 'package:dance_network_frontend/navigation/botton_bar.dart';
import 'package:dance_network_frontend/navigation/side_menu.dart';
import 'package:dance_network_frontend/time_table.dart';
import 'package:dance_network_frontend/navigation/top_bar.dart';
import 'package:dance_network_frontend/util/screen_utils.dart';
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
    const Center(child: Text('Page 4: Yet Another Content')),
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

  @override
  Widget build(BuildContext context) {
    final isWideScreen = ScreenUtils.isWideScreen(context);

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
      bottomNavigationBar: isWideScreen
          ? null
          : CustomBottomNavigationBar(
        currentIndex: _selectedIndex,
        onItemTapped: _onMenuItemTapped,
      ),
    );
  }
}
