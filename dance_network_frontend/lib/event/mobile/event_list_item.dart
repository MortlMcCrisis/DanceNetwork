import 'package:dance_network_frontend/common/image_loading.dart';
import 'package:dance_network_frontend/model/event.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:dance_network_frontend/util/image_resolver.dart';
import 'package:flutter/material.dart';

class MobileEventCard extends StatelessWidget {
  final Event event;
  final Widget eventDetails;

  const MobileEventCard({
    super.key,
    required this.event,
    required this.eventDetails,
  });

  @override
  Widget build(BuildContext context) {
    print('Image: ${event.imageUrl}');
    return Row(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        ClipRRect(
          borderRadius: BorderRadius.circular(AppThemes.borderRadius),
          child: Image.network(
            ImageResolver.getFullUrl(event.imageUrl),
            width: 120,
            height: 120,
            fit: BoxFit.cover,
            loadingBuilder: (context, child, loadingProgress) => CustomLoadingBuilder(
              loadingProgress: loadingProgress,
              child: child,
            ),
            errorBuilder: (context, error, stackTrace) => const CustomErrorBuilder(),
          ),
        ),
        const SizedBox(width: 8.0),
        Expanded(
          child: eventDetails,
        ),
      ],
    );
  }
}
