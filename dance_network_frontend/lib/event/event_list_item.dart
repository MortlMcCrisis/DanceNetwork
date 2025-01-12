import 'package:dance_network_frontend/common/responsive_switch.dart';
import 'package:dance_network_frontend/event/event.dart';
import 'package:dance_network_frontend/event/mobile/event_list_item.dart';
import 'package:dance_network_frontend/event/web/event_list_item.dart';
import 'package:dance_network_frontend/util/date_util.dart';
import 'package:dance_network_frontend/util/device_utils.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class EventCard extends StatelessWidget {
  final Event event;

  const EventCard({
    super.key,
    required this.event,
  });

  @override
  Widget build(BuildContext context) {
    var eventListItemContent = EventListItemContent(event: event);

    return GestureDetector(
      onTap: () {
        context.go('/events/${event.eventId}');
      },
      child: Container(
        margin: const EdgeInsets.symmetric(vertical: 8.0),
        child: ResponsiveSwitch(
          breakpoint: (context, constraints) => MediaQuery.of(context).size.width >= 800,
          mobileWidgetBuilder: (constraints) {
          return MobileEventCard(
                event: event,
                eventDetails: eventListItemContent
            );
          },
          webWidgetBuilder: (constraints) {
          return WebEventCard(
            event: event,
            eventDetails: eventListItemContent
            );
          },
        ),
      ),
    );
  }
}

class EventListItemContent extends StatelessWidget {
  final Event event;

  const EventListItemContent({
    super.key,
    required this.event,
  });

  @override
  Widget build(BuildContext context) {
    var (day, month, year) = DateUtil.extractDateComponents(event.startDate);

    return Padding(
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
            crossAxisAlignment: CrossAxisAlignment.start,
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
                  maxLines: DeviceUtils.isWideScreen(context) ? 2 : 3,
                  overflow: TextOverflow.ellipsis,
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }
}
