import 'package:dance_network_frontend/common/back_button.dart';
import 'package:dance_network_frontend/common/image_loading.dart';
import 'package:dance_network_frontend/common/max_sized_container.dart';
import 'package:dance_network_frontend/common/on_mobile.dart';
import 'package:dance_network_frontend/event/event.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:dance_network_frontend/util/api_service.dart';
import 'package:dance_network_frontend/util/device_utils.dart';
import 'package:dance_network_frontend/util/image_resolver.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:go_router/go_router.dart';
import 'package:intl/intl.dart';

class EventDetailPage extends StatelessWidget {
  final int eventId;

  const EventDetailPage({super.key, required this.eventId});

  Future<Event> _fetchEvent(int eventId) async {
    final response = await ApiService().get(
        endpoint: '${ApiService.eventEndpoint}/$eventId',
        typeMapper: (json) => Event.fromMap(json),
    );
    return response;
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: FutureBuilder<Event>(
        future: _fetchEvent(eventId),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (snapshot.hasData) {
            final event = snapshot.data!;
            return CustomScrollView(
              slivers: [
                SliverAppBar(
                  expandedHeight: 200,
                  //pinned: true, // TODO pin and move title into this bar. it should only appear when the bar is pinned and must also be aligned with the back button
                  flexibleSpace: FlexibleSpaceBar(
                    background: Image.network(
                      ImageResolver.getFullUrl(event.imageUrl),
                      width: double.infinity,
                      fit: BoxFit.cover,
                      loadingBuilder: (context, child, loadingProgress) => CustomLoadingBuilder(
                        loadingProgress: loadingProgress,
                        child: child,
                      ),
                      errorBuilder: (context, error, stackTrace) => const CustomErrorBuilder(),
                    ),
                  ),
                  leading: const OnMobile(CustomBackButton(route: '/events')),
                ),
                SliverToBoxAdapter(
                  child: Padding(
                    padding: const EdgeInsets.only(top: 16.0, left: 16.0, right: 16.0),
                    child: Center(
                      child: Text(
                        event.title,
                        style: Theme.of(context).textTheme.headlineLarge,
                      ),
                    ),
                  ),
                ),
                SliverToBoxAdapter(
                  child: EventDetailContent(event: event),
                )
              ],
            );
          } else {
            return const Center(child: Text('No data available'));
          }
        },
      ),
      floatingActionButton: Padding(
        padding: const EdgeInsets.only(bottom: 20.0),
        child: FloatingActionButton.extended(
          onPressed: () {
            context.go('/events/$eventId/checkout');
          },
          label: Text(
            AppLocalizations.of(context)!.buy_ticket,
            style: const TextStyle(color: AppThemes.white),
          ),
          icon: const Icon(
            Icons.confirmation_num,
            color: AppThemes.white,
          ),
          backgroundColor: AppThemes.primary,
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

    String formatDateRange(String start, String end) {
      final startDate = DateTime.parse(start);
      final endDate = DateTime.parse(end);
      final dateFormatter = DateFormat('MMMM d, y');
      return '${dateFormatter.format(startDate)} - ${dateFormatter.format(endDate)}';
    }

    final formattedDateRange = formatDateRange(event.startDate, event.endDate);

    return MaxSizedContainer(
      builder: (constraints) {
        return Center(
          child: ConstrainedBox(
            constraints: const BoxConstraints(maxWidth: DeviceUtils.wideScreenSize),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
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
