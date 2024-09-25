package com.mortl.dancenetwork.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

  @Id
  @GeneratedValue
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
}
