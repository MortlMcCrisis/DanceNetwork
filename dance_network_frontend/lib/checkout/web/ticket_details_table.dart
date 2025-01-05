import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class TicketDetailsTable extends StatelessWidget {
  final Map<int, ValueNotifier<List<Map<String, dynamic>>>> ticketDetails;
  final List<dynamic> ticketTypes;

  const TicketDetailsTable({
    super.key,
    required this.ticketDetails,
    required this.ticketTypes,
  });

  @override
  Widget build(BuildContext context) {
    return Table(
      columnWidths: const <int, TableColumnWidth>{
        0: FixedColumnWidth(30),
        1: FlexColumnWidth(),
        2: FixedColumnWidth(60),
      },
      defaultVerticalAlignment: TableCellVerticalAlignment.middle,
      children: [
        // Kopfzeile
        TableRow(
          children: <Widget>[
            Align(
              alignment: Alignment.centerLeft,
              child: Text(
                '#',
                style: Theme.of(context).textTheme.titleMedium,
              ),
            ),
            Align(
              alignment: Alignment.center,
              child: Text(
                AppLocalizations.of(context)!.type,
                style: Theme.of(context).textTheme.titleMedium,
              ),
            ),
            Align(
              alignment: Alignment.centerRight,
              child: Text(
                AppLocalizations.of(context)!.price,
                style: Theme.of(context).textTheme.titleMedium,
              ),
            ),
          ],
        ),
        const TableRow(
          children: <Widget>[
            SizedBox(height: 8.0),
            SizedBox(height: 8.0),
            SizedBox(height: 8.0),
          ],
        ),
        for (var entry in ticketDetails.entries)
          TableRow(
            children: [
              ValueListenableBuilder<List<Map<String, dynamic>>>(
                valueListenable: entry.value,
                builder: (context, tickets, child) {
                  if (tickets.isEmpty) {
                    return const SizedBox(height: 0);
                  }
                  return Align(
                    alignment: Alignment.centerLeft,
                    child: Text('${tickets.length}'),
                  );
                },
              ),
              ValueListenableBuilder<List<Map<String, dynamic>>>(
                valueListenable: entry.value,
                builder: (context, tickets, child) {
                  if (tickets.isEmpty) {
                    return const SizedBox(height: 0);
                  }
                  return Align(
                    alignment: Alignment.centerLeft,
                    child: Builder(
                      builder: (context) {
                        final ticket = ticketTypes.firstWhere(
                              (t) => t['id'] == entry.key,
                          orElse: () => null,
                        );
                        return Text(ticket?['name'] ?? 'Unknown');
                      },
                    ),
                  );
                },
              ),
              ValueListenableBuilder<List<Map<String, dynamic>>>(
                valueListenable: entry.value,
                builder: (context, tickets, child) {
                  if (tickets.isEmpty) {
                    return const SizedBox(height: 0);
                  }
                  final ticket = ticketTypes.firstWhere(
                        (t) => t['id'] == entry.key,
                    orElse: () => null,
                  );
                  final price = ticket?['price']?['number'] ?? 0.0;
                  final total = tickets.length * price;
                  return Align(
                    alignment: Alignment.centerRight,
                    child: Text('\$${total.toStringAsFixed(2)}'),
                  );
                },
              ),
            ],
          ),
      ],
    );
  }
}