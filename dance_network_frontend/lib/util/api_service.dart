import 'dart:convert';

import 'package:http/http.dart' as http;

class ApiService {
  static const eventEndpoint = '/api/open/v1/events';
  static const ticketTypes = '/api/open/v1/ticket-types';

  final String baseUrl;

  ApiService() : baseUrl = const String.fromEnvironment('api_base_url') {
    if (baseUrl.isEmpty) {
      throw ArgumentError('Base URL cannot be empty. Please set the "api_base_url" environment variable.');
    }
  }

  Future<T> get<T>({
      required String endpoint,
      Map<String, dynamic>? queryParams,
      required T Function(dynamic json) typeMapper}) async {

    final stringQueryParams = queryParams?.map((key, value) => MapEntry(key, value.toString()));
    final uri = Uri.parse('$baseUrl$endpoint').replace(queryParameters: stringQueryParams);

    final response = await http.get(uri);

    if (response.statusCode == 200) {
      final json = jsonDecode(utf8.decode(response.bodyBytes));
      return typeMapper(json);
    } else {
      throw Exception('Failed to fetch data: ${response.statusCode}');
    }
  }
}
