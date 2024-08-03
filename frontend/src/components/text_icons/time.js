import Icon from "./icon";

const TimeIcon = ({text}) => {
  const formatTime = (timeString) => {
    if(timeString === undefined || timeString === null)
      return "null";
    const [hours, minutes] = timeString.split(':');
    const date = new Date();
    date.setHours(hours);
    date.setMinutes(minutes);

    const options = {
      hour: 'numeric',
      minute: 'numeric',
      hour12: true,
    };

    return date.toLocaleTimeString([], options);
  }

  return (
      <Icon symbol="schedule" text={formatTime(text)} />
  )
}

export default TimeIcon