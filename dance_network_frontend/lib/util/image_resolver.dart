class ImageResolver {
  static const String baseUrl = String.fromEnvironment('image_base_url');

  static String getFullUrl(String path) {
    var baseUrl = const String.fromEnvironment('image_base_url');
    if (baseUrl.isEmpty) {
      throw ArgumentError('Base URL cannot be empty. Please set the "image_base_url" environment variable.');
    }

    if (path.isEmpty) {
      throw ArgumentError('Path cannot be empty.');
    }

    final formattedPath = path.startsWith('/') ? path.substring(1) : path;

    return '$baseUrl/$formattedPath';
  }
}
