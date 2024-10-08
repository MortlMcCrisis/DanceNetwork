package com.mortl.dancenetwork.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;

@Converter(autoApply = true)
public class MonetaryAmountConverter implements AttributeConverter<MonetaryAmount, String>
{

  @Override
  public String convertToDatabaseColumn(MonetaryAmount monetaryAmount)
  {
    if (monetaryAmount == null)
    {
      return null;
    }
    // Speichere als String im Format "amount|currency" (z.B. "19.99|USD")
    return monetaryAmount.getNumber() + "|" + monetaryAmount.getCurrency().getCurrencyCode();
  }

  @Override
  public MonetaryAmount convertToEntityAttribute(String dbData)
  {
    if (dbData == null || dbData.isEmpty())
    {
      return null;
    }
    // Teile den String in Betrag und Währung auf
    String[] parts = dbData.split("\\|");
    if (parts.length != 2)
    {
      throw new IllegalArgumentException("Invalid database value for MonetaryAmount: " + dbData);
    }
    return Money.of(Double.parseDouble(parts[0]), parts[1]);
  }
}