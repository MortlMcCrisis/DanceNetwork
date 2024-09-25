package com.mortl.dancenetwork.entity;

import com.mortl.dancenetwork.enums.NewsfeedEntryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "newsfeed-entry")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsfeedEntry {

  @Id
  @GeneratedValue
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  private NewsfeedEntryType type;

  @Column(nullable = false)
  private UUID creator;

  @Column(length = 10000, nullable = false)
  private String textField;

  private String image;

  @Column(nullable = false)
  private LocalDateTime creationDate;
}
