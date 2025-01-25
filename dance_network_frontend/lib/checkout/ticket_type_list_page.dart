import 'package:dance_network_frontend/checkout/ticket_type_list.dart';
import 'package:dance_network_frontend/checkout/web/ticket_details_table.dart';
import 'package:dance_network_frontend/common/back_button.dart';
import 'package:dance_network_frontend/common/image_loading.dart';
import 'package:dance_network_frontend/common/max_sized_container.dart';
import 'package:dance_network_frontend/event/event.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:dance_network_frontend/util/api_service.dart';
import 'package:dance_network_frontend/util/device_utils.dart';
import 'package:dance_network_frontend/util/image_resolver.dart';
import 'package:flutter/material.dart';

class TicketTypeListPage extends StatefulWidget {
  final int eventId;

  const TicketTypeListPage({
    super.key,
    required this.eventId,
  });

  @override
  TicketTypeListPageState createState() => TicketTypeListPageState();
}

class TicketTypeListPageState extends State<TicketTypeListPage> {
  late List<dynamic> ticketTypes;
  Map<int, ValueNotifier<List<Map<String, dynamic>>>> ticketDetails = {};
  ValueNotifier<double> totalAmountNotifier = ValueNotifier<double>(0.0);
  Event? event;

  Future<void> _fetchEvent(int eventId) async {
    final fetchedEvent = await ApiService().get(
      endpoint: '${ApiService.eventEndpoint}/$eventId',
      typeMapper: (json) => Event.fromMap(json),
    );
    setState(() {
      event = fetchedEvent; // Save the loaded event in state
    });
  }

  Future<void> _fetchTicketTypes() async {
    ticketTypes = await ApiService().get(
      endpoint: ApiService.ticketTypes,
      queryParams: {'eventId': widget.eventId},
      typeMapper: (json) => json as List<dynamic>
    );
    for (var ticketType in ticketTypes) {
      final ticketId = ticketType['id'];
      ticketDetails.putIfAbsent(ticketId, () => ValueNotifier<List<Map<String, dynamic>>>([]));
    }
    setState(() {});
  }

  void _addTicket(int ticketId) {
    ticketDetails.putIfAbsent(ticketId, () => ValueNotifier<List<Map<String, dynamic>>>([]));

    final updatedList = [
      ...ticketDetails[ticketId]!.value,
      {
        'firstName': '',
        'lastName': '',
        'address': '',
        'country': '',
        'gender': '',
        'role': '',
        'email': '',
        'phone': '',
      },
    ];

    ticketDetails[ticketId]!.value = updatedList;
    _updateTotal();
  }

  void _removeTicket(int ticketId, int index) {
    if (ticketDetails.containsKey(ticketId)) {
      final updatedList = List<Map<String, dynamic>>.from(ticketDetails[ticketId]!.value);
      updatedList.removeAt(index);

      ticketDetails[ticketId]!.value = updatedList;
      _updateTotal();
    }
  }

  void _updateTicketData(int ticketId, int index, Map<String, dynamic> updatedTicket) {
    setState(() {
      if (ticketDetails.containsKey(ticketId)) {
        final tickets = ticketDetails[ticketId]!.value;
        tickets[index] = updatedTicket;
        ticketDetails[ticketId]!.value = List.from(tickets);
      }
    });
  }

  void _updateTotal() {
    double total = 0.0;
    ticketDetails.forEach((ticketId, notifier) {
      final ticket = ticketTypes.firstWhere((ticket) => ticket['id'] == ticketId);
      final price = ticket['price']?['number'] ?? 0.0;
      total += price * notifier.value.length;
    });
    totalAmountNotifier.value = total;
  }

  bool _ticketDetailsValid() {
    return ticketDetails.values.every((notifier) =>
        notifier.value.every((details) => details.values.isNotEmpty));
  }

