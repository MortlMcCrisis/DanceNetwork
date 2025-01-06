import 'package:dance_network_frontend/util/image_resolver.dart';
import 'package:flutter/material.dart';
import 'package:dance_network_frontend/event/event_details.dart';
import 'package:dance_network_frontend/util/theme.dart';

Widget buildCard(
    BuildContext context, {
      required int eventId,
      required String imageUrl,
      required String startDate,
      required String endDate,
      required String startTime,
      required String title,
      required String location,
      required String website,
      required String email,
    }) {

  String getMonthName(int month) {
    const months = [
      "Jan", "Feb", "Mar", "Apr", "May", "Jun",
      "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    ];
    return months[month - 1];
  }

  DateTime parsedDate = DateTime.parse(startDate);

  String day = parsedDate.day.toString();
  String month = getMonthName(parsedDate.month);
  String year = parsedDate.year.toString();

  final imageResolver = ImageResolver();

  return GestureDetector(
    onTap: () {
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => DetailPage(
              eventId: eventId,
              imageUrl: imageUrl,
              startDate: startDate,
              endDate: endDate,
              startTime: startTime,
              title: title, location: location,
              website: website,
              email: email,
          )
        ),
      );
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
                imageResolver.getFullUrl(imageUrl),
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
                  title,
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
                              text: year, // Jahr
                              style: Theme.of(context).textTheme.displaySmall,
                            ),
                          ],
                        ),
                        textAlign: TextAlign.left,
                      ),
                    ),
                    Expanded(
                      child: Text(
                        location,
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
