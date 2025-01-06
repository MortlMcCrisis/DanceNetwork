import 'package:flutter/material.dart';

class ScreenUtils {

  static bool isWideScreen(BuildContext context) {
    return MediaQuery.of(context).size.width >= 600;
  }
}