  @override
  void initState() {
    super.initState();
    ticketTypes = [];
    _fetchEvent(widget.eventId);
    _fetchTicketTypes();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: LayoutBuilder(
        builder: (context, constraints) {
          return Stack(
            children: [
              CustomScrollView(
                slivers: [
                  SliverAppBar(
                    expandedHeight: 200,
                    //pinned: true, // TODO pin and move title into this bar. it should only appear when the bar is pinned and must also be aligned with the back button
                    flexibleSpace: FlexibleSpaceBar(
                      background: event != null
                          ? Image.network(
                        ImageResolver.getFullUrl(event!.imageUrl),
                        width: double.infinity,
                        fit: BoxFit.cover,
                        loadingBuilder: (context, child, loadingProgress) => CustomLoadingBuilder(
                          loadingProgress: loadingProgress,
                          child: child,
                        ),
                        errorBuilder: (context, error, stackTrace) => const CustomErrorBuilder(),
                      )
                          : const Center(child: CircularProgressIndicator()), // Placeholder while loading
                    ),
                    leading: DeviceUtils.isMobileDevice() ? CustomBackButton(route: '/events/${widget.eventId}') : Container(),
                  ),
                  SliverToBoxAdapter(
                    child: Padding(
                      padding: const EdgeInsets.only(top: 16.0, left: 16.0, right: 16.0),
                      child: Center(
                        child: Text(
                          event != null ? event!.title : "",
                          style: Theme.of(context).textTheme.headlineLarge,
                        ),
                      ),
                    ),
                  ),
                  SliverToBoxAdapter(
                    child: LayoutBuilder(
                      builder: (context, constraints) {
                        return MaxSizedContainer(
                          builder: (constraints) {
                            return Row(
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Expanded(
                                  flex: 2,
                                  child: TicketTypeList(
                                    ticketTypes: ticketTypes,
                                    ticketDetails: ticketDetails,
                                    onAddTicket: _addTicket,
                                    onRemoveTicket: _removeTicket,
                                    onUpdateTicket: _updateTicketData,
                                  ),
                                ),
                                if (constraints.maxWidth > 700)
                                  Expanded(
                                    flex: 1,
                                    child: Container(
                                      padding: const EdgeInsets.symmetric(
                                          vertical: 16.0, horizontal: 12.0),
                                      margin: const EdgeInsets.symmetric(
                                          vertical: 16.0),
                                      decoration: AppThemes.elevatedBoxDecoration,
                                      child: Column(
                                        mainAxisSize: MainAxisSize.min,
                                        children: [
                                          TicketDetailsTable(
                                            ticketDetails: ticketDetails,
                                            ticketTypes: ticketTypes,
                                          ),
                                          const Divider(thickness: 1.0, height: 16.0),
                                          const SizedBox(height: 16.0),
                                          ValueListenableBuilder<double>(
                                            valueListenable: totalAmountNotifier,
                                            builder: (context, total, child) {
                                              return Text(
                                                'Total: \$${total.toStringAsFixed(
                                                    2)}',
                                                style: Theme
                                                    .of(context)
                                                    .textTheme
                                                    .titleMedium,
                                              );
                                            },
                                          ),
                                          const SizedBox(height: 16.0),
                                          ElevatedButton(
                                            onPressed: () {
                                              // Aktion für den Checkout oder Weiter zur nächsten Seite
                                            },
                                            child: const Text('Checkout'),
                                          ),
                                        ],
                                      ),
                                    ),
                                  ),
                              ],
                            );
                          },
                        );
                      },
                    ),
                  ),
                ],
              ),
              if (constraints.maxWidth <= 700)
                Positioned(
                  bottom: 16,
                  left: 16,
                  right: 16,
                  child: MaxSizedContainer(
                    builder: (constraints) {
                      return Container(
                        padding: const EdgeInsets.symmetric(vertical: 16.0, horizontal: 12.0),
                        decoration: AppThemes.elevatedBoxDecoration,
                        child: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            ValueListenableBuilder<double>(
                              valueListenable: totalAmountNotifier,
                              builder: (context, total, child) {
                                return Text(
                                  'Total: \$${total.toStringAsFixed(2)}',
                                  style: Theme.of(context).textTheme.titleMedium,
                                );
                              },
                            ),
                            ElevatedButton(
                              onPressed: () {
                                // Aktion für den Checkout oder Weiter zur nächsten Seite
                              },
                              child: const Text('Checkout'),
                            ),
                          ],
                        ),
                      );
                    },
                  ),
                ),
            ],
          );
        },
      )
    );
  }
}
