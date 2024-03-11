export const TICKET_STEP = 1;
export const PERSONAL_DATA_STEP = 2;
export const PAYMENT_STEP = 3
export const FINISH_STEP = 4

export const getPriceForId = (ticketTypes, id) => {
  if( ticketTypes === undefined)
    return 0;

  const ticketType =  ticketTypes.find((ticketType) => ticketType.id == id);

  if (ticketType && ticketType.price !== undefined) {
    return ticketType.price;
  }
};

export const getNameForId = (ticketTypes, id) => {
  if( ticketTypes === undefined)
    return '';

  const ticketType =  ticketTypes.find((ticketType) => ticketType.id == id);

  if (ticketType && ticketType.name !== undefined) {
    return ticketType.name;
  }
};

export const calculateTotal = (ticketTypes, selectedTicketTypes) => {
  if( selectedTicketTypes === undefined)
    return 0;

  let total = 0;
  Object.entries(selectedTicketTypes).forEach(([ticketTypeId, quantity]) => {
    total += getPriceForId(ticketTypes, ticketTypeId)*quantity
  });

  return total.toFixed(2);
}

export const getAmountOfTickets = (selectedTicketTypes) => {
  if( selectedTicketTypes === undefined)
    return 0;

  let total = 0;
  Object.entries(selectedTicketTypes).forEach(([_, quantity]) => {
    total += quantity
  });

  return total;
}