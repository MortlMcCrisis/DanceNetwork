import 'package:dance_network_frontend/checkout/ticket_type_list_page.dart';
import 'package:dance_network_frontend/common/max_sized_container.dart';
import 'package:dance_network_frontend/event/event.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:dance_network_frontend/util/api_service.dart';
import 'package:dance_network_frontend/util/image_resolver.dart';
import 'package:dance_network_frontend/util/screen_utils.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:intl/intl.dart';

class EventDetailPage extends StatelessWidget {
  final int eventId;

  const EventDetailPage({super.key, required this.eventId});

  Future<Event> fetchEvent(int eventId) async {
    final response = await ApiService().get(
        endpoint: '${ApiService.eventEndpoint}/$eventId',
        typeMapper: (json) => Event.fromMap(json as Map<String, dynamic>),
    );
    return response;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Event Details'),
      ),
      body: FutureBuilder<Event>(
        future: fetchEvent(eventId),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (snapshot.hasData) {
            final event = snapshot.data!;
             return EventDetailContent(event: event);
          } else {
            return const Center(child: Text('No data available'));
          }
        },
      ),
      floatingActionButton: Padding(
        padding: const EdgeInsets.only(bottom: 20.0),
        child: FloatingActionButton.extended(
          onPressed: () {
            Navigator.push(
              context,
              MaterialPageRoute(
                  builder: (context) => TicketTypeListPage(
                    eventId: eventId,
                  )
              ),
            );
          },
          label: Text(AppLocalizations.of(context)!.buy_ticket),
          icon: const Icon(Icons.confirmation_num),
          backgroundColor: AppThemes.green,
        ),
      ),
      floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
    );
  }
}

class EventDetailContent extends StatelessWidget {
  final Event event;

  const EventDetailContent({super.key, required this.event});

  @override
  Widget build(BuildContext context) {
    final imageResolver = ImageResolver();

    String formatDateRange(String start, String end) {
      final startDate = DateTime.parse(start);
      final endDate = DateTime.parse(end);
      final dateFormatter = DateFormat('MMMM d, y');
      return '${dateFormatter.format(startDate)} - ${dateFormatter.format(endDate)}';
    }

    final formattedDateRange = formatDateRange(event.startDate, event.endDate);

    return MaxSizedContainer(
      builder: (context, constraints) {
        return Center(
          child: ConstrainedBox(
            constraints: const BoxConstraints(maxWidth: ScreenUtils.wideScreenSize),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                AspectRatio(
                  aspectRatio: 16 / 9,
                  child: Image.network(
                    imageResolver.getFullUrl(event.imageUrl),
                    width: double.infinity,
                    fit: BoxFit.cover,
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      _buildInfoRow(
                        context: context,
                        icon: Icons.calendar_today,
                        text: formattedDateRange,
                      ),
                      const SizedBox(height: 12),
                      _buildInfoRow(
                        context: context,
                        icon: Icons.schedule,
                        text: event.startTime,
                      ),
                      const SizedBox(height: 12),
                      _buildInfoRow(
                        context: context,
                        icon: Icons.location_on,
                        text: event.location,
                      ),
                      const SizedBox(height: 12),
                      _buildInfoRow(
                        context: context,
                        icon: Icons.language,
                        text: event.website,
                      ),
                      const SizedBox(height: 12),
                      _buildInfoRow(
                        context: context,
                        icon: Icons.email,
                        text: event.email,
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        );
      }
    );
  }

  Widget _buildInfoRow({
    required BuildContext context,
    required IconData icon,
    required String text
  }) {
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        Icon(
          icon,
          size: 24,
          color: AppThemes.generateGradient(AppThemes.black)[2],
        ),
        const SizedBox(width: 16),
        Expanded(
          child: Text(
            text,
            style: Theme.of(context).textTheme.bodyMedium,
          ),
        ),
      ],
    );
  }
}
