import 'package:dance_network_frontend/util/screen_utils.dart';
import 'package:dance_network_frontend/util/theme.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

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
          Expanded(
            child: Align(
              alignment: Alignment.centerRight,
              child: CircleAvatar(
                radius: 20.0,
                backgroundColor: AppThemes.white,
                child:
                IconButton(
                  icon: const Icon(Icons.account_circle), // Profil Icon
                  color: AppThemes.black,
                  onPressed: () {
                    // Aktion beim Klicken auf das Profil-Icon
                  },
                ),
              ),
            ),
          ),
        ],
      )
        : Expanded(
          child: SizedBox(
            height: 40.0,
            child: SearchField(
              onSearch: onSearch,
            ),
          ),
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