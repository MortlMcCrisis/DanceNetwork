import 'package:dance_network_frontend/common/back_button.dart';
import 'package:dance_network_frontend/common/editable_date_field.dart';
import 'package:dance_network_frontend/common/editable_image.dart';
import 'package:dance_network_frontend/common/editable_label.dart';
import 'package:dance_network_frontend/common/editable_time_field.dart';
import 'package:dance_network_frontend/common/max_sized_container.dart';
import 'package:dance_network_frontend/common/on_mobile.dart';
import 'package:dance_network_frontend/model/event.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:dance_network_frontend/util/api_service.dart';
import 'package:dance_network_frontend/util/device_utils.dart';
import 'package:dance_network_frontend/util/image_resolver.dart';
import 'package:dance_network_frontend/util/token_storage.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:go_router/go_router.dart';
import 'package:intl/intl.dart';

//TODO here the state handling could be done better with set state that the event is clean loaded.

class EventDetailPage extends StatefulWidget {
  final int eventId;

  const EventDetailPage({super.key, required this.eventId});

  @override
  EventDetailPageState createState() => EventDetailPageState();
}

class EventDetailPageState extends State<EventDetailPage> {
  Event? event;

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
        future: _fetchEvent(widget.eventId),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          } else if (snapshot.hasData) {
            event = snapshot.data!;
            return CustomScrollView(
              slivers: [
                SliverAppBar(
                  expandedHeight: 200,
                  //pinned: true, // TODO pin and move title into this bar. it should only appear when the bar is pinned and must also be aligned with the back button
                  flexibleSpace: FlexibleSpaceBar(
                    background: EditableBackgroundImage(
                      initialImageUrl: ImageResolver.getFullUrl(event!.imageUrl),
                      onImageChanged: (newImagePath) async {
                        final token = await TokenStorage().getToken();
                        final newImageUrl = await ApiService().postImage(
                          endpoint: ApiService.fileUploadClosedEndpoint,
                          file: newImagePath,
                          token: token,
                        );
                        event = await ApiService().patch(
                            endpoint: '${ApiService.eventClosedEndpoint}/${event!.eventId}/profileImage',
                            content: {'value': newImageUrl},
                            typeMapper: (json) => Event.fromMap(json),
                            token: token
                        );
                        return ImageResolver.getFullUrl(newImageUrl);
                      },
                    ),
                  ),
                  leading: const OnMobile(CustomBackButton(route: '/events')),
                ),
                SliverToBoxAdapter(
                  child: Padding(
                    padding: const EdgeInsets.only(top: 16.0, left: 16.0, right: 16.0),
                    child: Center(
                      child:
                      EditableLabel(
                        initialText: event!.title,
                        style: Theme.of(context).textTheme.headlineLarge,
                        onSubmitted: (newText) async {
                          final token = await TokenStorage().getToken();
                          event = await ApiService().patch(
                              endpoint: '${ApiService.eventClosedEndpoint}/${event!.eventId}/name',
                              content: {'value': newText},
                              typeMapper: (json) => Event.fromMap(json),
                              token: token
                          );
                        },
                      )
                    ),
                  ),
                ),
                SliverToBoxAdapter(
                  child: EventDetailContent(
                    event: event!,
                    onEventUpdated: (updatedEvent) {
                      event = updatedEvent;
                  },),
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
            context.go('/events/${widget.eventId}/checkout');
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
  final Function(Event) onEventUpdated;

  const EventDetailContent({
    super.key,
    required this.event,
    required this.onEventUpdated
  });

  @override
  Widget build(BuildContext context) {
    String formatDateRange(String start, String? end) {
      final dateFormatter = DateFormat('MMMM d, y');

      final startDate = DateTime.parse(start);

      if(end != null ) {
        final endDate = DateTime.parse(end);
        return '${dateFormatter.format(startDate)} - ${dateFormatter.format(endDate)}';
      }
      return dateFormatter.format(startDate);
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
                      _buildDateInfoRow(
                        context: context,
                        icon: Icons.calendar_today,
                        text: formattedDateRange,
                        id: event.eventId,
                        property: 'startDate'
                      ),
                      const SizedBox(height: 12),
                      _buildTimeInfoRow(
                        context: context,
                        icon: Icons.schedule,
                        text: event.startTime,
                        id: event.eventId,
                        property: 'startTime'
                      ),
                      const SizedBox(height: 12),
                      _buildInfoRow(
                        context: context,
                        icon: Icons.location_on,
                        text: event.location,
                        id: event.eventId,
                        property: 'location'
                      ),
                      const SizedBox(height: 12),
                      _buildInfoRow(
                        context: context,
                        icon: Icons.language,
                        text: event.website,
                        id: event.eventId,
                        property: 'website'
                      ),
                      const SizedBox(height: 12),
                      _buildInfoRow(
                        context: context,
                        icon: Icons.email,
                        text: event.email,
                        id: event.eventId,
                        property: 'email'
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
    required String text,
    required int id,
    required String property
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
          child: EditableLabel(
            initialText: text,
            onSubmitted: (newText) async {
              final token = await TokenStorage().getToken();
              var updatedEvent = await ApiService().patch(
                endpoint: '${ApiService.eventClosedEndpoint}/$id/$property',
                content: {'value': newText},
                typeMapper: (json) => Event.fromMap(json),
                token: token
              );
              onEventUpdated(updatedEvent);
            },
          )
        ),
      ],
    );
  }

  Widget _buildTimeInfoRow({
    required BuildContext context,
    required IconData icon,
    required String text,
    required int id,
    required String property
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
            child: TimePickerLabel(
              initialTime: TimeOfDay.fromDateTime(DateFormat.Hm().parse(text)),
              onSubmitted: (newText) async {
                final token = await TokenStorage().getToken();
                var updatedEvent = await ApiService().patch(
                    endpoint: '${ApiService.eventClosedEndpoint}/$id/$property',
                    content: {'value': timeOfDayToIsoString(newText)},
                    typeMapper: (json) => Event.fromMap(json),
                    token: token
                );
                onEventUpdated(updatedEvent);
              },
            )
        ),
      ],
    );
  }

  String timeOfDayToIsoString(TimeOfDay time) {
    twoDigits(int n) => n.toString().padLeft(2, '0');
    return '${twoDigits(time.hour)}:${twoDigits(time.minute)}';
  }

  Widget _buildDateInfoRow({
    required BuildContext context,
    required IconData icon,
    required String text,
    required int id,
    required String property
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
            child: DatePickerLabel(
              initialDate: DateFormat('MMMM d, yyyy').parse(text),
              onSubmitted: (newText) async {
                final token = await TokenStorage().getToken();
                var updatedEvent = await ApiService().patch(
                    endpoint: '${ApiService.eventClosedEndpoint}/$id/$property',
                    content: {'value': dateToIsoString(newText)},
                    typeMapper: (json) => Event.fromMap(json),
                    token: token
                );
                onEventUpdated(updatedEvent);
              },
            )
        ),
      ],
    );
  }

  String dateToIsoString(DateTime date) {
    twoDigits(int n) => n.toString().padLeft(2, '0');
    return '${date.year}-${twoDigits(date.month)}-${twoDigits(date.day)}';
  }
}
