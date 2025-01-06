import 'package:dance_network_frontend/checkout/ticket_details.dart';
import 'package:dance_network_frontend/util/theme.dart';
import 'package:flutter/material.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

class TicketTypeWidget extends StatelessWidget {
  final String name;
  final String description;
  final String price;
  final ValueNotifier<List<Map<String, dynamic>>> ticketDetailsNotifier;
  final Function() onAddTicket;
  final Function(int) onRemoveTicket;
  final Function(int, Map<String, dynamic>) onUpdateTicket;

  const TicketTypeWidget({
    super.key,
    required this.name,
    required this.description,
    required this.price,
    required this.ticketDetailsNotifier,
    required this.onAddTicket,
    required this.onRemoveTicket,
    required this.onUpdateTicket,
  });

  bool _ticketDetailsIncomplete(Map<String, dynamic> details) {
    return details.values.any((element) => element.isEmpty);
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 16.0, horizontal: 12.0),
      margin: const EdgeInsets.symmetric(vertical: 8.0),
      decoration: BoxDecoration(
        color: AppThemes.white,
        borderRadius: BorderRadius.circular(AppThemes.borderRadius),
        border: Border.all(
          color: AppThemes.generateGradient(AppThemes.black)[7],
          width: 1.0,
        ),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            name,
            style: Theme.of(context).textTheme.titleLarge,
          ),
          const SizedBox(height: 8.0),
          Text(
            description,
            style: Theme.of(context).textTheme.bodyMedium,
          ),
          const SizedBox(height: 16.0),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Row(
                children: [
                  ValueListenableBuilder<List<Map<String, dynamic>>>(
                    valueListenable: ticketDetailsNotifier,
                    builder: (context, ticketList, child) {
                      return Container(
                        width: 30.0,
                        height: 30.0,
                        alignment: Alignment.center,
                        decoration: BoxDecoration(
                          border: Border.all(color: AppThemes.generateGradient(AppThemes.black)[7]),
                          borderRadius: BorderRadius.circular(AppThemes.borderRadiusSmall),
                        ),
                        child: Text(
                          '${ticketList.length}',
                          style: Theme.of(context).textTheme.displayMedium,
                        ),
                      );
                    },
                  ),
                  IconButton(
                    onPressed: () {
                      onAddTicket();
                    },
                    icon: const Icon(Icons.add),
                    color: AppThemes.green,
                  ),
                ],
              ),
              Text(
                '\$$price',
                style: Theme.of(context).textTheme.titleMedium,
              ),
            ],
          ),
          const SizedBox(height: 16.0),
          ValueListenableBuilder<List<Map<String, dynamic>>>(
            valueListenable: ticketDetailsNotifier,
            builder: (context, ticketList, child) {
              return Column(
                children: List.generate(ticketList.length, (index) {
                  return GestureDetector(
                    onTap: () {
                      showModalBottomSheet(
                        context: context,
                        builder: (_) => TicketDetailsWidget(
                          ticketIndex: index,
                          ticketTypeName: name,
                          ticketDetailsNotifier: ticketDetailsNotifier,
                          onUpdateTicket: onUpdateTicket,
                        ),
                      );
                    },
                    child: Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        IconButton(
                          onPressed: () {
                            showModalBottomSheet(
                              context: context,
                              builder: (_) => TicketDetailsWidget(
                                ticketIndex: index,
                                ticketTypeName: name,
                                ticketDetailsNotifier: ticketDetailsNotifier,
                                onUpdateTicket: onUpdateTicket,
                              ),
                            );
                          },
                          icon: const Icon(Icons.edit),
                          color: AppThemes.primary,
                        ),
                        Expanded(
                          child: Container(
                            margin: const EdgeInsets.symmetric(vertical: 4.0),
                            padding: const EdgeInsets.all(8.0),
                            decoration: BoxDecoration(
                              color: AppThemes.generateGradient(AppThemes.primary)[8],
                              borderRadius: BorderRadius.circular(AppThemes.borderRadius),
                              border: Border.all(
                                color: AppThemes.generateGradient(AppThemes.primary)[6],
                                width: 1.0,
                              ),
                            ),
                            child: Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                Flexible(
                                  child: Text(
                                    '${ticketList[index]["firstName"]} ${ticketList[index]["lastName"]}',
                                    overflow: TextOverflow.ellipsis,
                                    style: const TextStyle(
                                      fontSize: 14.0,
                                      color: AppThemes.black,
                                      fontWeight: FontWeight.bold,
                                    ),
                                  ),
                                ),
                                Text(
                                  _ticketDetailsIncomplete(ticketList[index]) ? AppLocalizations.of(context)!.incomplete : '',
                                  style: const TextStyle(
                                    fontSize: 10.0,
                                    color: AppThemes.red,
                                    fontWeight: FontWeight.bold,
                                  ),
                                ),
                              ]
                            )
                          ),
                        ),
                        IconButton(
                          onPressed: () {
                            onRemoveTicket(index);
                          },
                          icon: const Icon(Icons.remove),
                          color: AppThemes.red,
                        ),
                      ],
                    ),
                  );
                }),
              );
            },
          ),
        ],
      ),
    );
  }
}
