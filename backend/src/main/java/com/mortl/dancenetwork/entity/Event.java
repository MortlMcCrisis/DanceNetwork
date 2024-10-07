package com.mortl.dancenetwork.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table
public class Event
{

  public Event()
  {
  }

  public Event(Long id, String name, UUID creator, String email, LocalDate startDate,
      LocalTime startTime, LocalDate endDate, String location, String website, String profileImage,
      String bannerImage, boolean published, LocalDateTime createdAt)
  {
    this.id = id;
    this.creator = creator;
    this.profileImage = profileImage;
    this.bannerImage = bannerImage;
    this.startDate = startDate;
    this.startTime = startTime;
    this.endDate = endDate;
    this.name = name;
    this.location = location;
    this.website = website;
    this.email = email;
    this.published = published;
    this.createdAt = createdAt;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  private UUID creator;

  private String profileImage;

  private String bannerImage;

  @Column(nullable = false)
  private LocalDate startDate;

  private LocalTime startTime;

  private LocalDate endDate;

  @Column(nullable = false)
  private String name;

  private String location;

  private String website;

  private String email;

  private boolean published;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public UUID getCreator()
  {
    return creator;
  }

  public void setCreator(UUID creator)
  {
    this.creator = creator;
  }

  public String getProfileImage()
  {
    return profileImage;
  }

  public void setProfileImage(String profileImage)
  {
    this.profileImage = profileImage;
  }

  public String getBannerImage()
  {
    return bannerImage;
  }

  public void setBannerImage(String bannerImage)
  {
    this.bannerImage = bannerImage;
  }

  public LocalDate getStartDate()
  {
    return startDate;
  }

  public void setStartDate(LocalDate startDate)
  {
    this.startDate = startDate;
  }

  public LocalTime getStartTime()
  {
    return startTime;
  }

  public void setStartTime(LocalTime startTime)
  {
    this.startTime = startTime;
  }

  public LocalDate getEndDate()
  {
    return endDate;
  }

  public void setEndDate(LocalDate endDate)
  {
    this.endDate = endDate;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getLocation()
  {
    return location;
  }

  public void setLocation(String location)
  {
    this.location = location;
  }

  public String getWebsite()
  {
    return website;
  }

  public void setWebsite(String website)
  {
    this.website = website;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public boolean isPublished()
  {
    return published;
  }

  public void setPublished(boolean published)
  {
    this.published = published;
  }

  public LocalDateTime getCreatedAt()
  {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt)
  {
    this.createdAt = createdAt;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (o == null || getClass() != o.getClass())
    {
      return false;
    }
    Event event = (Event) o;
    return published == event.published && Objects.equals(id, event.id)
        && Objects.equals(creator, event.creator) && Objects.equals(profileImage,
        event.profileImage) && Objects.equals(bannerImage, event.bannerImage)
        && Objects.equals(startDate, event.startDate) && Objects.equals(startTime,
        event.startTime) && Objects.equals(endDate, event.endDate)
        && Objects.equals(name, event.name) && Objects.equals(location,
        event.location) && Objects.equals(website, event.website)
        && Objects.equals(email, event.email) && Objects.equals(createdAt,
        event.createdAt);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id, creator, profileImage, bannerImage, startDate, startTime, endDate, name,
        location, website, email, published, createdAt);
  }

  @Override
  public String toString()
  {
    return "Event{" +
        "id=" + id +
        ", creator=" + creator +
        ", profileImage='" + profileImage + '\'' +
        ", bannerImage='" + bannerImage + '\'' +
        ", startDate=" + startDate +
        ", startTime=" + startTime +
        ", endDate=" + endDate +
        ", name='" + name + '\'' +
        ", location='" + location + '\'' +
        ", website='" + website + '\'' +
        ", email='" + email + '\'' +
        ", published=" + published +
        ", createdAt=" + createdAt +
        '}';
  }
}
