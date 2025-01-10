import 'package:flutter/foundation.dart';

import 'package:flutter/material.dart';

class DeviceUtils {

  static const wideScreenSize = 600.0;
  static const centerContainerMaxSize = 900.0;

  static bool isWideScreen(BuildContext context) {
    return MediaQuery.of(context).size.width >= wideScreenSize;
  }

  static bool isMobileDevice() {
    if (kIsWeb) {
      return false; // Auf dem Web läuft die App
    } else {
      return defaultTargetPlatform == TargetPlatform.iOS || defaultTargetPlatform == TargetPlatform.android;
    }
  }
}

