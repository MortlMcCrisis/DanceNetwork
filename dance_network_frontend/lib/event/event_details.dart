import 'package:dance_network_frontend/checkout/ticket_type_list_page.dart';
import 'package:dance_network_frontend/config/theme.dart';
import 'package:dance_network_frontend/util/image_resolver.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:intl/intl.dart';

class DetailPage extends StatelessWidget {
  final int eventId;
  final String imageUrl;
  final String startDate;
  final String endDate;
  final String startTime;
  final String title;
  final String location;
  final String website;
  final String email;

  const DetailPage({
    super.key,
    required this.eventId,
    required this.imageUrl,
    required this.startDate,
    required this.endDate,
    required this.startTime,
    required this.title,
    required this.location,
    required this.website,
    required this.email,
  });

  @override
  Widget build(BuildContext context) {
    final imageResolver = ImageResolver();

    String formatDateRange(String start, String end) {
      final startDate = DateTime.parse(start);
      final endDate = DateTime.parse(end);
      final dateFormatter = DateFormat('MMMM d, y');
      return '${dateFormatter.format(startDate)} - ${dateFormatter.format(endDate)}';
    }

    final formattedDateRange = formatDateRange(startDate, endDate);

    return Scaffold(
      appBar: AppBar(
        title: Text(title),
      ),
      body: SingleChildScrollView(
        child: Center(
          child: ConstrainedBox(
            constraints: const BoxConstraints(maxWidth: 550),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                AspectRatio(
                  aspectRatio: 16 / 9,
                  child: Image.network(
                    imageResolver.getFullUrl(imageUrl),
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
                        text: startTime,
                      ),
                      const SizedBox(height: 12),
                      _buildInfoRow(
                        context: context,
                        icon: Icons.location_on,
                        text: location,
                      ),
                      const SizedBox(height: 12),
                      _buildInfoRow(
                        context: context,
                        icon: Icons.language,
                        text: website,
                      ),
                      const SizedBox(height: 12),
                      _buildInfoRow(
                        context: context,
                        icon: Icons.email,
                        text: email,
                      ),
                    ],
                  ),
                ),
              ],
            ),
          ),
        ),
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
