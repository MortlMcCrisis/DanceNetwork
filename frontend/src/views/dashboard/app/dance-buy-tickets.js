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
import {
    fetchData,
    PAYMENTS_OPEN_ENDPOINT,
    postData, TICKET_TYPES_OPEN_ENDPOINT
} from "../../../components/util/network";
import {
    getNameForId
} from "../../../components/dance-buy-tickets/dance-buy-tickets-util";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const DanceBuyTickets = () => {

    const { keycloak, initialized } = useKeycloak();

    const { id } = useParams();

    const [state,setState] = useState(Util.TICKET_STEP);

    const [isValidated, setValidated] = useState(false);

    const validateAndSetState = (newState) => {
        if(state === newState){
            return;
        }

        if((state === Util.TICKET_STEP)
            && formData.length === 0) {
            toast.error("You need to select at least one ticket!");
            return;
        }

        if(state === Util.PERSONAL_DATA_STEP && newState !== Util.TICKET_STEP) {
            for(let i=0; i<formData.length; i++)
            {
                if (formData[i].firstName === '' ||
                    formData[i].lastName === '' ||
                    formData[i].address === '' ||
                    formData[i].country === '' ||
                    formData[i].email === '' ||
                    formData[i].emailConfirm === '') {

                    toast.error("Please complete personal data for all of your tickets!");
                    setValidated(true);
                    return;
                }

                console.log()

                if(formData[i].email != formData[i].emailConfirm) {
                    toast.error("E-mail addresses must match!");
                    setValidated(true);
                    return;
                }
            }
        }

        setValidated(false);

        setState(newState);
    }

    const increaseState = () => {
        if(state <= Util.FINISH_STEP) {
            validateAndSetState(state + 1)
        }

        if(state == Util.PAYMENT_STEP){
            triggerPayment()
        }
    }
    const decreaseState = () => {
        if(state >= Util.TICKET_STEP) {
            setState(state - 1)
        }
    }

    const triggerPayment = () => {
        (async () => {
            try {
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

                console.log(requestData);

                const response = await postData(PAYMENTS_OPEN_ENDPOINT, requestData, keycloak.token)
                if (response.ok) {
                    toast.success("Successfully bought tickets!");
                }
                else{
                    toast.error("Error while processing your request!");
                }

            } catch (error) {
                console.error('Fehler:', error);
            }
        })();
    }

    const [ticketTypes, setTicketTypes] = useState([]);

    const[formData, setFormData] = React.useState([]);

    const [selectedTickets, setSelectedTickets] = useState({});

    const setTicketType = (ticketTypeId, quantity) => {
        setSelectedTickets(prevState => ({
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
    };

    useEffect(() => {
        fetchData(`${TICKET_TYPES_OPEN_ENDPOINT}?eventId=${id}`, setTicketTypes);
    }, []);

    useEffect( () => {
        let newFormData = [];

        Object.entries(selectedTickets).map(([ticketTypeId, quantity]) => {
            Array.from({ length: quantity }, (_) => (
                newFormData.push({
                    ticketTypeId: ticketTypeId,
                    ticketTypeName: getNameForId(ticketTypes, ticketTypeId),
                    firstName: '',
                    lastName: '',
                    address: '',
                    country: '',
                    gender: '',
                    dancingRole: '',
                    email: '',
                    phone: '',})
            ));
        });

        setFormData(newFormData)
    }, [selectedTickets]);

    return (
        <div id="content-page" className="content-page">
            <Container>
                <DanceBuyTicketsHeader state={state} setState={validateAndSetState} />
                <Form id="form-buy-ticket" className="mt-3 needs-validation" validated={isValidated} noValidate>
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
                                    selectedTickets={selectedTickets}
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
                                    selectedTickets={selectedTickets}
                                    ticketTypes={ticketTypes}
                                    increaseState={increaseState}
                                    decreaseState={decreaseState}/>
                            </Col>
                        </Row>
                    </div>
                    {state === Util.PAYMENT_STEP &&
                        <div id="cart" className={`cart-card-block p-0 col-12 ${state === Util.PAYMENT_STEP ? 'show' : ''}`}>
                            <Row className="align-item-center">
                                <Col lg="8">
                                    <DanceBuyTicketsPayment
                                        formData={formData}/>
                                </Col>
                                <Col lg="4">
                                    <DanceBuyTicketsOrderDetails
                                        state={state}
                                        selectedTickets={selectedTickets}
                                        ticketTypes={ticketTypes}
                                        increaseState={increaseState}
                                        decreaseState={decreaseState}/>
                                </Col>
                            </Row>
                        </div>
                    }
                    <div id="cart" className={`cart-card-block p-0 col-12 ${state === Util.FINISH_STEP ? 'show' : ''}`}>
                        <DanceBuyTicketsSummary ticketTypes={ticketTypes} formData={formData} />
                    </div>
                </Form>
            </Container>
        </div>
    )
}

export default DanceBuyTickets