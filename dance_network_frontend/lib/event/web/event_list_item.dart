import 'package:dance_network_frontend/common/image_loading.dart';
import 'package:dance_network_frontend/model/event.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:dance_network_frontend/util/image_resolver.dart';
import 'package:flutter/material.dart';

class WebEventCard extends StatelessWidget {
  final Event event;
  final Widget eventDetails;

  const WebEventCard({
    super.key,
    required this.event,
    required this.eventDetails,
  });

  @override
  Widget build(BuildContext context) {
    return Column(
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
              ImageResolver.getFullUrl(event.profileImageUrl),
              width: double.infinity,
              fit: BoxFit.cover,
              loadingBuilder: (context, child, loadingProgress) => CustomLoadingBuilder(
                loadingProgress: loadingProgress,
                child: child,
              ),
              errorBuilder: (context, error, stackTrace) => const CustomErrorBuilder(),
            ),
          ),
        ),
        eventDetails,
      ],
    );
  }
}
