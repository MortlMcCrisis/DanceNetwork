import 'dart:convert';
import 'package:http/http.dart' as http;

class ImageResolver {
  final String baseUrl;

  ImageResolver() : baseUrl = const String.fromEnvironment('image_base_url') {
    if (baseUrl.isEmpty) {
      throw ArgumentError('Base URL cannot be empty. Please set the "image_base_url" environment variable.');
    }
  }

  String getFullUrl(String path) {
    if (path.isEmpty) {
      throw ArgumentError('Path cannot be empty.');
    }

    final formattedPath = path.startsWith('/') ? path.substring(1) : path;

    print('$baseUrl/$formattedPath');

    return '$baseUrl/$formattedPath';
  }
}
