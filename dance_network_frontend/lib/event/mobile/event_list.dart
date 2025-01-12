import 'package:dance_network_frontend/event/event.dart';
import 'package:flutter/material.dart';

class MobileEventList extends StatelessWidget {
  final List<Event> events;
  final Widget Function(Event event) eventCardBuilder;

  const MobileEventList({
    super.key,
    required this.events,
    required this.eventCardBuilder,
  });

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 8.0),
      child: ListView.builder(
        itemCount: events.length,
        itemBuilder: (context, index) {
          return eventCardBuilder(events[index]);
        },
      ),
    );
  }
}
