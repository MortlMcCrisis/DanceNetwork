import 'package:dance_network_frontend/checkout/ticket_type_list_page.dart';
import 'package:dance_network_frontend/editor.dart';
import 'package:dance_network_frontend/event/event_details.dart';
import 'package:dance_network_frontend/event/event_list.dart';
import 'package:dance_network_frontend/navigation/botton_bar.dart';
import 'package:dance_network_frontend/navigation/side_menu.dart';
import 'package:dance_network_frontend/util/device_utils.dart';
import 'package:flutter/material.dart';
import 'package:go_router/go_router.dart';

class AppRouter {
  static final router = GoRouter(
    routes: <RouteBase>[
      ShellRoute(
        builder: (context, state, child) {
          return Scaffold(
            body: Row(
              children: [
                if (DeviceUtils.isWideScreen(context))
                  NavigationRailComponent(
                    onDestinationSelected: (index) => _navigate(context, state, index),
                  ),
                Expanded(child: child),
              ],
            ),
            bottomNavigationBar: DeviceUtils.isWideScreen(context)
                ? null
                : CustomBottomNavigationBar(
              onItemTapped: (index) => _navigate(context, state, index),
            ),
          );
        },
        routes: [
          GoRoute(
            path: '/',
            builder: (BuildContext context, GoRouterState state) {
              return const EventListPage();
            },
            routes: <RouteBase>[
              _buildRoute(
                path: '/newsfeed',
                buildChild: (context, state) => RichTextEditorPage(), //const Center(child: Text('Page 1: Yet Another Content')),
              ),
              _buildRoute(
                path: '/events',
                buildChild: (context, state) => const EventListPage(),
              ),
              /*_buildRoute(
                path: '/create-event',
                buildChild: (context, state) => const CreateEventPage(),
              ),*/
              _buildRoute(
                  path: '/events/:eventId',
                  buildChild: (context, state) {
                    final eventId = int.parse(state.pathParameters['eventId']!);
                    return EventDetailPage(eventId: eventId);
                  }
              ),
              _buildRoute(
                  path: '/events/:eventId/checkout',
                  buildChild: (context, state) {
                    final eventId = int.parse(state.pathParameters['eventId']!);
                    return TicketTypeListPage(eventId: eventId);
                  }
              ),
            ],
          ),
        ],
      ),
    ],
  );

  static void _navigate(BuildContext context, GoRouterState state, int index) {
    if (index == 0) context.go('/newsfeed');
    if (index == 1) context.go('/events');
    //if (index == 2) context.go('/create-event');
    /*if (index == 4) {
      final eventId = int.parse(state.pathParameters['eventId']!);
      context.go('/events/$eventId');
    }*/
  }

  static GoRoute _buildRoute<T>({
    required String path,
    required Widget Function(BuildContext context, GoRouterState state) buildChild,
  }) {
    if(DeviceUtils.isMobileDevice()) {
      return GoRoute(
        path: path,
        pageBuilder: (context, state) {
          return CustomTransitionPage<T>(
            key: state.pageKey,
            child: buildChild(context, state),
            transitionsBuilder: (context, animation, secondaryAnimation, child) {
              return FadeTransition(
                opacity: animation,
                child: child,
              );
            },
          );
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
}