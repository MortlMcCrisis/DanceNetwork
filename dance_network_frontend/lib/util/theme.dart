import 'package:flutter/material.dart';

class AppThemes {

  static const Color black = Color(0xFF1e102a);
  static const Color primary = Color(0xFF6b1bbb);
  static const Color green = Color(0xFF6bbb1b);
  static const Color red = Color(0xFFbb1b1b);
  static const Color white = Color(0xFFFEFDFB);

  static List<Color> generateGradient(Color color, {int steps = 10}) {
    return List<Color>.generate(
      steps,
          (index) {
        final double fraction = index / (steps - 1);
        return Color.lerp(color, AppThemes.white, fraction)!;
      },
    );
  }

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
        color: AppThemes.white,
      ),
    ),
  );

  static const double borderRadiusSmall = 4.0;
  static const double borderRadius = 8.0;

  static Border border = Border.all(
    color: AppThemes.generateGradient(AppThemes.black)[7],
    width: 1.0,
  );

  static BoxDecoration elevatedBoxDecoration = BoxDecoration(
    color: AppThemes.white,
    borderRadius: BorderRadius.circular(AppThemes.borderRadius),
    border: AppThemes.elevatedBoxBorder,
    boxShadow: [
      AppThemes.elevatedBoxShadow,
    ],
  );

  static BoxShadow elevatedBoxShadow = BoxShadow(
    color: black.withOpacity(0.6),
    blurRadius: 11.0,
    offset: const Offset(0, 4),
  );

  static Border elevatedBoxBorder = Border.all(
    color: generateGradient(AppThemes.black)[7],
    width: 1.0,
  );
}