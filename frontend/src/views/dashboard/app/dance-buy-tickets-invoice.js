import React, {useEffect, useState} from "react";
import {Navigate, useParams} from 'react-router-dom'
import {Container, Row, Col, Form} from 'react-bootstrap'

import DanceBuyTicketsHeader
    from "../../../components/dance-buy-tickets/dance-buy-tickets-header";
import {useKeycloak} from "@react-keycloak/web";
import DanceBuyTicketsSummary
    from "../../../components/dance-buy-tickets/dance-buy-tickets-summary";
import {
    PAYMENTS_OPEN_ENDPOINT
} from "../../../components/util/network";
import {
    FINISH_STEP
} from "../../../components/dance-buy-tickets/dance-buy-tickets-util";
import "react-toastify/dist/ReactToastify.css";

const DanceBuyTicketsInvoice = () => {

    const { keycloak, initialized } = useKeycloak();

    //TODO brauch ich eigentlich nicht, oder? Der user blickt des eh nicht
    const { id } = useParams();

    const [status, setStatus] = useState(null);

    const [customerEmail, setCustomerEmail] = useState('');

    useEffect(() => {
        const queryString = window.location.search;
        const urlParams = new URLSearchParams(queryString);
        const sessionId = urlParams.get('session_id');
        const ticketOrderId = urlParams.get('ticket_order_id');

        fetch(`${PAYMENTS_OPEN_ENDPOINT}/session-status?session_id=${sessionId}&ticket_order_id=${ticketOrderId}`)
        .then((res) => res.json())
        .then((data) => {
            setStatus(data.status);
            setCustomerEmail(data.customer_email);
        });
    }, []);

    if (status === 'open') {
        return (
            <Navigate to="/dashboards/app/dance-event-detail/3/dance-buy-ticket" />
        )
    }

    if (status === 'complete') {
        return (
            <div id="content-page" className="content-page">
                <Container>
                    <DanceBuyTicketsHeader state={FINISH_STEP} setState={() => {}} />
                    <DanceBuyTicketsSummary ticketTypes={[]} formData={[]} />
                </Container>
            </div>
        )
    }
}

export default DanceBuyTicketsInvoice