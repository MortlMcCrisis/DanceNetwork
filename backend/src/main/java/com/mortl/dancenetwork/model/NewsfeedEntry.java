package com.mortl.dancenetwork.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;

@Entity
@Table(name = "newsfeed-entry")
@Data
public class NewsfeedEntry {

  @Id
  @GeneratedValue
  private Long id;

  private String userName;

  @Column(length = 1000)
  private String textField;

  private Date creationDate;
}
