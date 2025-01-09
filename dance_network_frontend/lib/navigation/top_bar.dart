import 'package:dance_network_frontend/config/theme.dart';
import 'package:dance_network_frontend/profile/login.dart';
import 'package:dance_network_frontend/profile/profile.dart';
import 'package:dance_network_frontend/util/screen_utils.dart';
import 'package:dance_network_frontend/util/token_storage.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:provider/provider.dart';

class AppBarWithSearch extends StatelessWidget implements PreferredSizeWidget {
  final String title;
  final ValueChanged<String> onSearch;

  const AppBarWithSearch({
    super.key,
    required this.title,
    required this.onSearch,
  });

  @override
  Widget build(BuildContext context) {
    return AppBar(
      title: ScreenUtils.isWideScreen(context)
          ? Row(
        mainAxisAlignment: MainAxisAlignment.start,
        children: [
          Expanded(
            child: Text(
              title,
              style: const TextStyle(
                  fontSize: 20.0,
                  fontWeight: FontWeight.bold
              ),
            ),
          ),
          Align(
            alignment: Alignment.center,
            child: SizedBox(
              width: MediaQuery.of(context).size.width * 0.45,
              height: 35.0,
              child: SearchField(
                onSearch: onSearch,
              ),
            ),
          ),
          const Expanded(
            child: Align(
              alignment: Alignment.centerRight,
              child: AuthIcon(),
            ),
          ),
        ],
      )
      : Row(
        children: [
          Expanded(
            child: SizedBox(
              height: 40.0,
              child: SearchField(onSearch: onSearch),
            ),
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
    return TextField(
      onChanged: onSearch,
      decoration: InputDecoration(
        hintText: '${AppLocalizations.of(context)!.search}...',
        prefixIcon: const Icon(Icons.search),
        border: OutlineInputBorder(
          borderRadius: BorderRadius.circular(AppThemes.borderRadius),
          borderSide: BorderSide.none,
        ),
        filled: true,
        fillColor: AppThemes.white,
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
    return CircleAvatar(
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
    );
  }
}

class LoginIcon extends StatelessWidget {
  const LoginIcon({super.key});

  @override
  Widget build(BuildContext context) {
    return CircleAvatar(
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
    );
  }
}
