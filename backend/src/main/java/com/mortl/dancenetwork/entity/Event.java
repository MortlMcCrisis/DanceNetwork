package com.mortl.dancenetwork.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
  @NonNull
  private LocalDate startDate;

  private LocalTime startTime;

  private LocalDate endDate;

  @Column(nullable = false)
  @NonNull
  private String name;

  private String location;

  private String website;

  private String email;

  private boolean published;
}
