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
import 'dart:convert';

//TODO here the state handling could be done better with set state that the event is clean loaded.

class EventDetailPage extends StatefulWidget {
  final int eventId;

  const EventDetailPage({super.key, required this.eventId});

  @override
  EventDetailPageState createState() => EventDetailPageState();
}

class EventDetailPageState extends State<EventDetailPage> {
  String? token;
  Event? event;

  @override
  void initState() {
    super.initState();
    _loadToken();
  }

  //TODO move to util class
  String? extractSubFromToken(String? token) {
    if (token == null) return null;
    try {
      final parts = token.split('.');
      if (parts.length != 3) return null;

      final payload = base64Url.normalize(parts[1]);
      final decoded = utf8.decode(base64Url.decode(payload));
      final payloadMap = json.decode(decoded);

      return payloadMap['sub'];
    } catch (e) {
      return null;
    }
  }

  Future<void> _loadToken() async {
    final t = await TokenStorage().getToken();
    setState(() {
      token = t;
    });
  }

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
                    background: Stack(
                      alignment: Alignment.bottomLeft,
                      children: [
                        EditableImage(
                          initialImageUrl: ImageResolver.getFullUrl(event!.bannerImageUrl),
                          active: extractSubFromToken(token) != null && extractSubFromToken(token) == event!.creator,
                          onImageChanged: (newImagePath) async {
                            final token = await TokenStorage().getToken();
                            final newImageUrl = await ApiService().postImage(
                              endpoint: ApiService.fileUploadClosedEndpoint,
                              file: newImagePath,
                              token: token,
                            );
                            event = await ApiService().patch(
                                endpoint: '${ApiService.eventClosedEndpoint}/${event!.eventId}/bannerImage',
                                content: {'value': newImageUrl},
                                typeMapper: (json) => Event.fromMap(json),
                                token: token
                            );
                            return ImageResolver.getFullUrl(newImageUrl);
                          },
                        ),
                        Padding(
                          padding: const EdgeInsets.only(top: 32.0, left: 16.0, right: 16.0),
                          child: Center(
                            child: ClipRRect(
                              borderRadius: BorderRadius.circular(AppThemes.borderRadius),
                              child: SizedBox(
                                width: 120,
                                height: 120,
                                child: EditableImage(
                                  initialImageUrl: ImageResolver.getFullUrl(event!.profileImageUrl),
                                  active: extractSubFromToken(token) != null && extractSubFromToken(token) == event!.creator,
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
                            ),
                          ),
                        ),
                      ]
                    ),
                  ),
                  leading: const OnMobile(CustomBackButton(route: '/events')),
                ),
                SliverToBoxAdapter(
                  child: Padding(
                    padding: const EdgeInsets.only(top: 16.0, left: 16.0, right: 16.0),
                    child: Center(
                      child: EditableLabel(
                        initialText: event!.title,
                        fallbackText: "enter title",
                        style: Theme.of(context).textTheme.headlineLarge,
                        active: extractSubFromToken(token) != null && extractSubFromToken(token) == event!.creator,
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
                    },
                    token: token
                  ),
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
  final String? token;

  const EventDetailContent({
    super.key,
    required this.event,
    required this.onEventUpdated,
    required this.token
  });

  //TODO move to util class
  String? extractSubFromToken(String? token) {
    if (token == null) return null;
    try {
      final parts = token.split('.');
      if (parts.length != 3) return null;

      final payload = base64Url.normalize(parts[1]);
      final decoded = utf8.decode(base64Url.decode(payload));
      final payloadMap = json.decode(decoded);

      return payloadMap['sub'];
    } catch (e) {
      return null;
    }
  }

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
                        property: 'startDate',
                        creator: event.creator,
                        currentUser: extractSubFromToken(token)
                      ),
                      const SizedBox(height: 12),
                      _buildTimeInfoRow(
                        context: context,
                        icon: Icons.schedule,
                        text: event.startTime,
                        id: event.eventId,
                        property: 'startTime',
                        creator: event.creator,
                        currentUser: extractSubFromToken(token)
                      ),
                      const SizedBox(height: 12),
                      _buildInfoRow(
                        context: context,
                        icon: Icons.location_on,
                        text: event.location,
                        fallbackText: "enter location",
                        id: event.eventId,
                        property: 'location',
                        creator: event.creator,
                        currentUser: extractSubFromToken(token)
                      ),
                      const SizedBox(height: 12),
                      _buildInfoRow(
                        context: context,
                        icon: Icons.language,
                        text: event.website,
                        fallbackText: "enter website",
                        id: event.eventId,
                        property: 'website',
                        creator: event.creator,
                        currentUser: extractSubFromToken(token)
                      ),
                      const SizedBox(height: 12),
                      _buildInfoRow(
                        context: context,
                        icon: Icons.email,
                        text: event.email,
                        fallbackText: "enter email",
                        id: event.eventId,
                        property: 'email',
                        creator: event.creator,
                        currentUser: extractSubFromToken(token)
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
    required String fallbackText,
    required int id,
    required String property,
    required String creator,
    required String? currentUser
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
            fallbackText: fallbackText,
            active: currentUser != null && currentUser == creator,
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
    required String property,
    required String creator,
    required String? currentUser
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
              active: currentUser != null && currentUser == creator,
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
    required String property,
    required String creator,
    required String? currentUser
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
              active: currentUser != null && currentUser == creator,
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
