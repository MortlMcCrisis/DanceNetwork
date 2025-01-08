import 'dart:convert';

import 'package:http/http.dart' as http;

class AuthService {
  final String tokenEndpoint = '${const String.fromEnvironment('keycloak_url')}/realms/dance-network/protocol/openid-connect/token';
  final String clientId = const String.fromEnvironment('keycloak_client');
  final String redirectUri = Uri.encodeComponent('com.mortl.dance_network_frontend://callback');
  //final String redirectUri = Uri.encodeComponent('http://localhost:61248/');

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

  /*void redirectToLogin() async {
    final loginUrl = Uri.parse(
      '${const String.fromEnvironment('keycloak_url')}/realms/dance-network/protocol/openid-connect/auth'
          '?client_id=$clientId'
          '&redirect_uri=$redirectUri'
          '&response_type=code'
          '&scope=openid profile email',
    );

    if (await canLaunchUrl(loginUrl)) {
      await launchUrl(loginUrl, mode: LaunchMode.externalApplication);
    } else {
      throw 'Konnte Login-Seite nicht öffnen: $loginUrl';
    }
  }

  Future<String?> exchangeCodeForToken(String code) async {
    const String tokenEndpoint =
        '${const String.fromEnvironment('keycloak_url')}/realms/dance-network/protocol/openid-connect/token';

    try {
      final response = await http.post(
        Uri.parse(tokenEndpoint),
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: {
          'grant_type': 'authorization_code',
          'client_id': clientId,
          'code': code,
          'redirect_uri': redirectUri,
        },
      );

      if (response.statusCode == 200) {
        final Map<String, dynamic> responseBody = json.decode(response.body);
        return responseBody['access_token'];
      } else {
        print('Fehler beim Token-Austausch: ${response.body}');
        return null;
      }
    } catch (e) {
      print('Fehler: $e');
      return null;
    }
  }
*/
}