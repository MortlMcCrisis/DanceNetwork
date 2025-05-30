import React,{useState, useEffect} from 'react'
import {Link} from 'react-router-dom'
import {
    Container,
    Row,
    Col,
    Dropdown,
} from 'react-bootstrap'
import Card from '../../../components/Card'
import CustomToggle from '../../../components/dropdowns'

import imgpp3 from '../../../assets/images/page-img/07.jpg'
import imgpp4 from '../../../assets/images/page-img/06.jpg'

import loader from "../../../assets/images/page-img/page-load-loader.gif";
import s4 from "../../../assets/images/page-img/s4.jpg";
import s5 from "../../../assets/images/page-img/s5.jpg";
import {useKeycloak} from "@react-keycloak/web";
import {
    NEWSFEED_ENTRIES_CLOSED_ENDPOINT,
    fetchData,
    NEWSFEED_ENTRIES_OPEN_ENDPOINT, USERS_OPEN_ENDPOINT, EVENTS_OPEN_ENDPOINT
} from "../../../components/util/network";
import DanceNewsfeedCard
    from "../../../components/dance-newsfeed/dance-newsfeed-card";
import dayGridPlugin from "@fullcalendar/daygrid";
import listPlugin from "@fullcalendar/list";
import FullCalendar from "@fullcalendar/react";
import placeholder from "../../../assets/images/event/placeholder.png";
import DanceEventCard from "./dance-events-card";

const DanceNewsfeed=()=>{

    const { keycloak, initialized } = useKeycloak();

    const [newsfeedEntries, setNewsfeedEntries] = useState([]);

    const [creators, setCreators] = useState([]);

    const [events, setEvents] = useState([]);

    useEffect(() => {
        if(keycloak.authenticated) {
            fetchData(NEWSFEED_ENTRIES_CLOSED_ENDPOINT, setNewsfeedEntries, keycloak.token);
        }
        else {
            fetchData(NEWSFEED_ENTRIES_OPEN_ENDPOINT, setNewsfeedEntries);
        }
    }, [keycloak.authenticated]);

    useEffect(() => {
        if(newsfeedEntries.length > 0) {
            const uniqueCreators = new Set(newsfeedEntries.map(newsfeedEntry => newsfeedEntry.creator));
            fetchData(`${USERS_OPEN_ENDPOINT}?${[...uniqueCreators].map(creator => `userUUID=${creator}`).join('&')}`, setCreators);
        }
    }, [newsfeedEntries]);

    useEffect(() => {
        const today = new Date().toISOString().split('T')[0];
        fetchData(EVENTS_OPEN_ENDPOINT + `?maxEntries=2&from=${today}`, setEvents);
    }, [keycloak.authenticated]);

    return(
        <>
            <div id="content-page" className="content-page">
                <Container>
                    <Row className="mt-1">
                        <Col lg="4" md="4">
                            <Card>
                                <div className="card-header d-flex justify-content-between">
                                    <div className="header-title">
                                        <h4 className="card-title">Upcoming Event</h4>
                                    </div>
                                    <div className="card-header-toolbar d-flex align-items-center">
                                        <p className="m-0"><Link to="/dashboards/app/dance-events"> All events </Link></p>
                                    </div>
                                </div>
                                <Card.Body>
                                    <Row>
                                        {events.map(event =>
                                            <div key={event.id}>
                                                <Col sm={12}>
                                                    <DanceEventCard event={event} />
                                                </Col>
                                            </div>
                                        )}
                                    </Row>
                                </Card.Body>
                            </Card>
                            <Card>
                                <Card.Header>
                                    <h3>Bachaturo Workshops</h3>
                                </Card.Header>
                                <Card.Body>
                                    <FullCalendar
                                        plugins={[ dayGridPlugin,listPlugin ]}
                                        //    themeSystem={bootstrap}
                                        buttonText={{
                                            day:'day',
                                            list: 'whole event'
                                        }}
                                        initialView={'listWeek'}
                                        initialDate={'2024-07-15'}
                                        firstDay={1}
                                        headerToolbar={{
                                            left:'prev,next',
                                            center:'title',
                                            right:''
                                        }}
                                        events={[
                                            {title: '5:30a Repeating Event', date: '2024-07-20',textColor:'white',backgroundColor:'#ff9b8a',borderColor:'#d592ff'},
                                            {title: '5:30a Repeating Event', date: '2024-07-20',textColor:'white',backgroundColor:'#ff9b8a',borderColor:'#d592ff'},
                                            {title: '5:30a Repeating Event', date: '2024-07-21',textColor:'white',backgroundColor:'#49f0d3',borderColor:'#49f0d3'},
                                            {title: '5:30a Repeating Event', date: '2024-07-21',textColor:'white',backgroundColor:'#49f0d3',borderColor:'#49f0d3'},
                                            {title: '5:30a Repeating Event', date: '2024-07-22',textColor:'white',backgroundColor:'#d592ff',borderColor:'#ff9b8a'},
                                            {title: '5:30a Repeating Event', date: '2024-07-22',textColor:'white',backgroundColor:'#d592ff',borderColor:'#ff9b8a'},
                                            {title: '5:30a Repeating Event', date: '2024-08-29',textColor:'white',backgroundColor:'#d592ff',borderColor:'#d592ff'},
                                            {title: '5:30a Repeating Event', date: '2024-08-31',textColor:'white',backgroundColor:'#ff9b8a',borderColor:'#ff9b8a'},
                                            {title: '5:30a Birthday Party', date: '2024-09-02',textColor:'white',backgroundColor:'#49f0d3',borderColor:'#49f0d3'},
                                            {title: '5:30a Meeting', date: '2024-09-04',textColor:'white',backgroundColor:'#a09e9e',borderColor:'#a09e9e'},
                                            {title: '5:30a Birthday Party', date: '2024-09-05',textColor:'white',backgroundColor:'#49f0d3',borderColor:'#49f0d3'},
                                            {title: '5:30a Birthday Party', date: '2024-09-08',textColor:'white',backgroundColor:'#ff9b8a',borderColor:'#ff9b8a'},
                                            {title: '5:30a Doctor Meeting', date: '2024-09-10',textColor:'white',backgroundColor:'#f4a965',borderColor:'#f4a965'},
                                            {title: '5:30a All Day Event', date: '2024-09-11',textColor:'white',backgroundColor:'#50b5ff',borderColor:'#50b5ff'},
                                            {title: '5:30a Repeating Event', date: '2024-09-18',textColor:'white',backgroundColor:'#50b5ff',borderColor:'#50b5ff'},
                                            {title: '5:30a Repeating Event', date: '2024-09-20',textColor:'white',backgroundColor:'#49f0d3',borderColor:'#49f0d3'},
                                        ]}
                                        footerToolbar={{
                                            center:'dayGridDay,listWeek'
                                        }}
                                    />
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col lg="8" md="8">
                            {newsfeedEntries.map((newsfeedEntry, idx) =>
                                <Col sm={12} key={idx}>
                                    <DanceNewsfeedCard newsfeedEntry={newsfeedEntry} creator={creators.find(creator => creator.uuid === newsfeedEntry.creator)}/>
                                </Col>
                            )}
                        </Col>
                        <div className="col-sm-12 text-center">
                            <img src={loader} alt="loader" style={{height: "100px"}}/>
                        </div>
                    </Row>
                </Container>
            </div>
        </>
    )
}
export default DanceNewsfeed