import Icon from "./icon";

const TicketIcon = ({text}) => {
  return (
      <Icon symbol="confirmation_number" text={`Ticket number: ${text}`} />
  )
}

export default TicketIcon