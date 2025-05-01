import 'dart:convert';

import 'package:flutter/cupertino.dart';
import 'package:http/http.dart' as http;
import 'package:http_parser/http_parser.dart';

class ApiService {
  static const eventEndpoint = '/api/open/v1/events';
  static const ticketTypes = '/api/open/v1/ticket-types';

  static const eventClosedEndpoint = '/api/closed/v1/events';
  static const fileUploadClosedEndpoint = '/api/closed/v1/files/photo-upload-new';

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
      throw Exception('Failed to get data: ${response.statusCode} - ${response.body}');
    }
  }

  Future<T> post<T>({
    required String endpoint,
    required Map<String, dynamic> content,
    required T Function(dynamic json) typeMapper,
    String? token}) async {

    final uri = Uri.parse('$baseUrl$endpoint');

    final response = await http.post(
      uri,
      headers: {
        'Content-Type': 'application/json',
        if (token != null) 'Authorization': 'Bearer $token',
      },
      body: jsonEncode(content),
    );

    if (response.statusCode == 201) {
      final json = jsonDecode(utf8.decode(response.bodyBytes));
      return typeMapper(json);
    } else {
      throw Exception('Failed to post data: ${response.statusCode} - ${response.body}');
    }
  }

  Future<String> postImage<T>({
    required String endpoint,
    required UploadableFile file,
    String? token}) async {

    final request = http.MultipartRequest(
      'POST',
      Uri.parse('$baseUrl$endpoint'),
    );

    request.files.add(await file.toMultipartFile('file'));

    if (token != null) {
      request.headers['Authorization'] = 'Bearer $token';
    }

    final streamedResponse = await request.send();
    final response = await http.Response.fromStream(streamedResponse);

    if (response.statusCode == 200) {
      debugPrint('Uploaded! File URL: ${response.body}');
      return response.body;
    } else {
      throw Exception('Failed to post image: ${response.statusCode} - ${response.body}');
    }
  }

  Future<T> patch<T>({
    required String endpoint,
    required Map<String, dynamic> content,
    required T Function(dynamic json) typeMapper,
    String? token}) async {

    final uri = Uri.parse('$baseUrl$endpoint');
    final response = await http.patch(
      uri,
      headers: {
        'Content-Type': 'application/json',
        if (token != null) 'Authorization': 'Bearer $token',
      },
      body: jsonEncode(content),
    );
    if (response.statusCode == 200) {
      final json = jsonDecode(utf8.decode(response.bodyBytes));
      return typeMapper(json);
    } else {
      throw Exception('Failed to patch data: ${response.statusCode} - ${response.body}');
    }
  }
}

class UploadableFile {
  final List<int> bytes;
  final String filename;
  final String? contentType;

  UploadableFile({
    required this.bytes,
    required this.filename,
    this.contentType,
  });

  Future<http.MultipartFile> toMultipartFile(String fieldName) async {
    return http.MultipartFile.fromBytes(
      fieldName,
      bytes,
      filename: filename,
      contentType: contentType != null ? MediaType.parse(contentType!) : null,
    );
  }
}