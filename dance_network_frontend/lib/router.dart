import 'package:dance_network_frontend/event/event_details.dart';
import 'package:dance_network_frontend/event/event_list.dart';
import 'package:dance_network_frontend/main.dart';
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
        GoRoute(
          path: 'events',
          builder: (BuildContext context, GoRouterState state) {
            return const CardListPage();
          },
        ),
        GoRoute(
          path: '/event/:eventId',
          builder: (context, state) {
            final eventId = int.parse(state.pathParameters['eventId']!);
            return EventDetailPage(eventId: eventId);
          },
        )
      ],
    ),
  ],
);
