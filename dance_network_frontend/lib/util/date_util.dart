class DateUtil {

  static String _getMonthName(int month) {
    const months = [
      "Jan", "Feb", "Mar", "Apr", "May", "Jun",
      "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
    ];
    return months[month - 1];
  }

  static (String, String, String) extractDateComponents(String date) {
    final DateTime parsedDate = DateTime.parse(date);
    final String day = parsedDate.day.toString();
    final String month = _getMonthName(parsedDate.month);
    final String year = parsedDate.year.toString();

    return (day, month, year);
  }
}