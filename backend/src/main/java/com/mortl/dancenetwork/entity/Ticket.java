package com.mortl.dancenetwork.entity;

import com.mortl.dancenetwork.enums.DancingRole;
import com.mortl.dancenetwork.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table
public class Ticket
{

  public Ticket()
  {
  }

  public Ticket(Long id, UUID owner, TicketType ticketType, String firstName, String lastName,
      String address, String country, String email, String phone, DancingRole dancingRole,
      Gender gender, TicketOrder ticketOrder)
  {
    this.id = id;
    this.owner = owner;
    this.ticketType = ticketType;
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.country = country;
    this.email = email;
    this.phone = phone;
    this.dancingRole = dancingRole;
    this.gender = gender;
    this.ticketOrder = ticketOrder;
  }

  @Id
  @GeneratedValue
  @Column(nullable = false)
  private Long id;

  private UUID owner;

  @ManyToOne
  @JoinColumn(name = "ticket_type_id", nullable = false)
  private TicketType ticketType;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String country;

  @Column(nullable = false)
  private String email;

  private String phone;

  @Column(nullable = false)
  private DancingRole dancingRole;

  @Column(nullable = false)
  private Gender gender;

  @ManyToOne
  @JoinColumn(name = "ticket_order_id", nullable = false)
  private TicketOrder ticketOrder;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public UUID getOwner()
  {
    return owner;
  }

  public void setOwner(UUID owner)
  {
    this.owner = owner;
  }

  public TicketType getTicketType()
  {
    return ticketType;
  }

  public void setTicketType(TicketType ticketType)
  {
    this.ticketType = ticketType;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getCountry()
  {
    return country;
  }

  public void setCountry(String country)
  {
    this.country = country;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public DancingRole getDancingRole()
  {
    return dancingRole;
  }

  public void setDancingRole(DancingRole dancingRole)
  {
    this.dancingRole = dancingRole;
  }

  public Gender getGender()
  {
    return gender;
  }

  public void setGender(Gender gender)
  {
    this.gender = gender;
  }

  public TicketOrder getOrder()
  {
    return ticketOrder;
  }

  public void setOrder(TicketOrder ticketOrder)
  {
    this.ticketOrder = ticketOrder;
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
    Ticket ticket = (Ticket) o;
    return Objects.equals(id, ticket.id) && Objects.equals(owner, ticket.owner)
        && Objects.equals(ticketType, ticket.ticketType) && Objects.equals(
        firstName, ticket.firstName) && Objects.equals(lastName, ticket.lastName)
        && Objects.equals(address, ticket.address) && Objects.equals(country,
        ticket.country) && Objects.equals(email, ticket.email) && Objects.equals(
        phone, ticket.phone) && dancingRole == ticket.dancingRole && gender == ticket.gender
        && Objects.equals(ticketOrder, ticket.ticketOrder);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id, owner, ticketType, firstName, lastName, address, country, email, phone,
        dancingRole, gender, ticketOrder);
  }

  @Override
  public String toString()
  {
    return "Ticket{" +
        "id=" + id +
        ", owner=" + owner +
        ", ticketType=" + ticketType +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", address='" + address + '\'' +
        ", country='" + country + '\'' +
        ", email='" + email + '\'' +
        ", phone='" + phone + '\'' +
        ", dancingRole=" + dancingRole +
        ", gender=" + gender +
        ", order=" + ticketOrder +
        '}';
  }
}
