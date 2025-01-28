import 'package:dance_network_frontend/common/max_sized_container.dart';
import 'package:dance_network_frontend/common/responsive_switch.dart';
import 'package:dance_network_frontend/event/event.dart';
import 'package:dance_network_frontend/event/event_list_item.dart';
import 'package:dance_network_frontend/event/mobile/event_list.dart';
import 'package:dance_network_frontend/event/web/event_list.dart';
import 'package:dance_network_frontend/navigation/top_bar.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:dance_network_frontend/util/api_service.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class EventListPage extends StatelessWidget {
  const EventListPage({super.key});

  Future<List<Event>> _fetchEvents() async {
    return await ApiService().get(
      endpoint: ApiService.eventEndpoint,
      typeMapper: (json) {
        return (json as List<dynamic>)
          .map((eventJson) => Event.fromMap(eventJson as Map<String, dynamic>))
          .toList();
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        const SizedBox(height: 20),
        Expanded(
          child: Scaffold(
            appBar: AppBarWithSearch(
              onSearch: (query) {
                // Optional: Handling der Suche
                print('Search query: $query');
              },
            ),
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

                final events = (snapshot.data!)
                    .map((item) => item as Event)
                    .toList();

                eventCardBuilder(event) => EventCard(event: event);

                return MaxSizedContainer(
                  builder: (constraints) {
                    return ResponsiveSwitch(
                      breakpoint: (context, constraints) => MediaQuery.of(context).size.width >= 800,
                      webWidgetBuilder: (constraints) {
                        return WebEventList(
                            constraints: constraints,
                            events: events,
                            eventCardBuilder: eventCardBuilder);
                      },
                      mobileWidgetBuilder: (constraints) {
                        return MobileEventList(
                            events: events,
                            eventCardBuilder: eventCardBuilder);
                      }
                    );
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
                label: Text(
                  AppLocalizations.of(context)!.map,
                  style: const TextStyle(color: AppThemes.white),
                ),
                icon: const Icon(
                  Icons.map,
                  color: AppThemes.white,
                ),
                backgroundColor: AppThemes.primary,
              ),
            ),
            floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
          ),
        ),
      ],
    );
  }
}
