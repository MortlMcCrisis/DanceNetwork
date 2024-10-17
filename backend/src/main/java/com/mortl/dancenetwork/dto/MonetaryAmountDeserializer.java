package com.mortl.dancenetwork.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import java.io.IOException;
import java.math.BigDecimal;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;

public class MonetaryAmountDeserializer extends JsonDeserializer<MonetaryAmount>
{

  @Override
  public MonetaryAmount deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException
  {
    TreeNode node = p.getCodec().readTree(p);
    String currency = ((TextNode) node.get("currency")).asText();
    BigDecimal number = new BigDecimal(node.get("number").toString());
    return Money.of(number, currency);
  }
}