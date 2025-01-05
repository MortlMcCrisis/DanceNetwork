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
      title: Text(title),
      bottom: PreferredSize(
        preferredSize: const Size.fromHeight(40.0),
        child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Center(
            child: ConstrainedBox(
              constraints: BoxConstraints(
                maxWidth: MediaQuery.of(context).size.width * 0.65,
              ),
              child: Container(
                height: 50.0,
                decoration: BoxDecoration(
                  color: Colors.white,
                  borderRadius: BorderRadius.circular(16.0),
                  boxShadow: const [
                    BoxShadow(
                      color: Colors.black26,
                      blurRadius: 8.0,
                      offset: Offset(0, 2),
                    ),
                  ],
                ),
                child: TextField(
                  onChanged: onSearch,
                  decoration: InputDecoration(
                    hintText: '${AppLocalizations.of(context)!.search}...',
                    prefixIcon: const Icon(Icons.search),
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(16.0),
                      borderSide: BorderSide.none,
                    ),
                    filled: true,
                    fillColor: Colors.white,
                  ),
                ),
              ),
            ),
          ),
        ),
      ),
    );
  }

  @override
  Size get preferredSize => const Size.fromHeight(kToolbarHeight + 56.0);
}
