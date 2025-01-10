import 'package:dance_network_frontend/event/event_details.dart';
import 'package:dance_network_frontend/event/event_list.dart';
import 'package:dance_network_frontend/main.dart';
import 'package:dance_network_frontend/util/device_utils.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

final GoRouter router = GoRouter(
  routes: <RouteBase>[
    GoRoute(
      path: '/',
      builder: (BuildContext context, GoRouterState state) {
        return const MyHomePage();
      },
      routes: <RouteBase>[
        buildRoute(
          path: '/events',
          buildChild: (context, state) => const EventListPage(),
        ),
        buildRoute(
          path: '/events/:eventId',
          buildChild: (context, state) {
            final eventId = int.parse(state.pathParameters['eventId']!);
            return EventDetailPage(eventId: eventId);
          }
        ),
      ],
    ),
  ],
);

GoRoute buildRoute<T>({
  required String path,
  required Widget Function(BuildContext context, GoRouterState state) buildChild,
}) {
  if(DeviceUtils.isMobileDevice()) {
    return GoRoute(
      path: path,
      builder: (context, state) {
        return buildChild(context, state);
      },
    );
  }

  return GoRoute(
      path: path,
      pageBuilder: (context, state) {
        return CustomTransitionPage<T>(
          key: state.pageKey,
          child: buildChild(context, state),
          transitionsBuilder: (context, animation, secondaryAnimation, child) {
            // No animation
            return child;
          },
        );
      }
  );
}