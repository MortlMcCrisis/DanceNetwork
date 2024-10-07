import Card from "../Card";
import {Link} from "react-router-dom";
import paypal from "../../assets/images/paypal/checkout-logo-large-de.png";
import visa from "../../assets/images/credit-card-logos/visa-small.png";
import mastercard
  from "../../assets/images/credit-card-logos/master-card-small.png";
import DanceFormInput from "../../views/dashboard/app/dance-form-input";
import React, {useCallback} from "react";
import {loadStripe} from "@stripe/stripe-js";
import {
  EmbeddedCheckout,
  EmbeddedCheckoutProvider
} from "@stripe/react-stripe-js";
import {PAYMENTS_OPEN_ENDPOINT} from "../util/network";

const stripePromise = loadStripe("pk_test_51Q4KA0K0od2j0zBCi6XBYdcIh7IR2vZuXwAPr2CGU5aedZwy8gjLD0oGWOKR3OBn8V9KfqEO2cNumdSVxXZvAzPf00a7Abc81N");
const DanceBuyTicketsPayment = ({formData}) => {

  //TODO darf erst aufgerufen nachdem die tickets und infos ausgewählt und ausgefüllt wurden
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

    return fetch(`${PAYMENTS_OPEN_ENDPOINT}/create-checkout-session`, {
      method: "POST",
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(requestData)
    })
    .then((res) => res.json())
    .then((data) => data.clientSecret);
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
        {/*
        <div className="d-flex align-items-center justify-content-center">
          <Link to="#"><img loading="lazy" src={paypal} alt="gallary-img" className="img-fluid rounded"/></Link>
        </div>
        <hr/>
        <div className="d-flex justify-content-end">
          <img loading="lazy" src={visa} alt="gallary-img" className="img-fluid rounded"/>
          <img loading="lazy" src={mastercard} alt="gallary-img" className="img-fluid rounded"/>
        </div>
        <br/>
        <DanceFormInput id="card-number" label="Card Number: *" placeholder="XXXX XXXX XXXX XXXX" type="text" />
        <DanceFormInput id="name-on-card" label="Name on card: *" placeholder="Enter the name on the card" type="text" />
        <DanceFormInput id="expire-date" label="Expire Date: *" placeholder="XX/XX" type="text" />
        <DanceFormInput id="cvc" label="Security code: *" placeholder="CVC" type="text" />
        */}
      </Card.Body>
    </Card>
  )
}

export default DanceBuyTicketsPayment;