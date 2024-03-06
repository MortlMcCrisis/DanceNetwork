import { Col, OverlayTrigger, Row, Tooltip} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import Card from "../Card";

const DanceTicket = ({ticketType, setTicketType}) => {

  const [count, setCount] = useState(0);

  useEffect(() => {
    setTicketType(ticketType.id, count)
  }, [count]);

  return (
      <Card>
        <Card.Header className=" d-flex justify-content-between iq-border-bottom mb-0">
          <div className="header-title">
            <h4 className="card-title">Full Pass {ticketType.name}</h4>
          </div>
        </Card.Header>
        <Card.Body>
          <div className="checkout-product">
            <Row className=" align-items-center">
              <Col sm="7">
                <div className="checkout-product-details">
                  <ul>
                    <li>All workshops (Master classes not included)</li>
                    <li>All parties</li>
                    <li>All socials</li>
                    <li>{ticketType.description}</li>
                  </ul>
                </div>
              </Col>
              <Col sm="5">
                <Row>
                  <Col sm="12" className="col-12">
                    <Row className=" align-items-center ">
                      <Col sm="6" md="6" className="col-6">
                        <div className="quantity buttons_added">
                          <input type="button" defaultValue="-" className="minus h5" onClick={() => setCount(count - 1)} />
                          <input type="button" defaultValue={count} title="Qty" className="input-text qty text ms-1"  />
                          <input type="button" defaultValue="+" className="plus h5" onClick={() => setCount(count + 1)}/>
                        </div>
                      </Col>
                      <Col sm="3" md="3" className="col-3">
                        <span className="product-price">${ticketType.price}</span>
                      </Col>
                      <Col sm="3" md="3" className="col-3">
                        <OverlayTrigger placement="top" overlay={
                            <Tooltip >
                              <s>5€ until 30 of nov</s><br/><s>70€ until 1 of dec</s><br/>76€ until 4 of dec
                            </Tooltip>
                          }>
                          <span className="material-symbols-outlined md-18">info</span>
                        </OverlayTrigger>{' '}
                      </Col>
                    </Row>
                  </Col>
                </Row>
              </Col>
            </Row>
          </div>
        </Card.Body>
      </Card>
  )
}

export default DanceTicket