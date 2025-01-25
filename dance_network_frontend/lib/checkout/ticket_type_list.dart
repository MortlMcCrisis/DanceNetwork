import 'package:dance_network_frontend/checkout/ticket_type_item.dart';
import 'package:flutter/material.dart';

class TicketTypeList extends StatelessWidget {
  final List<dynamic> ticketTypes;
  final Map<int, ValueNotifier<List<Map<String, dynamic>>>> ticketDetails;
  final void Function(int ticketId) onAddTicket;
  final void Function(int ticketId, int index) onRemoveTicket;
  final void Function(int ticketId, int index, Map<String, dynamic> updatedTicket) onUpdateTicket;

  const TicketTypeList({
    super.key,
    required this.ticketTypes,
    required this.ticketDetails,
    required this.onAddTicket,
    required this.onRemoveTicket,
    required this.onUpdateTicket,
  });

  @override
  Widget build(BuildContext context) {
    return Container(
      margin: const EdgeInsets.symmetric(vertical: 8.0, horizontal: 16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.stretch,
        children: ticketTypes.map((ticket) {
          int ticketId = ticket['id'] ?? 0;
          String name = ticket['name'] ?? "";
          String description = ticket['description'] ?? "";
          String price = ticket['price']?['number']?.toString() ?? "";

          return TicketTypeWidget(
            name: name,
            description: description,
            price: price,
            ticketDetailsNotifier: ticketDetails[ticketId]!,
            onAddTicket: () => onAddTicket(ticketId),
            onRemoveTicket: (index) => onRemoveTicket(ticketId, index),
            onUpdateTicket: (index, updatedTicket) =>
                onUpdateTicket(ticketId, index, updatedTicket),
          );
        }).toList(),
      ),
    );
  }
}
