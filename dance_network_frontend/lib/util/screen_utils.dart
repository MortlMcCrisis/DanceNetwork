import 'package:flutter/material.dart';

class ScreenUtils {

  static const wideScreenSize = 600.0;
  static const centerContainerMaxSize = 900.0;

  static bool isWideScreen(BuildContext context) {
    return MediaQuery.of(context).size.width >= wideScreenSize;
  }
}
