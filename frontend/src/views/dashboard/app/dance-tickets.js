import React, {useEffect, useState} from "react";
import { Container, Row, Col } from "react-bootstrap";
import Card from "../../../components/Card";

import {
  EVENTS_ENDPOINT,
  fetchData,
  fetchDataWithBody,
  TICKETS_ENDPOINT
} from "../../../components/util/network";
import {useKeycloak} from "@react-keycloak/web";
import PersonIcon from "../../../components/text_icons/person";
import AddressIcon from "../../../components/text_icons/address";
import TicketIcon from "../../../components/text_icons/ticket";
import LocationIcon from "../../../components/text_icons/location";
import DateIcon from "../../../components/text_icons/date";
import {format} from "date-fns";
const DanceTickets = () => {

  const { keycloak, initialized } = useKeycloak();

  const [tickets, setTickets] = useState([]);

  const [events, setEvents] = useState([]);

  useEffect(() => {
    if(keycloak.authenticated) {
      fetchData(TICKETS_ENDPOINT, keycloak.token, setTickets);
    }

    console.log(tickets)
  }, [keycloak.authenticated]);

  //TODO remove endpoint
  /*useEffect(() => {
    if (keycloak.authenticated && tickets.length > 0) {
      const ticketTypeIds = tickets.map(ticket => ticket.ticketTypeId);
      fetchData(`${EVENTS_ENDPOINT}?ticketTypeIds=${ticketTypeIds.join(',')}`, keycloak.token, setEvents);
    }
  }, [tickets]);*/

  return (
    <>
      <div id="content-page" className="content-page">
        <Container>
          <Row>
            <div className="mb-2">
              <h4>Tickets</h4>
            </div>
            {tickets.map((ticket, idx) => (
                <Col sm="6" md="4" key={idx}>
                  <Card className="cardhover">
                    <Card.Body>
                      <div className="text-center">
                        <img
                            src={`/qrcodes/code${ticket.ticketId}.png`}
                            alt="mimg"
                            className="avatar job-icon mb-2 d-inline-block"
                            loading="lazy"
                        />
                      </div>
                      <h3>{ticket.eventData.name}</h3>
                      <p>{ticket.ticketDescription}</p>
                      <hr/>
                      <h5>Event</h5>
                      <DateIcon startDate={ticket.eventData.startDate} endDate={ticket.eventData.endDate} />
                      <LocationIcon text={ticket.eventData.location} />
                      <hr/>
                      <h5>Attendee</h5>
                      <PersonIcon text={`${ticket.ticketData.firstName} ${ticket.ticketData.lastName}`} />
                      <AddressIcon text={ticket.ticketData.address} />
                      <hr/>
                      <TicketIcon text={ticket.ticketId} />
                    </Card.Body>
                  </Card>
                </Col>
            ))}
          </Row>
        </Container>
      </div>
    </>
  );
};
export default DanceTickets;
