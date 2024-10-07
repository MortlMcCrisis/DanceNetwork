package com.mortl.dancenetwork.service.impl;

import com.mortl.dancenetwork.entity.Ticket;
import com.mortl.dancenetwork.entity.TicketType;
import com.mortl.dancenetwork.service.IStripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.PriceListParams;
import com.stripe.param.PriceUpdateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.ProductCreateParams.DefaultPriceData;
import com.stripe.param.ProductListParams;
import com.stripe.param.ProductUpdateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.common.EmptyParam;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripeServiceImpl implements IStripeService
{

  private static final Logger log = LoggerFactory.getLogger(StripeServiceImpl.class);

  private static final String DOMAIN = "http://localhost:3000";

  private static final String PRICE_ID = "price_1Q4LKpK0od2j0zBCtJVHWfIA";

  @Value("${stripe.api-key}")
  private String apiKey;

  public StripeServiceImpl()
  {
    Stripe.apiKey = apiKey;
  }

  private void createProduct(long id, String name, String description, String url,
      MonetaryAmount amount)
      throws StripeException
  {
    ProductCreateParams params = ProductCreateParams.builder()
        .setId(String.valueOf(id))
        .setName(name)
        .setDescription(description)
        .setStatementDescriptor("") // TODO shown on credit card billing
        .setActive(false)
        .setUrl(url)
        .setDefaultPriceData(DefaultPriceData.builder()
            .setCurrency("eur")
            .setUnitAmount(toPriceInCents(amount))
            .build())
        .build();
    Product.create(params);
  }

  private String createPrice(String productId, MonetaryAmount amount) throws StripeException
  {
    PriceCreateParams params =
        PriceCreateParams.builder()
            .setCurrency("eur")
            .setActive(true)
            .setUnitAmount(toPriceInCents(amount))
            .setProduct(productId)
            .build();
    return Price.create(params).getId();
  }

  @Override
  public void syncTicketTypes(List<TicketType> newTicketTypes)
      throws StripeException
  {
    if (newTicketTypes.isEmpty())
    {
      return;
    }

    ProductListParams listParams = ProductListParams.builder()
        .setUrl(newTicketTypes.get(0).getEventUrl())
        .build();

    Set<Long> updatedIds = new HashSet<>();
    for (Product product : Product.list(listParams).autoPagingIterable())
    {
      Optional<TicketType> newTicketTypeOptional = newTicketTypes.stream()
          .filter(ticketType -> ticketType.getId() == Long.parseLong(product.getId()))
          .findAny();

      if (newTicketTypeOptional.isEmpty())
      {
        deleteProduct(product);
        log.info("deleted product '" + product.getName() + "' with id " + product.getId());
      }
      else
      {
        TicketType newTicketType = newTicketTypeOptional.get();
        Optional<String> newPriceId = updatePrice(product, newTicketType);
        updateProduct(product, newTicketType, newPriceId);

        updatedIds.add(newTicketType.getId());
      }
    }
    createNotExistingProducts(newTicketTypes, updatedIds);
  }

  private void updateProduct(Product product, TicketType newTicketType, Optional<String> newPriceId)
      throws StripeException
  {
    boolean isDirty = false;

    ProductUpdateParams.Builder updateParamsBuilder = ProductUpdateParams.builder();

    if (product.getName() != newTicketType.getName())
    {
      updateParamsBuilder.setName(newTicketType.getName());
      isDirty = true;
    }

    if (product.getDescription() != newTicketType.getDescription())
    {
      updateParamsBuilder.setDescription(newTicketType.getDescription());
      isDirty = true;
    }

    if (newPriceId.isPresent())
    {
      updateParamsBuilder.setDefaultPrice(newPriceId.get());
      isDirty = true;
    }

    if (!product.getActive())
    {
      updateParamsBuilder.setActive(true);
      isDirty = true;
    }

    if (isDirty)
    {
      product.update(updateParamsBuilder.build());
      log.info("updated product '" + product.getName() + "' with id " + product.getId());
    }
  }

  private Optional<String> updatePrice(Product product, TicketType newTicketType)
      throws StripeException
  {
    //TODO change price in ticketType to monetary amount
    MonetaryAmount monetaryAmount = Money.of(newTicketType.getPrice(),
        Monetary.getCurrency("EUR"));

    if (product.getDefaultPrice() == null)
    {
      String newPriceId = createPrice(product.getId(), monetaryAmount);
      return Optional.of(newPriceId);
    }
    else
    {
      Price oldPrice = Price.retrieve(product.getDefaultPrice());

      if (oldPrice.getUnitAmount() != toPriceInCents(monetaryAmount))
      {
        String newPriceId = createPrice(product.getId(), monetaryAmount);
        PriceUpdateParams params =
            PriceUpdateParams.builder()
                .setActive(false)
                .build();
        oldPrice.update(params);

        log.info("updated price of product " + product.getId());

        return Optional.of(newPriceId);
      }
      return Optional.empty();
    }
  }

  private void createNotExistingProducts(
      List<TicketType> newTicketTypes,
      Set<Long> updatedIds)
      throws StripeException
  {
    for (TicketType ticketType : newTicketTypes)
    {
      if (!updatedIds.contains(ticketType.getId()))
      {
        MonetaryAmount monetaryAmount = Money.of(ticketType.getPrice(),
            Monetary.getCurrency("EUR"));
        createProduct(
            ticketType.getId(),
            ticketType.getName(),
            ticketType.getDescription(),
            ticketType.getEventUrl(),
            monetaryAmount);

        log.info("created product '" + ticketType.getName() + "' with id " + ticketType.getId());
      }
    }
  }

  private void deleteProduct(Product product) throws StripeException
  {
    ProductUpdateParams updateDefaultPricesParams = ProductUpdateParams.builder()
        .setDefaultPrice(EmptyParam.EMPTY)
        .build();
    product.update(updateDefaultPricesParams);

    PriceListParams priceListParams = PriceListParams.builder().setProduct(product.getId())
        .build();
    for (Price price : Price.list(priceListParams).autoPagingIterable())
    {
      PriceUpdateParams priceUpdateParams =
          PriceUpdateParams.builder()
              .setActive(false)
              .build();
      price.update(priceUpdateParams);
    }

    ProductUpdateParams updateParams = ProductUpdateParams.builder()
        .setActive(false)
        .build();

    product.update(updateParams);
    System.out.println("Product archived: " + product.getId());
  }

  private long toPriceInCents(MonetaryAmount amount)
  {
    return (long) (amount.getNumber().numberValueExact(Double.class) * 100);
  }

  @Override
  public Map<String, String> createSession(List<Ticket> tickets) throws StripeException
  {
    SessionCreateParams.Builder builder =
        SessionCreateParams.builder()
            .setUiMode(SessionCreateParams.UiMode.EMBEDDED)
            .setMode(SessionCreateParams.Mode.PAYMENT)
            // TODO create frontend url where I can redirect to
            .setReturnUrl(DOMAIN
                + "/dashboards/app/dance-event-detail/3/dance-buy-ticket-invoice?session_id={CHECKOUT_SESSION_ID}");
    for (Ticket ticket : tickets)
    {
      Product product = Product.retrieve(String.valueOf(ticket.getTicketType().getId()));

      builder.addLineItem(
          SessionCreateParams.LineItem.builder()
              .setQuantity(1L)
              .setPrice(product.getDefaultPrice())
              .build());
    }

    SessionCreateParams params = builder.build();

    Session session;
    try
    {
      session = Session.create(params);
    }
    catch (StripeException e)
    {
      throw new RuntimeException(e);
    }

    Map<String, String> map = new HashMap<>();
    map.put("clientSecret",
        session.getRawJsonObject().getAsJsonPrimitive("client_secret").getAsString());

    return map;
  }

  @Override
  public Map<String, String> sessionStatus(String sessionId) throws StripeException
  {
    Session session = Session.retrieve(sessionId);

    Map<String, String> map = new HashMap();
    map.put("status", session.getRawJsonObject().getAsJsonPrimitive("status").getAsString());
    map.put("customer_email",
        session.getRawJsonObject().getAsJsonObject("customer_details").getAsJsonPrimitive("email")
            .getAsString());

    return map;
  }

  @Override
  public void activateTickets(List<TicketType> ticketTypes)
      throws StripeException
  {
    if (ticketTypes.isEmpty())
    {
      return;
    }
    //TODO do this in one request
    syncTicketTypes(ticketTypes);

    ProductListParams listParams = ProductListParams.builder()
        .setUrl(ticketTypes.get(0).getEventUrl())
        .build();

    for (Product product : Product.list(listParams).autoPagingIterable())
    {
      ProductUpdateParams updateParams = ProductUpdateParams.builder()
          .setActive(true)
          .build();
      product.update(updateParams);
    }
  }
}
