import React, {useEffect, useState} from "react";
import { Container, Row, Col } from "react-bootstrap";
import Card from "../../../components/Card";

import {
  fetchData,
  TICKETS_CLOSED_ENDPOINT
} from "../../../components/util/network";
import {useKeycloak} from "@react-keycloak/web";
import PersonIcon from "../../../components/text_icons/person";
import AddressIcon from "../../../components/text_icons/address";
import TicketIcon from "../../../components/text_icons/ticket";
import LocationIcon from "../../../components/text_icons/location";
import DateIcon from "../../../components/text_icons/date";
const DanceTickets = () => {

  const { keycloak, initialized } = useKeycloak();

  const [ticketInfos, setTicketInfos] = useState([]);

  useEffect(() => {
    if(keycloak.authenticated) {
      fetchData(`${TICKETS_CLOSED_ENDPOINT}/infos`, setTicketInfos, keycloak.token);
    }

    console.log(ticketInfos)
  }, [keycloak.authenticated]);

  return (
    <>
      <div id="content-page" className="content-page">
        <Container>
          <Row>
            <div className="mb-2">
              <h4>Tickets</h4>
            </div>
            {ticketInfos.map((ticketInfo, idx) => (
                <Col sm="6" md="4" key={idx}>
                  <Card className="cardhover">
                    <Card.Body>
                      <div className="text-center">
                        <img
                            src={`/qrcodes/code${ticketInfo.ticket.id}.png`}
                            alt="mimg"
                            className="avatar job-icon mb-2 d-inline-block"
                            loading="lazy"
                        />
                      </div>
                      <h3>{ticketInfo.event.name}</h3>
                      <p>{ticketInfo.ticketType.description}</p>
                      <hr/>
                      <h5>Event</h5>
                      <DateIcon startDate={ticketInfo.event.startDate} endDate={ticketInfo.event.endDate} />
                      <LocationIcon text={ticketInfo.event.location} />
                      <hr/>
                      <h5>Attendee</h5>
                      <PersonIcon text={`${ticketInfo.ticket.firstName} ${ticketInfo.ticket.lastName}`} />
                      <AddressIcon text={ticketInfo.ticket.address} />
                      <hr/>
                      <TicketIcon text={ticketInfo.ticket.id} />
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
