class Event {
  final int eventId;
  final String creator;
  final String profileImageUrl;
  final String bannerImageUrl;
  final String startDate;
  final String? endDate;
  final String startTime;
  final String title;
  final String location;
  final String website;
  final String email;

  Event({
    required this.eventId,
    required this.creator,
    required this.profileImageUrl,
    required this.bannerImageUrl,
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
      creator: event['creator'] ?? "",
      profileImageUrl: event['profileImage'] ?? "",
      bannerImageUrl:  event['bannerImage'] ?? "",
      startDate: event['startDate'] ?? "Unknown date",
      endDate: event['endDate'],
      startTime: event['startTime'] ?? "Unknown time",
      title: event['name'] ?? "",
      location: event['location'] ?? "",
      website: event['website'] ?? "",
      email: event['email'] ?? "",
    );
  }
}
