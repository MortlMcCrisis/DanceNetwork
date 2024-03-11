import Card from "../Card";
import {Link} from "react-router-dom";
import paypal from "../../assets/images/paypal/checkout-logo-large-de.png";
import visa from "../../assets/images/credit-card-logos/visa-small.png";
import mastercard
  from "../../assets/images/credit-card-logos/master-card-small.png";
import DanceFormInput from "../../views/dashboard/app/dance-form-input";
import React from "react";

const DanceBuyTicketsPayment = () => {
  return (
    <Card>
      <Card.Header>
        <h4>Choose payment method</h4>
      </Card.Header>
      <Card.Body>
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
      </Card.Body>
    </Card>
  )
}

export default DanceBuyTicketsPayment;