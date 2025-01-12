import 'package:dance_network_frontend/checkout/ticket_type_list.dart';
import 'package:dance_network_frontend/checkout/web/ticket_details_table.dart';
import 'package:dance_network_frontend/common/max_sized_container.dart';
import 'package:dance_network_frontend/theme.dart';
import 'package:dance_network_frontend/util/api_service.dart';
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
    _fetchTicketTypes();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Select Tickets'),
      ),
      body: LayoutBuilder(
        builder: (context, constraints) {
          if (constraints.maxWidth > 700) {
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
                    Expanded(
                      flex: 1,
                      child: Container(
                        padding: const EdgeInsets.symmetric(vertical: 16.0, horizontal: 12.0),
                        margin: const EdgeInsets.symmetric(vertical: 16.0),
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
                                  'Total: \$${total.toStringAsFixed(2)}',
                                  style: Theme.of(context).textTheme.titleMedium,
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
          } else {
            return Stack(
              children: [
                MaxSizedContainer(
                  builder: (constraints) {
                    return Container(
                      margin: const EdgeInsets.only(bottom: 90.0),
                      child: TicketTypeList(
                        ticketTypes: ticketTypes,
                        ticketDetails: ticketDetails,
                        onAddTicket: _addTicket,
                        onRemoveTicket: _removeTicket,
                        onUpdateTicket: _updateTicketData,
                      ),
                    );
                  },
                ),
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
          }
        },
      ),
    );
  }
}

