package com.mortl.dancenetwork.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table
public class TicketOrder
{

  public TicketOrder()
  {
  }

  public TicketOrder(Long id, LocalDateTime buyDate)
  {
    this.id = id;
    this.buyDate = buyDate;
  }

  @Id
  @GeneratedValue
  @Column(nullable = false)
  private Long id;

  private LocalDateTime buyDate;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public LocalDateTime getBuyDate()
  {
    return buyDate;
  }

  public void setBuyDate(LocalDateTime buyDate)
  {
    this.buyDate = buyDate;
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
    TicketOrder ticketOrder = (TicketOrder) o;
    return Objects.equals(id, ticketOrder.id) && Objects.equals(buyDate, ticketOrder.buyDate);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id, buyDate);
  }

  @Override
  public String toString()
  {
    return "Order{" +
        "id=" + id +
        ", buyDate=" + buyDate +
        '}';
  }
}
