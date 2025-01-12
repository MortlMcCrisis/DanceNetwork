import 'package:dance_network_frontend/event/event.dart';
import 'package:flutter/material.dart';

class WebEventList extends StatelessWidget {
  final BoxConstraints constraints;
  final List<Event> events;
  final Widget Function(Event event) eventCardBuilder;

  const WebEventList({
    super.key,
    required this.constraints,
    required this.events,
    required this.eventCardBuilder,
  });

  @override
  Widget build(BuildContext context) {
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
          return eventCardBuilder(events[index]);
        },
      ),
    );
  }
}
