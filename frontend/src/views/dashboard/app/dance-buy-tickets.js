import React, {useEffect, useState} from "react";
import {useParams} from 'react-router-dom'
import {Container, Row, Col, Form} from 'react-bootstrap'

import DanceBuyTicketsTicketType
    from "../../../components/dance-buy-tickets/dance-buy-tickets-ticket-type";
import DanceBuyTicketsHeader
    from "../../../components/dance-buy-tickets/dance-buy-tickets-header";
import {useKeycloak} from "@react-keycloak/web";
import DanceBuyTicketsOrderDetails
    from "../../../components/dance-buy-tickets/dance-buy-tickets-order-details";
import * as Util from "../../../components/dance-buy-tickets/dance-buy-tickets-util";
import DanceBuyTicketsPersonalData
    from "../../../components/dance-buy-tickets/dance-buy-tickets-personal-data";
import DanceBuyTicketsPayment
    from "../../../components/dance-buy-tickets/dance-buy-tickets-payment";
import DanceBuyTicketsSummary
    from "../../../components/dance-buy-tickets/dance-buy-tickets-summary";
import {fetchData} from "../../../components/util/network";
import {
    getNameForId
} from "../../../components/dance-buy-tickets/dance-buy-tickets-util";

const DanceBuyTickets = () => {

    const { keycloak, initialized } = useKeycloak();

    const { id } = useParams();

    const [state,setState] = useState(Util.TICKET_STEP);
    const increaseState = () => {
        if(state <= Util.FINISH_STEP) {
            setState(state + 1)
        }
    }
    const decreaseState = () => {
        if(state >= Util.TICKET_STEP) {
            setState(state - 1)
        }
    }

    const [ticketTypes, setTicketTypes] = useState([]);

    const[formData, setFormData] = React.useState([]);

    const [selectedTicketTypes, setSelectedTicketsTypes] = useState({});

    const setTicketType = (ticketTypeId, quantity) => {
        setSelectedTicketsTypes(prevState => ({
            ...prevState,
            [ticketTypeId]: quantity,
        }));
    };

    const handleFormDataChange = (event, index) => {
        setFormData((prevForm) => {
            return prevForm.map((item, i) => {
                if (i === index) {
                    return {
                        ...item,
                        [event.target.id]: event.target.value,
                    };
                }
                return item;
            });
        });

        console.log(formData)
    };

    useEffect(() => {
        if(keycloak.authenticated) {
            fetchData(`/api/v1/ticketTypes?eventId=${id}`, keycloak.token, setTicketTypes);
        }
    }, [keycloak.authenticated]);

    useEffect( () => {
        let newFormData = [];

        Object.entries(selectedTicketTypes).map(([ticketTypeId, quantity]) => {
            Array.from({ length: quantity }, (_) => (
                newFormData.push({
                    ticketTypeId: ticketTypeId,
                    ticketTypeName: getNameForId(ticketTypes, ticketTypeId),
                    firstName: '',
                    lastName: '',
                    address: '',
                    country: '',
                    gender: '',
                    role: '',
                    email: '',
                    phone: '',})
            ));
        });

        setFormData(newFormData)
    }, [selectedTicketTypes]);

    return (
        <div id="content-page" className="content-page">
            <Container>
                <DanceBuyTicketsHeader state={state} setState={setState} />
                <Form id="form-buy-ticket" className="mt-3">
                    <div id="cart" className={`cart-card-block p-0 col-12 ${state === Util.TICKET_STEP ? 'show' : ''}`}>
                        <Row className="align-item-center">
                            <Col lg="8">
                                {ticketTypes.map(ticketType =>
                                    <div key={ticketType.id}>
                                        <Col sm={12}>
                                            <DanceBuyTicketsTicketType ticketType={ticketType} setTicketType={setTicketType}/>
                                        </Col>
                                    </div>
                                )}
                            </Col>
                            <Col lg="4">
                                <DanceBuyTicketsOrderDetails
                                    state={state}
                                    selectedTicketTypes={selectedTicketTypes}
                                    ticketTypes={ticketTypes}
                                    increaseState={increaseState}
                                    decreaseState={decreaseState}/>
                            </Col>
                        </Row>
                    </div>
                    {/* TODO store information in cookie to preserve state when page is reloaded */}
                    {/* TODO personal information has to be entered for every person */}
                    <div id="cart" className={`cart-card-block p-0 col-12 ${state === Util.PERSONAL_DATA_STEP ? 'show' : ''}`}>
                        <Row className="align-item-center">
                            <Col lg="8">
                                <DanceBuyTicketsPersonalData
                                    formData={formData}
                                    handleFormDataChange={handleFormDataChange}/>
                            </Col>
                            <Col lg="4">
                                <DanceBuyTicketsOrderDetails
                                    state={state}
                                    selectedTicketTypes={selectedTicketTypes}
                                    ticketTypes={ticketTypes}
                                    increaseState={increaseState}
                                    decreaseState={decreaseState}/>
                            </Col>
                        </Row>
                    </div>
                    <div id="cart" className={`cart-card-block p-0 col-12 ${state === Util.PAYMENT_STEP ? 'show' : ''}`}>
                        <Row className="align-item-center">
                            <Col lg="8">
                                <DanceBuyTicketsPayment />
                            </Col>
                            <Col lg="4">
                                <DanceBuyTicketsOrderDetails
                                    state={state}
                                    selectedTicketTypes={selectedTicketTypes}
                                    ticketTypes={ticketTypes}
                                    increaseState={increaseState}
                                    decreaseState={decreaseState}/>
                            </Col>
                        </Row>
                    </div>
                    <div id="cart" className={`cart-card-block p-0 col-12 ${state === Util.FINISH_STEP ? 'show' : ''}`}>
                        <DanceBuyTicketsSummary ticketTypes={ticketTypes} formData={formData} />
                    </div>
                </Form>
            </Container>
        </div>
    )
}

export default DanceBuyTickets