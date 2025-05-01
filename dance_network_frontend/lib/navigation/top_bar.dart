import 'package:dance_network_frontend/create_event/create_event.dart';
import 'package:dance_network_frontend/profile/login.dart';
import 'package:dance_network_frontend/profile/profile.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:dance_network_frontend/util/token_storage.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:provider/provider.dart';

class AppBarWithSearch extends StatelessWidget implements PreferredSizeWidget {
  final ValueChanged<String> onSearch;

  const AppBarWithSearch({
    super.key,
    required this.onSearch,
  });

  @override
  Widget build(BuildContext context) {
    return AppBar(
      automaticallyImplyLeading: false,
      title: Row(
        children: [
          Expanded(
            child: Center(
              child: ConstrainedBox(
                constraints: const BoxConstraints(
                  maxWidth: 400.0,
                ),
                child: SizedBox(
                  height: 40.0,
                  child: SearchField(onSearch: onSearch),
                ),
              ),
            ),
          ),
          Consumer<TokenStorage>(
            builder: (context, tokenStorage, child) {
              return FutureBuilder<String?>(
                future: tokenStorage.getToken(),
                builder: (context, snapshot) {
                  if (snapshot.connectionState == ConnectionState.waiting) {
                    return const SizedBox();
                  }
                  if (snapshot.hasData && snapshot.data != null) {
                    return const Row(
                      children: [
                        SizedBox(width: 8.0),
                        CreateEventIcon(),
                      ],
                    );
                  }
                  return const SizedBox();
                },
              );
            },
          ),
          const SizedBox(width: 8.0),
          const AuthIcon(),
        ],
      ),
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight);
}

class SearchField extends StatelessWidget {
  final ValueChanged<String> onSearch;

  const SearchField({
    super.key,
    required this.onSearch,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: AppThemes.white,
        borderRadius: BorderRadius.circular(AppThemes.borderRadius),
        border: Border.all(
          color: AppThemes.generateGradient(AppThemes.black)[7],
          width: 1.0,
        ),
        boxShadow: [
          BoxShadow(
            color: AppThemes.black.withOpacity(0.2),
            blurRadius: 6.0,
            offset: const Offset(0, 4),
          ),
        ],
      ),
      child: TextField(
        onChanged: onSearch,
        decoration: InputDecoration(
          hintText: '${AppLocalizations.of(context)!.search}...',
          prefixIcon: const Icon(Icons.search),
          border: InputBorder.none,
        ),
      ),
    );
  }
}

class AuthIcon extends StatelessWidget {
  const AuthIcon({super.key});

  @override
  Widget build(BuildContext context) {
    return Consumer<TokenStorage>(
      builder: (context, tokenStorage, child) {
        return FutureBuilder<String?>(
          future: tokenStorage.getToken(),
          builder: (context, snapshot) {
            if (snapshot.connectionState == ConnectionState.waiting) {
              return const CircularProgressIndicator();
            }
            if (snapshot.hasData && snapshot.data != null) {
              return const AccountIcon();
            }
            return const LoginIcon();
          },
        );
      },
    );
  }
}

class AccountIcon extends StatelessWidget {
  const AccountIcon({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        shape: BoxShape.circle,
        border: Border.all(
          color: AppThemes.generateGradient(AppThemes.black)[6],
          width: 1.0,
        ),
      ),
      child: CircleAvatar(
        radius: 20.0,
        backgroundColor: AppThemes.white,
        child: PopupMenuButton<int>(
          icon: const Icon(Icons.account_circle, color: AppThemes.black),
          onSelected: (value) async {
            switch (value) {
              case 3:
              //AuthService().redirectToLogin();
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const ProfilePage(),
                  ),
                );
              case 4:
                final tokenStorage = Provider.of<TokenStorage>(context, listen: false);
                await tokenStorage.deleteToken();
            }
          },
          itemBuilder: (context) => [
            PopupMenuItem(
              value: 3,
              child: Text(AppLocalizations.of(context)!.profile),
            ),
            PopupMenuItem(
              value: 4,
              child: Text(AppLocalizations.of(context)!.logout),
            ),
          ],
        ),
      ),
    );
  }
}

class LoginIcon extends StatelessWidget {
  const LoginIcon({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        shape: BoxShape.circle,
          border: Border.all(
            color: AppThemes.generateGradient(AppThemes.black)[6],
            width: 1.0,
        ),
      ),
      child: CircleAvatar(
        radius: 20.0,
        backgroundColor: AppThemes.white,
        child: PopupMenuButton<int>(
          icon: const Icon(Icons.account_circle, color: AppThemes.black),
          onSelected: (value) {
            switch (value) {
              case 1:
                //AuthService().redirectToLogin();
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const LoginPage(),
                  ),
                );
              case 2:
                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => const LoginPage(), //TODO create Account Page
                  ),
                );
            }
          },
          itemBuilder: (context) => [
            PopupMenuItem(
              value: 1,
              child: Text(AppLocalizations.of(context)!.login),
            ),
            PopupMenuItem(
              value: 2,
              child: Text(AppLocalizations.of(context)!.createAccount),
            ),
          ],
        ),
      ),
    );
  }
}

class CreateEventIcon extends StatelessWidget {
  const CreateEventIcon({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        shape: BoxShape.circle,
        border: Border.all(
          color: AppThemes.generateGradient(AppThemes.black)[6],
          width: 1.0,
        ),
      ),
      child: CircleAvatar(
        radius: 20.0,
        backgroundColor: AppThemes.white,
        child: IconButton(
          icon: const Icon(Icons.add_circle, color: AppThemes.black),
          onPressed: () {
            showModalBottomSheet(
              scrollControlDisabledMaxHeightRatio: 0.7,
              context: context,
              builder: (_) => const CreateEventPage(),
            );
          },
          splashRadius: 20.0,
          tooltip: 'Create Event',
        ),
      ),
    );
  }
}
