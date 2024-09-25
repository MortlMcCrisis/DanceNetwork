package com.mortl.dancenetwork.entity;

import com.mortl.dancenetwork.enums.Gender;
import com.mortl.dancenetwork.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
  private Role role;

  @Column(nullable = false)
  private Gender gender;

  @Column(nullable = false)
  private LocalDateTime buyDate;
}
