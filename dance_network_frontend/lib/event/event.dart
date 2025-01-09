class Event {
  final int eventId;
  final String imageUrl;
  final String startDate;
  final String endDate;
  final String startTime;
  final String title;
  final String location;
  final String website;
  final String email;

  Event({
    required this.eventId,
    required this.imageUrl,
    required this.startDate,
    required this.endDate,
    required this.startTime,
    required this.title,
    required this.location,
    required this.website,
    required this.email,
  });

  factory Event.fromMap(Map<String, dynamic> event) {
    return Event(
      eventId: event['id'] ?? 0,
      imageUrl: event['profileImage'] ?? "",
      startDate: event['startDate'] ?? "Unknown date",
      endDate: event['endDate'] ?? "Unknown date",
      startTime: event['startTime'] ?? "Unknown time",
      title: event['name'] ?? "Untitled",
      location: event['location'] ?? "Unknown location",
      website: event['website'] ?? "Unknown website",
      email: event['email'] ?? "Unknown email",
    );
  }
}
