package com.mortl.dancenetwork.model;

import com.mortl.dancenetwork.dto.Gender;
import com.mortl.dancenetwork.dto.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "ticket")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

  @Id
  @GeneratedValue
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  private UUID owner;

  @ManyToOne
  @JoinColumn(name = "ticket_type_id", nullable = false)
  private TicketType ticketType;

  @Column(nullable = false)
  @NonNull
  private String firstName;

  @Column(nullable = false)
  @NonNull
  private String lastName;

  @Column(nullable = false)
  @NonNull
  private String address;

  @Column(nullable = false)
  @NonNull
  private String country;

  @Column(nullable = false)
  @NonNull
  private String email;

  private String phone;

  @Column(nullable = false)
  @NonNull
  private Role role;

  @Column(nullable = false)
  @NonNull
  private Gender gender;
}
