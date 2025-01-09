import 'package:dance_network_frontend/common/max_sized_container.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class ProfilePage extends StatelessWidget {
  const ProfilePage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(AppLocalizations.of(context)!.profile),
      ),
      body: MaxSizedContainer(
        builder: (context, constraints) {
          return Center(
            child: ConstrainedBox(
            constraints: const BoxConstraints(maxWidth: 600), // Maximale Breite auf 600 setzen
            child: Padding(
              padding: const EdgeInsets.all(16.0),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    const CircleAvatar(
                      radius: 50,
                      /*backgroundImage: AssetImage(
                          'assets/images/profile_placeholder.png'),*/
                    ),
                    const SizedBox(height: 16.0),
                    Text(
                      'Username',
                      // Hier kannst du den Benutzernamen dynamisch einfügen
                      style: Theme
                          .of(context)
                          .textTheme
                          .headlineMedium,
                    ),
                    const SizedBox(height: 8.0),
                    Text(
                      'user@example.com',
                      // Hier kannst du die E-Mail dynamisch einfügen
                      style: Theme
                          .of(context)
                          .textTheme
                          .bodyMedium,
                    ),
                    const SizedBox(height: 32.0),
                    ElevatedButton(
                      onPressed: () {
                        // Aktion für den "Abmelden"-Button (z.B. Logout)
                        // Beispiel: Navigator.pushNamed(context, '/login');
                      },
                      child: Text(AppLocalizations.of(context)!.logout),
                    ),
                  ],
                ),
              )
            )
          );
        }
      )
    );
  }
}
