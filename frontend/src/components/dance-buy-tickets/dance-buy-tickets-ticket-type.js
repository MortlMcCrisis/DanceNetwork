import { Col, OverlayTrigger, Row, Tooltip} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import Card from "../Card";
import TicketIcon from "../text_icons/ticket";
import DOMPurify from "quill/formats/link";

const DanceBuyTicketsTicketType = ({ticketType, setTicketType}) => {

  const [count, setCount] = useState(0);

  useEffect(() => {
    setTicketType(ticketType.id, count)
  }, [count]);

  return (
      <Card>
        <Card.Header className=" d-flex justify-content-between iq-border-bottom mb-0">
          <div className="header-title">
            <h4 className="card-title">{ticketType.name}</h4>
          </div>
        </Card.Header>
        <Card.Body>
          <div className="checkout-product">
            <Row className=" align-items-center">
              <Col sm="7">
                <div className="checkout-product-details" dangerouslySetInnerHTML={{ __html: DOMPurify.sanitize(ticketType.description) }}>
                </div>
              </Col>
              <Col sm="5">
                <Row>
                  <Col sm="12" className="col-12">
                    <Row className=" align-items-center ">
                      <Col sm="6" md="6" className="col-6">
                        <div className="quantity buttons_added">
                          <input type="button" defaultValue="-" className="minus h5" onClick={() => setCount(count > 0 ? count - 1 : 0)} />
                          <input type="button" defaultValue={count} title="Qty" className="input-text qty text ms-1"  />
                          <input type="button" defaultValue="+" className="plus h5" onClick={() => setCount(count + 1)}/>
                        </div>
                      </Col>
                      <Col sm="3" md="3" className="col-3">
                        <span className="product-price">${ticketType.price.number}</span>
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

export default DanceBuyTicketsTicketType