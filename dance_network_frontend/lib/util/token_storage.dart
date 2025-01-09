import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class TokenStorage extends ChangeNotifier {
  final FlutterSecureStorage _storage = const FlutterSecureStorage();

  Future<String?> getToken() async {
    return await _storage.read(key: 'token');
  }

  Future<void> saveToken(String token) async {
    await _storage.write(key: 'token', value: token);
    notifyListeners();
  }

  Future<void> deleteToken() async {
    await _storage.delete(key: 'token');
    notifyListeners();
  }
}
