import 'package:flutter/material.dart';

class AppThemes {

  static const Color black = Color(0xFF1e102a);
  static const Color primary = Color(0xFF6b1bbb);
  static const Color green = Color(0xFF6bbb1b);
  static const Color green2 = Color(0xFF1bbb6b);
  static const Color red = Color(0xFFbb1b1b);

  static final ThemeData defaultTheme = ThemeData(
    colorScheme: ColorScheme.fromSeed(
      seedColor: primary,
      brightness: Brightness.light,
    ),
    useMaterial3: true,
    fontFamily: 'Montserrat',
    textTheme: const TextTheme(
      bodyLarge: TextStyle(fontSize: 18, fontWeight: FontWeight.normal, color: black),
      bodyMedium: TextStyle(fontSize: 14.0, color: black),
      titleLarge: TextStyle(fontSize: 20, fontWeight: FontWeight.bold, color: black),
      titleMedium: TextStyle(fontSize: 17, fontWeight: FontWeight.bold, color: black),
      displayLarge: TextStyle(fontSize: 15.0, color: Colors.black87, fontWeight: FontWeight.bold),
      displayMedium: TextStyle(fontSize: 13.0, color: Colors.black87, fontWeight: FontWeight.bold),
      displaySmall: TextStyle(fontSize: 12.0, color: Colors.black54),
    ),
    appBarTheme: const AppBarTheme(
      color:  Color(0xFF6b1bbb),
      titleTextStyle: TextStyle(
        fontSize: 18,
        fontWeight: FontWeight.bold,
        color: Colors.white,
      ),
    ),
  );
}
