package com.mortl.dancenetwork.model;

import com.mortl.dancenetwork.enums.Gender;
import java.util.Objects;
import java.util.UUID;

//TODO enforce uniqueness of username?
public class User{
  private UUID uuid;
  private String email;
  private String username;
  private String firstName;
  private String lastName;
  private Gender gender;
  private String phone;
  private String photoPath;

  public User(UUID uuid, String email, String username, String firstName, String lastName,
      Gender gender, String phone, String photoPath) {
    this.uuid = uuid;
    this.email = email;
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.phone = phone;
    this.photoPath = photoPath;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPhotoPath() {
    return photoPath;
  }

  public void setPhotoPath(String photoPath) {
    this.photoPath = photoPath;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(uuid, user.uuid) && Objects.equals(email, user.email)
        && Objects.equals(username, user.username) && Objects.equals(firstName,
        user.firstName) && Objects.equals(lastName, user.lastName) && gender == user.gender
        && Objects.equals(phone, user.phone) && Objects.equals(photoPath,
        user.photoPath);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid, email, username, firstName, lastName, gender, phone, photoPath);
  }

  @Override
  public String toString() {
    return "User{" +
        "uuid=" + uuid +
        ", email='" + email + '\'' +
        ", username='" + username + '\'' +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", gender=" + gender +
        ", phone='" + phone + '\'' +
        ", photoPath='" + photoPath + '\'' +
        '}';
  }
}
