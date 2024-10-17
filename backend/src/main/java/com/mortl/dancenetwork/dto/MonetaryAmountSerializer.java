package com.mortl.dancenetwork.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import javax.money.MonetaryAmount;

public class MonetaryAmountSerializer extends JsonSerializer<MonetaryAmount>
{

  @Override
  public void serialize(MonetaryAmount value, JsonGenerator gen, SerializerProvider serializers)
      throws IOException
  {
    gen.writeStartObject();
    gen.writeStringField("currency", value.getCurrency().getCurrencyCode());
    gen.writeNumberField("number", value.getNumber().doubleValueExact());
    gen.writeEndObject();
  }
}
