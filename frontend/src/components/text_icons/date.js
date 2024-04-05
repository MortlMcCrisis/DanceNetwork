import Icon from "./icon";
import {format} from "date-fns";

const DateIcon = ({startDate, endDate}) => {
  return (
      <Icon symbol="date_range" text={`${startDate ? format(new Date(startDate), "MMMM d, yyyy") : ''}${endDate ? ` - ${format(new Date(endDate), "MMMM d, yyyy")}` : ''}`} />
  )
}

export default DateIcon