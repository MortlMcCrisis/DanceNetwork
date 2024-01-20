package com.mortl.dancenetwork.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
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
  private Long id; // TODO to UUID

  private String userName;

  @Column(length = 1000)
  private String textField;

  private Date creationDate;
}
