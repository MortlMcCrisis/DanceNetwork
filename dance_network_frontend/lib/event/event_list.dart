import 'package:dance_network_frontend/common/max_sized_container.dart';
import 'package:dance_network_frontend/event/event.dart';
import 'package:dance_network_frontend/event/event_list_item.dart';
import 'package:dance_network_frontend/util/api_service.dart';
import 'package:dance_network_frontend/util/screen_utils.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class CardListPage extends StatelessWidget {
  const CardListPage({super.key});

  Future<List<dynamic>> _fetchEvents() async {
    return await ApiService().get(
        endpoint: ApiService.eventEndpoint,
        typeMapper: (json) => json as List<dynamic>
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: FutureBuilder<List<dynamic>>(
        future: _fetchEvents(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
            return const Center(child: Text('No events found.'));
          }

          final events = snapshot.data!;

          return MaxSizedContainer(
            builder: (context, constraints) {
              if (constraints.maxWidth > ScreenUtils.wideScreenSize) {
                var columnCount = constraints.maxWidth > 700 ? 3 : 2;
                var aspectRatio = constraints.maxWidth > 700 ? 0.95 : 1.05;
                return Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: GridView.builder(
                    gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                      crossAxisCount: columnCount,
                      crossAxisSpacing: 8.0,
                      mainAxisSpacing: 8.0,
                      childAspectRatio: aspectRatio,
                    ),
                    itemCount: events.length,
                    itemBuilder: (context, index) {
                      final event = Event.fromMap(events[index]);
                      return EventCard(event: event);
                    },
                  ),
                );
              } else {
                return Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 8.0),
                  child: ListView.builder(
                    itemCount: events.length,
                    itemBuilder: (context, index) {
                      final event = Event.fromMap(events[index]);
                      return EventCard(event: event);
                    },
                  ),
                );
              }
            },
          );
        },
      ),
      floatingActionButton: Padding(
        padding: const EdgeInsets.only(bottom: 20.0),
        child: FloatingActionButton.extended(
          onPressed: () async {
            ScaffoldMessenger.of(context).showSnackBar(
              const SnackBar(content: Text('Karte Button gedrückt!')),
            );
          },
          label: Text(AppLocalizations.of(context)!.map),
          icon: const Icon(Icons.map),
        ),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
    );
  }
}
