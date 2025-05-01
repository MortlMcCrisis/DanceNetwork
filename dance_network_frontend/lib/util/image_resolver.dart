

class ImageResolver {
  static const String placeholder = 'images/300x300.png';
  static const String baseUrl = String.fromEnvironment('image_base_url');

  static String getFullUrl(String path) {
    if (baseUrl.isEmpty) {
      throw ArgumentError('Base URL cannot be empty. Please set the "image_base_url" environment variable.');
    }

    if (path.isEmpty) {
      return '$baseUrl/$placeholder';
    }

    final formattedPath = path.startsWith('/') ? path.substring(1) : path;

    return '$baseUrl/$formattedPath';
  }
}
