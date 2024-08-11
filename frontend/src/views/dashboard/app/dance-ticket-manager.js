import {
  Button,
  Col,
  Container,
  Form,
  ListGroup,
  ListGroupItem,
  Row
} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import { useParams} from "react-router-dom";
import DanceTicketManagerTicket
  from "../../../components/dance-ticket-manager/dance-ticket-manager-ticket";
import {
  fetchData, putData, TICKET_TYPES_CLOSED_ENDPOINT,
  TICKET_TYPES_OPEN_ENDPOINT
} from "../../../components/util/network";
import {toast} from "react-toastify";
import {useKeycloak} from "@react-keycloak/web";
import Card from "../../../components/Card";

const DanceTicketManager = () => {

  const { keycloak, initialized } = useKeycloak();

  const { id } = useParams();

  const [ticketTypes, setTicketTypes] = useState([]);

  useEffect(() => {
    fetchData(`${TICKET_TYPES_OPEN_ENDPOINT}?eventId=${id}`, setTicketTypes);
  }, []);

  //TODO only submit when dirty
  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      console.log(ticketTypes);
      await putData(TICKET_TYPES_CLOSED_ENDPOINT, JSON.stringify(ticketTypes),
          keycloak.token);

      toast.success("Event successfully updated");
    } catch (error) {
      toast.error("Error updating event");
    }
  };

  const updateValue = (index, event) => {
    setTicketTypes((prevData) => prevData.map((item, i) =>
        i === index ? { ...item, [event.target.id]: event.target.value } : item
    ));
  };

  const updateDescription = (index, newDescription) => {
    setTicketTypes((prevData) => prevData.map((item, i) =>
        i === index ? { ...item, description: newDescription } : item
    ));
  };

  const addNewTicketType = () => {
    const newTicketType = {
      name: "New Ticket Type",
      description: "<p>New Description</p>",
      price: 100.00,
      eventId: id
    };

    setTicketTypes((prevTicketTypes) => [...prevTicketTypes, newTicketType]);
  };

  const removeTicketType = (index) => {
    setTicketTypes((prevTicketTypes) => prevTicketTypes.filter((_, i) => i !== index));
  };

  return (
      <>
        <div id="content-page" className="content-page">
          <Container>
            <Row>
              <Col sm="6" md="12">
                <Form onSubmit={handleSubmit}>
                  <Card>
                    <Card.Header>
                      <div className="mb-2">
                        <h4>Edit tickets</h4>
                      </div>
                    </Card.Header>
                    <Card.Body>
                        <ListGroup>
                          {ticketTypes.map((item, index) => (
                            <ListGroupItem key={index}>
                              <DanceTicketManagerTicket
                                  name={item.name}
                                  setName={(event) => updateValue(index, event)}
                                  description={item.description}
                                  setDescription={(newDescription) => updateDescription(index, newDescription)}
                                  price={item.price}
                                  setPrice={(event) => updateValue(index, event)}
                                  removeCallback={() => removeTicketType(index)}
                              />
                            </ListGroupItem>
                          ))}
                          <ListGroupItem>
                            <div className="text-center mb-3">
                              <button className="btn btn-primary" type="button" onClick={addNewTicketType} >+</button>
                            </div>
                          </ListGroupItem>
                        </ListGroup>
                    </Card.Body>
                    <Card.Footer>
                      <div className="user-detail text-center mb-3">
                        <Button type="submit" variant="primary">
                          Confirm
                        </Button>
                      </div>
                    </Card.Footer>
                  </Card>
                </Form>
              </Col>
            </Row>
          </Container>
        </div>
      </>
  )
}

export default DanceTicketManager;