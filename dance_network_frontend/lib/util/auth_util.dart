import 'dart:convert';
import 'package:http/http.dart' as http;

class AuthService {
  final String tokenEndpoint = '${const String.fromEnvironment('keycloak_url')}/realms/dance-network/protocol/openid-connect/token';
  final String clientId = const String.fromEnvironment('keycloak_client');

  Future<String?> authenticateWithCredentials(
      String username, String password) async {
    try {
      final response = await http.post(
        Uri.parse(tokenEndpoint),
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: {
          'grant_type': 'password',
          'client_id': clientId,
          'username': username,
          'password': password,
          'scope': 'openid profile email',
        },
      );

      if (response.statusCode == 200) {
        final Map<String, dynamic> responseBody = json.decode(response.body);
        final String? accessToken = responseBody['access_token'];
        print(accessToken);
        return accessToken;
      } else {
        // Fehler beim Abrufen des Tokens
        print('Fehler beim Abrufen des Access Tokens: ${response.body}');
        return null;
      }
    } catch (e) {
      // Allgemeiner Fehler
      print('Fehler bei der Authentifizierung: $e');
      return null;
    }
  }
}