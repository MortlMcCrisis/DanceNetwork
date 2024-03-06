import React, {useEffect, useState} from "react";
import {Link, useParams} from 'react-router-dom'
import {Container, Row, Col, Form, Button, ListGroupItem} from 'react-bootstrap'
import Card from '../../../components/Card'

import DanceTicket
    from "../../../components/dance-buy-tickets/dance-ticket";
import DanceBuyTicketHeader
    from "../../../components/dance-buy-tickets/dance-buy-ticket-header";
import {useKeycloak} from "@react-keycloak/web";

const DanceBuyTickets = () => {

    const { id } = useParams();

    const TICKET_STEP = 'Tickets';
    const PERSONAL_DATA_STEP = 'PersonalData';
    const PAYMENT_STEP = 'Payment'
    const FINISH = 'Finish'
    const [show,setShow] = useState(TICKET_STEP);

    const { keycloak, initialized } = useKeycloak();

    const [ticketTypes, setTicketTypes] = useState([]);

    useEffect(() => {
        if(keycloak.authenticated) {
            const fetchData = async () => {
                try {
                    const response = await fetch(`/api/v1/ticketTypes?eventId=${id}`, {
                        headers: {
                            Authorization: `Bearer ${keycloak.token}`,
                            'Content-Type': 'application/json',
                        },
                    });
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    const body = await response.json();

                    setTicketTypes(body);

                    console.log(body);
                } catch (error) {
                    console.error('Error fetching clients:', error);
                }
            };

            fetchData();
        }
    }, [keycloak.authenticated]);

    const [selectedTicketTypes, setSelectedTicketsTypes] = useState({});

    const setTicketType = (ticketTypeId, quantity) => {
        setSelectedTicketsTypes(prevState => ({
            ...prevState,
            [ticketTypeId]: quantity,
        }));
    };

    const getPriceForId = (id) => {
        const ticketType =  ticketTypes.find((ticketType) => ticketType.id == id);

        if (ticketType && ticketType.price !== undefined) {
            return ticketType.price;
        }
    };

    const getNameForId = (id) => {
        const ticketType =  ticketTypes.find((ticketType) => ticketType.id == id);

        if (ticketType && ticketType.name !== undefined) {
            return ticketType.name;
        }
    };

    const calculateTotal = () => {
        let total = 0;
        Object.entries(selectedTicketTypes).forEach(([ticketTypeId, quantity]) => {
            total += getPriceForId(ticketTypeId)*quantity
        });

        return total.toFixed(2);
    }

    return (
        <>
            <div id="content-page" className="content-page">
                <Container>
                    <ul id="dance-top-tab-list" className="p-0 row list-inline">
                        <li className={`
                            ${show === PERSONAL_DATA_STEP || show === PAYMENT_STEP || show === FINISH ? 'done ' : ''}
                            col-lg-3 col-md-6 text-start mb-2 active`} id="account">
                            <Link to="#">
                                <i className="material-symbols-outlined">insert_drive_file</i><span>Tickets</span>
                            </Link>
                        </li>
                        <li id="personal" className={`
                            ${show === PERSONAL_DATA_STEP ? 'active ' : ''} 
                            ${show === PAYMENT_STEP || show === FINISH ? 'active done ' : ''} 
                            col-lg-3 col-md-6 mb-2 text-start`}>
                            <Link to="#">
                                <i className="material-symbols-outlined">person</i><span>Personal Data</span>
                            </Link>
                        </li>
                        <li id="payment" className={` 
                            ${show === PAYMENT_STEP ? 'active ' : ''} 
                            ${show === FINISH ? 'active done ' : ''} 
                            col-lg-3 col-md-6 mb-2 text-start`}>
                            <Link to="#">
                                <i className="material-symbols-outlined">credit_card</i><span>Payment</span>
                            </Link>
                        </li>
                        <li id="confirm" className={`
                            ${show === FINISH ? 'active done ' : ''} 
                            col-lg-3 col-md-6 mb-2 text-start`}>
                            <Link to="#">
                                <i className="material-symbols-outlined">done</i><span>Finish</span>
                            </Link>
                        </li>
                    </ul>
                    <Form id="form-buy-ticket" className="mt-3">
                        <div id="cart" className={`cart-card-block p-0 col-12 ${show === TICKET_STEP ? 'show' : ''}`}>
                            <Row className="align-item-center">
                                <Col lg="8">
                                    {ticketTypes.map(ticketType =>
                                        <div key={ticketType.id}>
                                            <Col sm={12}>
                                                <DanceTicket ticketType={ticketType} setTicketType={setTicketType}/>
                                            </Col>
                                        </div>
                                    )}
                                </Col>
                                <Col lg="4">
                                    <Card>
                                        <Card.Body>
                                            <p><b>Order Details</b></p>
                                            {Object.entries(selectedTicketTypes).map(([ticketTypeId, quantity]) => (
                                                quantity > 0 &&
                                                <div key={ticketTypeId} className="d-flex justify-content-between mb-2">
                                                    <span>{quantity} {getNameForId(ticketTypeId)}</span>
                                                    <span>${getPriceForId(ticketTypeId)}</span>
                                                </div>
                                            ))}
                                            <hr/>
                                            <div className="d-flex justify-content-between mb-4">
                                                <span className="text-dark"><strong>Total</strong></span>
                                                <span className="text-dark"><strong>${calculateTotal()}</strong></span>
                                            </div>
                                            <Link id="place-order" to="#" className="btn btn-primary d-block mt-3 next" onClick={() => setShow(PERSONAL_DATA_STEP)}>Place order</Link>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            </Row>
                        </div>
                        {/* TODO prefil if user is logged in */}
                        {/* TODO store information in cookie to preserve state when page is reloaded */}
                        <div id="cart" className={`cart-card-block p-0 col-12 ${show === PERSONAL_DATA_STEP ? 'show' : ''}`}>
                            <Row className="align-item-center">
                                <Col lg="8">
                                    <Card>
                                        <Card.Body>
                                            <ListGroupItem>
                                                <Form.Group className="form-group">
                                                    <Form.Label htmlFor="firstName" className="form-label">Name:</Form.Label>
                                                    <Form.Control type="text" className="form-control" id="firstName" value='' onChange="()"/>
                                                </Form.Group>
                                                <Form.Group className="form-group">
                                                    <Form.Label htmlFor="lastName" className="form-label">Last name:</Form.Label>
                                                    <Form.Control type="text" className="form-control" id="lastName" value='' onChange="()"/>
                                                </Form.Group>
                                                <Form.Group className="form-group">
                                                    <Form.Label htmlFor="adress" className="form-label">Adress:</Form.Label>
                                                    <Form.Control type="text" className="form-control" id="adress" value='' onChange="()"/>
                                                </Form.Group>
                                                <Form.Group className="form-group">
                                                    <Form.Label htmlFor="city" className="form-label">City:</Form.Label>
                                                    <Form.Control type="text" className="form-control" id="city" value='' onChange="()"/>
                                                </Form.Group>
                                                <Form.Group className="form-group">
                                                    <Form.Label htmlFor="country" className="form-label">Country:</Form.Label>
                                                    <Form.Control type="text" className="form-control" id="country" value='' onChange="()"/>
                                                </Form.Group>
                                                {/*TODO change to any kind of gender neutral sex*/}
                                                <Form.Group className="form-group">
                                                    <Form.Label >Sex: *</Form.Label>
                                                    <Form.Check className="form-check">
                                                        <Form.Check className="form-check form-check-inline">
                                                            <Form.Check.Label> Male</Form.Check.Label>
                                                            <Form.Check.Input type="radio" checked='' onChange="()" className="form-check-input" name="customRadio" id="male"/>
                                                        </Form.Check>
                                                        <Form.Check className="form-check form-check-inline">
                                                            <Form.Check.Input type="radio" checked='' onChange="()" className="form-check-input" name="customRadio" id="female"/>
                                                            <Form.Check.Label> Female</Form.Check.Label>
                                                        </Form.Check>
                                                    </Form.Check>
                                                </Form.Group>
                                                <Form.Group className="form-group">
                                                    <Form.Label >Role: *</Form.Label>
                                                    <Form.Check className="form-check">
                                                        <Form.Check className="form-check form-check-inline">
                                                            <Form.Check.Label> Leader</Form.Check.Label>
                                                            <Form.Check.Input type="radio" checked='' onChange="()" className="form-check-input" name="customRadio" id="leader"/>
                                                        </Form.Check>
                                                        <Form.Check className="form-check form-check-inline">
                                                            <Form.Check.Input type="radio" checked='' onChange="()" className="form-check-input" name="customRadio" id="follower"/>
                                                            <Form.Check.Label> Follower</Form.Check.Label>
                                                        </Form.Check>
                                                        <Form.Check className="form-check form-check-inline">
                                                            <Form.Check.Input type="radio" checked='' onChange="()" className="form-check-input" name="customRadio" id="both"/>
                                                            <Form.Check.Label> Both</Form.Check.Label>
                                                        </Form.Check>
                                                    </Form.Check>
                                                </Form.Group>
                                            </ListGroupItem>
                                            {/* TODO validate emails matching*/}
                                            <ListGroupItem>
                                                <Form.Group className="form-group">
                                                    <Form.Label htmlFor="email" className="form-label">E-Mail:</Form.Label>
                                                    <Form.Control type="email" className="form-control" id="email" defaultValue={initialized ? keycloak.idTokenParsed.email : ""}/>
                                                </Form.Group>
                                            </ListGroupItem>
                                            <ListGroupItem>
                                                <Form.Group className="form-group">
                                                    <Form.Label htmlFor="email-confirm" className="form-label">Confirm E-Mail:</Form.Label>
                                                    <Form.Control type="email" className="form-control" id="email-confirm" defaultValue={initialized ? keycloak.idTokenParsed.email : ""}/>
                                                </Form.Group>
                                            </ListGroupItem>
                                        </Card.Body>
                                    </Card>
                                </Col>
                                <Col lg="4">
                                    <Card>
                                        <Card.Body>
                                            <p><b>Order Details</b></p>
                                            {Object.entries(selectedTicketTypes).map(([ticketTypeId, quantity]) => (
                                                quantity > 0 &&
                                                <div key={ticketTypeId} className="d-flex justify-content-between mb-2">
                                                    <span>{quantity} {getNameForId(ticketTypeId)}</span>
                                                    <span>${getPriceForId(ticketTypeId)}</span>
                                                </div>
                                            ))}
                                            <hr/>
                                            <div className="d-flex justify-content-between mb-4">
                                                <span className="text-dark"><strong>Total</strong></span>
                                                <span className="text-dark"><strong>${calculateTotal()}</strong></span>
                                            </div>
                                            <Link id="place-order" to="#" className="btn btn-primary d-block mt-3 next" onClick={() => setShow(PAYMENT_STEP)}>Place order</Link>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            </Row>
                        </div>
                        <div id="cart" className={`cart-card-block p-0 col-12 ${show === PAYMENT_STEP ? 'show' : ''}`}>
                            <Row className="align-item-center">
                                <Col lg="8">
                                    <Card>
                                        <Card.Body>
                                            Baam
                                        </Card.Body>
                                    </Card>
                                </Col>
                                <Col lg="4">
                                    <Card>
                                        <Card.Body>
                                            <p><b>Order Details</b></p>
                                            {Object.entries(selectedTicketTypes).map(([ticketTypeId, quantity]) => (
                                                quantity > 0 &&
                                                <div key={ticketTypeId} className="d-flex justify-content-between mb-2">
                                                    <span>{quantity} {getNameForId(ticketTypeId)}</span>
                                                    <span>${getPriceForId(ticketTypeId)}</span>
                                                </div>
                                            ))}
                                            <hr/>
                                            <div className="d-flex justify-content-between mb-4">
                                                <span className="text-dark"><strong>Total</strong></span>
                                                <span className="text-dark"><strong>${calculateTotal()}</strong></span>
                                            </div>
                                            <Link id="place-order" to="#" className="btn btn-primary d-block mt-3 next" onClick={() => setShow(FINISH)}>Place order</Link>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            </Row>
                        </div>
                        <div id="cart" className={`cart-card-block p-0 col-12 ${show === FINISH ? 'show' : ''}`}>
                            <Row className="align-item-center">
                                <Col lg="12">
                                    <Card>
                                        <Card.Body>
                                            <p><b>Order Details</b></p>
                                            {Object.entries(selectedTicketTypes).map(([ticketTypeId, quantity]) => (
                                                quantity > 0 &&
                                                <div key={ticketTypeId} className="d-flex justify-content-between mb-2">
                                                    <span>{quantity} {getNameForId(ticketTypeId)}</span>
                                                    <span>${getPriceForId(ticketTypeId)}</span>
                                                </div>
                                            ))}
                                            <hr/>
                                            <div className="d-flex justify-content-between mb-4">
                                                <span className="text-dark"><strong>Total</strong></span>
                                                <span className="text-dark"><strong>${calculateTotal()}</strong></span>
                                            </div>
                                            <hr/>
                                            <hr/>
                                            <div className="d-flex justify-content-between mb-4">
                                                <span className="text-dark">Jan Mortensen</span>
                                                <span className="text-dark">Augustinerweg 18, 79098 Freiburg</span>
                                                <span className="text-dark">Germany</span>
                                                <span className="text-dark">0160 7574886</span>
                                            </div>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            </Row>
                        </div>
                    </Form>
                </Container>
            </div>
        </>
    )
}

export default DanceBuyTickets