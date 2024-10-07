package com.mortl.dancenetwork.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Objects;

@Entity
@Table
public class TicketType
{

  public TicketType()
  {
  }

  public TicketType(Long id, String name, String description, Float price, Long contingent,
      Event event)
  {
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.contingent = contingent;
    this.event = event;
  }

  @Id
  @GeneratedValue
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false, length = 1000)
  private String description;

  @Column(nullable = false)
  private Float price;

  @Column(nullable = false)
  private Long contingent;

  //TODO lazy loading verwenden? Dann muss transaktionsverwaltung verwendet werden und sichergestellt, dass beim zugriff auf das event die transaktion noch aktiv ist
  @ManyToOne
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public Float getPrice()
  {
    return price;
  }

  public void setPrice(Float price)
  {
    this.price = price;
  }

  public Long getContingent()
  {
    return contingent;
  }

  public void setContingent(Long contingent)
  {
    this.contingent = contingent;
  }

  public Event getEvent()
  {
    return event;
  }

  public void setEvent(Event event)
  {
    this.event = event;
  }

  public String getEventUrl()
  {
    //TODO must be parameterised in prod env
    return "https://dance-network.com/dashboards/app/dance-event-detail/" + id;
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
    TicketType that = (TicketType) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name)
        && Objects.equals(description, that.description) && Objects.equals(price,
        that.price) && Objects.equals(contingent, that.contingent)
        && Objects.equals(event, that.event);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id, name, description, price, contingent, event);
  }

  @Override
  public String toString()
  {
    return "TicketType{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", price=" + price +
        ", contingent=" + contingent +
        ", event=" + event +
        '}';
  }
}
