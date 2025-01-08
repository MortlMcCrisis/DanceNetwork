import 'package:dance_network_frontend/navigation/botton_bar.dart';
import 'package:dance_network_frontend/navigation/side_menu.dart';
import 'package:dance_network_frontend/navigation/top_bar.dart';
import 'package:dance_network_frontend/time_table.dart';
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
  //late final AppLinks _appLinks;

  int _selectedIndex = 1;
  String _searchQuery = '';

  /*@override
  void initState() {
    super.initState();
    _appLinks = AppLinks();
    _handleIncomingLinks();
  }*/

  /*void _handleIncomingLinks() async {
    print("handle link");
    try {
      _appLinks.uriLinkStream.listen((Uri? uri) {
        if (uri != null && uri.queryParameters.containsKey('code')) {
          final code = uri.queryParameters['code']!;
          // Tausche den Authorization Code gegen ein Access Token
          AuthService().exchangeCodeForToken(code).then((token) {
            if (token != null) {
              print('Access Token: $token');
              // Token speichern oder Nutzer weiterleiten
            } else {
              print('Fehler beim Abrufen des Tokens');
            }
          });
        }
      });
    } catch (err) {
      print('Allgemeiner Fehler beim Verarbeiten der Links: $err');
    }
  }*/

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
