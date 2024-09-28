package com.mortl.dancenetwork.entity;

import com.mortl.dancenetwork.enums.NewsfeedEntryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table
public class NewsfeedEntry {

  public NewsfeedEntry() {
  }

  public NewsfeedEntry(Long id, NewsfeedEntryType type, UUID creator, String textField,
      String image,
      LocalDateTime creationDate) {
    this.id = id;
    this.type = type;
    this.creator = creator;
    this.textField = textField;
    this.image = image;
    this.creationDate = creationDate;
  }

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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public NewsfeedEntryType getType() {
    return type;
  }

  public void setType(NewsfeedEntryType type) {
    this.type = type;
  }

  public UUID getCreator() {
    return creator;
  }

  public void setCreator(UUID creator) {
    this.creator = creator;
  }

  public String getTextField() {
    return textField;
  }

  public void setTextField(String textField) {
    this.textField = textField;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public LocalDateTime getCreationDate() {
    return creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    NewsfeedEntry that = (NewsfeedEntry) o;
    return Objects.equals(id, that.id) && type == that.type && Objects.equals(
        creator, that.creator) && Objects.equals(textField, that.textField)
        && Objects.equals(image, that.image) && Objects.equals(creationDate,
        that.creationDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, creator, textField, image, creationDate);
  }

  @Override
  public String toString() {
    return "NewsfeedEntry{" +
        "id=" + id +
        ", type=" + type +
        ", creator=" + creator +
        ", textField='" + textField + '\'' +
        ", image='" + image + '\'' +
        ", creationDate=" + creationDate +
        '}';
  }
}
