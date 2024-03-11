import Card from "../Card";
import {Link} from "react-router-dom";
import React from "react"

import * as Util from "./dance-buy-tickets-util";

const DanceBuyTicketsOrderDetails = ({state, selectedTicketTypes, ticketTypes, increaseState, decreaseState}) => {

  return (
      <Card>
        <Card.Body>
          <h5>Order Details</h5>
          <br/>
          <div className="row">
            <div className="col-3 d-flex justify-content-center">
              <b>Amount</b>
            </div>
            <div className="col-9 d-flex justify-content-between">
              <b>Ticket</b>
              <b>Price</b>
            </div>
          </div>
          {Object.entries(selectedTicketTypes).map(
              ([ticketTypeId, quantity], idx) => (
                  quantity > 0 &&
                  <div key={idx} className="row">
                    <div className="col-3 d-flex justify-content-center">
                      <span>{quantity}</span>
                    </div>
                    <div className="col-9 d-flex justify-content-between">
                      <span>{Util.getNameForId(ticketTypes, ticketTypeId)}</span>
                      <span>${Util.getPriceForId(ticketTypes, ticketTypeId)}</span>
                    </div>
                  </div>
              ))}
          <hr/>
          <div className="d-flex justify-content-between mb-4">
            <span className="text-dark"><strong>Total</strong></span>
            <span
                className="text-dark"><strong>${Util.calculateTotal(ticketTypes, selectedTicketTypes)}</strong></span>
          </div>
          <div className="row">
            <div className="col-sm-6">
              <Link id="place-order" to="#"
                    className={`btn btn-primary d-block mt-3 next ${state === Util.TICKET_STEP ? 'disabled' : ''}`}
                    onClick={decreaseState}>Back</Link>
            </div>
            <div className="col-sm-6">
              <Link id="place-order" to="#"
                    className="btn btn-primary d-block mt-3 next"
                    onClick={increaseState}>Next</Link>
            </div>
          </div>
        </Card.Body>
      </Card>
  )
}

export default DanceBuyTicketsOrderDetails