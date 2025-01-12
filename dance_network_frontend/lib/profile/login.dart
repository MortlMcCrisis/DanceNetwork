import 'package:dance_network_frontend/common/max_sized_container.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:dance_network_frontend/util/auth_util.dart';
import 'package:dance_network_frontend/util/device_utils.dart';
import 'package:dance_network_frontend/util/token_storage.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:provider/provider.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  LoginPageState createState() => LoginPageState();
}

class LoginPageState extends State<LoginPage> {
  final TextEditingController _emailController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  bool _isPasswordVisible = false;

  late final TokenStorage tokenStorage;

  @override
  void initState() {
    super.initState();
    tokenStorage = Provider.of<TokenStorage>(context, listen: false);
  }

  void _login() async {
    if (_formKey.currentState!.validate()) {
      final email = _emailController.text.trim();
      final password = _passwordController.text;

      try {
        final token = await AuthService().authenticateWithCredentials(email, password);
        if (token != null) {
          debugPrint('Token erhalten: $token');
          await tokenStorage.saveToken(token);
          if (mounted) {
            Navigator.pop(context);
          }
        } else {
          debugPrint('Authentifizierung fehlgeschlagen: Kein Token zurückgegeben');
        }
      } catch (e) {
        debugPrint('Fehler bei der Authentifizierung: $e');
      }
    }
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(AppLocalizations.of(context)!.login),
        centerTitle: true,
      ),
      body: MaxSizedContainer(
          builder: (constraints) {
            return Center(
              child: ConstrainedBox(
              constraints: const BoxConstraints(maxWidth: DeviceUtils.wideScreenSize),
              child: Form(
                key: _formKey,
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Text(
                AppLocalizations.of(context)!.loginWithEmail,
                      style: const TextStyle(
                        fontSize: 24.0,
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    const SizedBox(height: 20.0),
                    TextFormField(
                      controller: _emailController,
                      decoration: InputDecoration(
                        labelText: AppLocalizations.of(context)!.email,
                        border: const OutlineInputBorder(),
                        prefixIcon: const Icon(Icons.email),
                      ),
                      keyboardType: TextInputType.emailAddress,
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return AppLocalizations.of(context)!.enterValidEmail;
                        }
                        final emailRegex = RegExp(r'^[^@]+@[^@]+\.[^@]+');
                        if (!emailRegex.hasMatch(value)) {
                          return AppLocalizations.of(context)!.invalidEmail;
                        }
                        return null;
                      },
                    ),
                    const SizedBox(height: 16.0),
                    TextFormField(
                      controller: _passwordController,
                      obscureText: !_isPasswordVisible,
                      decoration: InputDecoration(
                        labelText: AppLocalizations.of(context)!.password,
                        border: const OutlineInputBorder(),
                        prefixIcon: const Icon(Icons.lock),
                        suffixIcon: IconButton(
                          icon: Icon(
                            _isPasswordVisible
                                ? Icons.visibility
                                : Icons.visibility_off,
                          ),
                          onPressed: () {
                            setState(() {
                              _isPasswordVisible = !_isPasswordVisible;
                            });
                          },
                        ),
                      ),
                      validator: (value) {
                        if (value == null || value.isEmpty) {
                          return AppLocalizations.of(context)!.enterValidPassword;
                        }
                        /*if (value.length < 6) {
                          return AppLocalizations.of(context)!.passwordTooShort;
                        }*/
                        return null;
                      },
                    ),
                    const SizedBox(height: 24.0),
                    SizedBox(
                      width: double.infinity,
                      child: ElevatedButton(
                        onPressed: _login,
                        style: ElevatedButton.styleFrom(
                          padding: const EdgeInsets.symmetric(vertical: 16.0),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(AppThemes.borderRadius),
                          ),
                        ),
                        child: Text(AppLocalizations.of(context)!.login),
                      ),
                    ),
                    const SizedBox(height: 16.0),
                    Center(
                      child: TextButton(
                        onPressed: () {
                          // Aktion für "Passwort vergessen" hier hinzufügen
                          debugPrint('Forgot Password tapped');
                        },
                        child: Text(AppLocalizations.of(context)!.forgotPassword),
                      ),
                    ),
                  ],
                ),
              ),
            )
          );
        }
      ),
    );
  }
}
