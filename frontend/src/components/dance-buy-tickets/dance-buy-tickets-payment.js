import Card from "../Card";
import React, {useCallback} from "react";
import {loadStripe} from "@stripe/stripe-js";
import {
  EmbeddedCheckout,
  EmbeddedCheckoutProvider
} from "@stripe/react-stripe-js";
import {
  PAYMENTS_OPEN_ENDPOINT,
  postData
} from "../util/network";

const stripePromise = loadStripe("pk_test_51Q4KA0K0od2j0zBCi6XBYdcIh7IR2vZuXwAPr2CGU5aedZwy8gjLD0oGWOKR3OBn8V9KfqEO2cNumdSVxXZvAzPf00a7Abc81N");
const DanceBuyTicketsPayment = ({formData, keycloak}) => {

  const fetchClientSecret = useCallback(() => {
    let tickets = [];
    formData.forEach(obj => {
      const newObj = {};
      for (const key in obj) {
        if (key !== 'ticketTypeName' && key !== 'emailConfirm') {
          newObj[key] = obj[key];
        }
      }
      tickets.push(newObj);
    });
    let requestData = {
      tickets: tickets
    }

    console.log(requestData)

    return postData(`${PAYMENTS_OPEN_ENDPOINT}/create-checkout-session`, requestData, keycloak.token)
      .then((res) => res.json())
      .then((data) => data.clientSecret);;
  }, []);

  const options = {fetchClientSecret};

  return (
    <Card>
      <Card.Header>
        <h4>Choose payment method</h4>
      </Card.Header>
      <Card.Body>
        <div id="checkout">
          <EmbeddedCheckoutProvider
              stripe={stripePromise}
              options={options}
          >
            <EmbeddedCheckout />
          </EmbeddedCheckoutProvider>
        </div>
      </Card.Body>
    </Card>
  )
}

export default DanceBuyTicketsPayment;