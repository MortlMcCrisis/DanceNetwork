import 'package:dance_network_frontend/event/event.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:dance_network_frontend/util/image_resolver.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class EventCard extends StatelessWidget {
  final Event event;

  const EventCard({
    super.key,
    required this.event,
  });

  String _getMonthName(int month) {
    const months = [
      "Jan", "Feb", "Mar", "Apr", "May", "Jun",
      "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    ];
    return months[month - 1];
  }

  @override
  Widget build(BuildContext context) {
    final DateTime parsedDate = DateTime.parse(event.startDate);
    final String day = parsedDate.day.toString();
    final String month = _getMonthName(parsedDate.month);
    final String year = parsedDate.year.toString();

    final imageResolver = ImageResolver();

    return GestureDetector(
      onTap: () {
        context.go('/event/${event.eventId}');
      },
      child: Container(
        margin: const EdgeInsets.symmetric(vertical: 8.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            ClipRRect(
              borderRadius: const BorderRadius.only(
                topLeft: Radius.circular(AppThemes.borderRadius),
                topRight: Radius.circular(AppThemes.borderRadius),
              ),
              child: AspectRatio(
                aspectRatio: 16 / 9,
                child: Image.network(
                  imageResolver.getFullUrl(event.imageUrl),
                  width: double.infinity,
                  fit: BoxFit.cover,
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.all(9.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    event.title,
                    style: Theme.of(context).textTheme.titleLarge,
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                  ),
                  const SizedBox(height: 4.0),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Container(
                        margin: const EdgeInsets.only(right: 12.0),
                        child: Text.rich(
                          TextSpan(
                            children: [
                              TextSpan(
                                text: '$day ',
                                style: Theme.of(context).textTheme.displayMedium,
                              ),
                              TextSpan(
                                text: '$month\n',
                                style: Theme.of(context).textTheme.displayLarge,
                              ),
                              TextSpan(
                                text: year,
                                style: Theme.of(context).textTheme.displaySmall,
                              ),
                            ],
                          ),
                          textAlign: TextAlign.left,
                        ),
                      ),
                      Expanded(
                        child: Text(
                          event.location,
                          style: Theme.of(context).textTheme.bodyMedium,
                          maxLines: 2,
                          overflow: TextOverflow.ellipsis,
                        ),
                      ),
                    ],
                  ),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
