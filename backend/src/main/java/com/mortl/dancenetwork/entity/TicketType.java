package com.mortl.dancenetwork.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "ticket-types")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketType {

  @Id
  @GeneratedValue
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  @NonNull
  private String name;

  @Column(nullable = false, length = 1000)
  @NonNull
  private String description;

  @Column(nullable = false)
  @NonNull
  private Float price;

  //TODO lazy loading verwenden? Dann muss transaktionsverwaltung verwendet werden und sichergestellt, dass beim zugriff auf das event die transaktion noch aktiv ist
  @ManyToOne
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;
}
