import React, {useEffect, useState} from 'react'
import {
   Col,
   Container, Nav,
   Row, Tab
} from 'react-bootstrap'
import Card from '../../../components/Card'
import {Link} from 'react-router-dom'

// images
import user05 from '../../../assets/images/user/05.jpg'
import user06 from '../../../assets/images/user/06.jpg'
import user07 from '../../../assets/images/user/07.jpg'
import user08 from '../../../assets/images/user/08.jpg'
import user09 from '../../../assets/images/user/09.jpg'
import img51 from '../../../assets/images/page-img/51.jpg'
import img52 from '../../../assets/images/page-img/52.jpg'
import img53 from '../../../assets/images/page-img/53.jpg'
import img54 from '../../../assets/images/page-img/54.jpg'
import img55 from '../../../assets/images/page-img/55.jpg'
import img56 from '../../../assets/images/page-img/56.jpg'
import img58 from '../../../assets/images/page-img/58.jpg'
import img57 from '../../../assets/images/page-img/57.jpg'
import img59 from '../../../assets/images/page-img/59.jpg'
import {useKeycloak} from "@react-keycloak/web";
import DanceEventsCreateButton from "./dance-events-create-button";
import DanceEventCard from "./dance-events-card";
import {EVENTS_OPEN_ENDPOINT, fetchData} from "../../../components/util/network";
import dayGridPlugin from "@fullcalendar/daygrid";
import listPlugin from "@fullcalendar/list";
import FullCalendar from "@fullcalendar/react";

const DanceEvents =() =>{

   const [events, setEvents] = useState([]);

   const [transformedEvents, setTransformedEvents] = useState([]);

   const { keycloak, initialized } = useKeycloak();

   useEffect(() => {
         fetchData(EVENTS_OPEN_ENDPOINT, (data) => setEvents(data));
   }, []);

   useEffect(() => {
      setTransformedEvents(() => events.map(event => {
         const transformedEvent = {
            title: event.name,
            start: event.startDate,
            url: `dance-event-detail/${event.id}`
         };

         if (event.endDate) {
            transformedEvent.end = event.endDate;
         } else {
            transformedEvent.start = `${event.startDate}T${event.startTime}`;
            transformedEvent.allDay = false;
         }

         console.log(event.name)
         console.log(transformedEvent.start)

         return transformedEvent;
      }));
   }, [events]);

   return(
      <>
         { keycloak.authenticated && <DanceEventsCreateButton /> }
         <Container>
            <Row className="mt-1">
               <Col lg="12" md="12">
                  <div id="content-page" className="content-page">
                     <Tab.Container id="left-tabs-example" defaultActiveKey="first">
                        <Card className="p-0">
                           <Card.Body className="p-0">
                              <div className="user-tabing">
                                 <Nav as="ul" variant="pills" className="d-flex align-items-center justify-content-center profile-feed-items p-0 m-0">
                                    <Nav.Item as="li" className=" col-12 col-sm-6 p-0 ">
                                       <Nav.Link href="#pills-timeline-tab"  eventKey="first" role="button" className=" text-center p-3">Calendar</Nav.Link>
                                    </Nav.Item>
                                    <Nav.Item as="li" className="col-12 col-sm-6 p-0">
                                       <Nav.Link href="#pills-about-tab" eventKey="second" role="button" className="text-center p-3">List</Nav.Link>
                                    </Nav.Item>
                                 </Nav>
                              </div>
                           </Card.Body>
                        </Card>
                        <Container>
                           <Tab.Content>
                              {/* TODO make this the second tab the problem here is that the calendar is not correctly displayed, when hidden on page load, because the size is not calculated correctly

                              [
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
                                                  {
                                                     title  : 'event1',
                                                     start  : '2024-08-01'
                                                  },
                                                  {
                                                     title  : 'event2',
                                                     start  : '2024-08-05',
                                                     end    : '2024-08-07'
                                                  },
                                                  {
                                                     title  : 'event3',
                                                     start  : '2024-08-09T12:30:00',
                                                     allDay : false
                                                  },
                                                  {
                                                     title  : events[0].name,
                                                     start  : events[0].startDate,
                                                     end    : events[0].endDate,
                                                     url    : `dance-event-detail/${events[0].id}`
                                                  },
                                                  {
                                                     title  : events[1].name,
                                                     start  : events[1].startDate,
                                                     end    : events[1].endDate,
                                                     url    : `dance-event-detail/${events[1].id}`
                                                  },
                                                  {
                                                     title  : events[2].name,
                                                     start  : events[2].startDate,
                                                     end    : events[2].endDate,
                                                     url    : `dance-event-detail/${events[2].id}`
                                                  },
                                               ]


                              */}
                              <Tab.Pane eventKey="first">
                                 <Card>
                                    <Card.Body>
                                       {transformedEvents.length > 0 &&
                                           <FullCalendar
                                               plugins={[dayGridPlugin,listPlugin]}
                                               //    themeSystem={bootstrap}
                                               headerToolbar={{
                                                  left:'prev,next today',
                                                  center:'title',
                                                  right:'dayGridMonth,dayGridWeek,dayGridDay,listWeek',
                                               }}
                                               firstDay={1}
                                               events={transformedEvents}
                                               eventTimeFormat={{
                                                  hour: 'numeric',
                                                  minute: '2-digit',
                                                  meridiem: 'short',
                                               }}
                                           />
                                       }
                                    </Card.Body>
                                 </Card>
                              </Tab.Pane>
                              <Tab.Pane eventKey="second" >
                                 <div className="d-grid gap-3 d-grid-template-1fr-19">
                                    {events.map(event =>
                                        <div key={event.id}>
                                           <Col sm={12}>
                                              <DanceEventCard event={event} />
                                           </Col>
                                        </div>
                                    )}

                                       <Card className=" rounded  mb-0">
                                          <div className="event-images">
                                             <Link to="#">
                                                <img src={img51} className="img-fluid" alt="Responsive"/>
                                             </Link>
                                          </div>
                                          <Card.Body>
                                             <div className="d-flex">
                                                <div className="date-of-event">
                                                   <span>Jan</span>
                                                   <h5>01</h5>
                                                </div>
                                                <div className="events-detail ms-3">
                                                   <h5><Link to="/dashboards/app/event-detail">New Year Celebration</Link></h5>
                                                   <p>Lorem Ipsum is simply dummy text</p>
                                                   <div className="event-member">
                                                      <div className="iq-media-group">
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user05} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user06} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user07} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user08} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user09} alt=""/>
                                                         </Link>
                                                      </div>
                                                   </div>
                                                </div>
                                             </div>
                                          </Card.Body>
                                       </Card>
                                    <div>
                                       <Card className=" rounded  mb-0">
                                          <div className="event-images">
                                             <Link to="#">
                                                <img src={img52} className="img-fluid" alt="Responsive"/>
                                             </Link>
                                          </div>
                                          <div className="card-body">
                                             <div className="d-flex">
                                                <div className="date-of-event">
                                                   <span>Jan</span>
                                                   <h5>24</h5>
                                                </div>
                                                <div className="events-detail ms-3">
                                                   <h5><Link to="/dashboards/app/event-detail">Birthday Celebration</Link></h5>
                                                   <p>Lorem Ipsum is simply dummy text</p>
                                                   <div className="event-member">
                                                      <div className="iq-media-group">
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user05} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user06} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user07} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user08} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user09} alt=""/>
                                                         </Link>
                                                      </div>
                                                   </div>
                                                </div>
                                             </div>
                                          </div>
                                       </Card>
                                    </div>
                                    <div>
                                       <Card className="mb-0 rounded ">
                                          <div className="event-images">
                                             <Link to="#">
                                                <img src={img53} className="img-fluid" alt="Responsive "/>
                                             </Link>
                                          </div>
                                          <Card.Body>
                                             <div className="d-flex">
                                                <div className="date-of-event">
                                                   <span>Jan</span>
                                                   <h5>26</h5>
                                                </div>
                                                <div className="events-detail ms-3">
                                                   <h5><Link to="/dashboards/app/event-detail">Republic Day</Link></h5>
                                                   <p>Lorem Ipsum is simply dummy text</p>
                                                   <div className="event-member">
                                                      <div className="iq-media-group">
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user05} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user06} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user07} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user08} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user09} alt=""/>
                                                         </Link>
                                                      </div>
                                                   </div>
                                                </div>
                                             </div>
                                          </Card.Body>
                                       </Card>
                                    </div>
                                    <div>
                                       <Card className=" mb-0 rounded ">
                                          <div className="event-images">
                                             <Link to="#">
                                                <img src={img54} className="img-fluid" alt="Responsive"/>
                                             </Link>
                                          </div>
                                          <div className="card-body">
                                             <div className="d-flex">
                                                <div className="date-of-event">
                                                   <span>Feb</span>
                                                   <h5>04</h5>
                                                </div>
                                                <div className="events-detail ms-3">
                                                   <h5><Link to="/dashboards/app/event-detail">Meetings & Conventions</Link></h5>
                                                   <p>Lorem Ipsum is simply dummy text</p>
                                                   <div className="event-member">
                                                      <div className="iq-media-group">
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user05} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user06} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user07} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user08} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user09} alt=""/>
                                                         </Link>
                                                      </div>
                                                   </div>
                                                </div>
                                             </div>
                                          </div>
                                       </Card>
                                    </div>
                                    <div>
                                       <div className="card mb-0 rounded ">
                                          <div className="event-images">
                                             <Link to="#">
                                                <img src={img55} className="img-fluid" alt="Responsive "/>
                                             </Link>
                                          </div>
                                          <div className="card-body">
                                             <div className="d-flex">
                                                <div className="date-of-event">
                                                   <span>March</span>
                                                   <h5>01</h5>
                                                </div>
                                                <div className="events-detail ms-3">
                                                   <h5><Link to="/dashboards/app/event-detail">Fun Events and Festivals </Link></h5>
                                                   <p>Lorem Ipsum is simply dummy text</p>
                                                   <div className="event-member">
                                                      <div className="iq-media-group">
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user05} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user06} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user07} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user08} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user09} alt=""/>
                                                         </Link>
                                                      </div>
                                                   </div>
                                                </div>
                                             </div>
                                          </div>
                                       </div>
                                    </div>
                                    <div>
                                       <Card className=" mb-0 rounded ">
                                          <div className="event-images">
                                             <Link to="#">
                                                <img src={img56} className="img-fluid" alt="Responsive"/>
                                             </Link>
                                          </div>
                                          <Card.Body>
                                             <div className="d-flex">
                                                <div className="date-of-event">
                                                   <span>March</span>
                                                   <h5>10</h5>
                                                </div>
                                                <div className="events-detail ms-3">
                                                   <h5><Link to="/dashboards/app/event-detail">Atlanta Retail Show</Link></h5>
                                                   <p>Lorem Ipsum is simply dummy text</p>
                                                   <div className="event-member">
                                                      <div className="iq-media-group">
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user05} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user06} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user07} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user08} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user09} alt=""/>
                                                         </Link>
                                                      </div>
                                                   </div>
                                                </div>
                                             </div>
                                          </Card.Body>
                                       </Card>
                                    </div>
                                    <div>
                                       <Card className="rounded ">
                                          <div className="event-images">
                                             <Link to="#">
                                                <img src={img57} className="img-fluid" alt="Responsive "/>
                                             </Link>
                                          </div>
                                          <div className="card-body">
                                             <div className="d-flex">
                                                <div className="date-of-event">
                                                   <span>March</span>
                                                   <h5>14</h5>
                                                </div>
                                                <div className="events-detail ms-3">
                                                   <h5><Link to="/dashboards/app/event-detail">Holi in the City</Link></h5>
                                                   <p>Lorem Ipsum is simply dummy text</p>
                                                   <div className="event-member">
                                                      <div className="iq-media-group">
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user05} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user06} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user07} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user08} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user09} alt=""/>
                                                         </Link>
                                                      </div>
                                                   </div>
                                                </div>
                                             </div>
                                          </div>
                                       </Card>
                                    </div>
                                    <div>
                                       <Card className="card rounded ">
                                          <div className="event-images">
                                             <Link to="#">
                                                <img src={img58} className="img-fluid" alt="Responsive"/>
                                             </Link>
                                          </div>
                                          <Card.Body>
                                             <div className="d-flex">
                                                <div className="date-of-event">
                                                   <span>Mar</span>
                                                   <h5>16</h5>
                                                </div>
                                                <div className="events-detail ms-3">
                                                   <h5><Link to="/dashboards/app/event-detail">Insurance Innovators</Link></h5>
                                                   <p>Lorem Ipsum is simply dummy text</p>
                                                   <div className="event-member">
                                                      <div className="iq-media-group">
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user05} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user06} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user07} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user08} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user09} alt=""/>
                                                         </Link>
                                                      </div>
                                                   </div>
                                                </div>
                                             </div>
                                          </Card.Body>
                                       </Card>
                                    </div>
                                    <div>
                                       <Card className=" rounded ">
                                          <div className="event-images">
                                             <Link to="#">
                                                <img src={img59} className="img-fluid" alt="Responsive"/>
                                             </Link>
                                          </div>
                                          <Card.Body>
                                             <div className="d-flex">
                                                <div className="date-of-event">
                                                   <span>Apr</span>
                                                   <h5>12</h5>
                                                </div>
                                                <div className="events-detail ms-3">
                                                   <h5><Link to="/dashboards/app/event-detail">BIG 5G Event</Link></h5>
                                                   <p>Lorem Ipsum is simply dummy text</p>
                                                   <div className="event-member">
                                                      <div className="iq-media-group">
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user05} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user06} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user07} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user08} alt=""/>
                                                         </Link>
                                                         <Link to="#" className="iq-media">
                                                            <img className="img-fluid avatar-40 rounded-circle" src={user09} alt=""/>
                                                         </Link>
                                                      </div>
                                                   </div>
                                                </div>
                                             </div>
                                          </Card.Body>
                                       </Card>
                                    </div>
                                 </div>
                              </Tab.Pane>
                           </Tab.Content>
                        </Container>
                     </Tab.Container>
                  </div>
               </Col>
            </Row>
         </Container>
      </>
  )

}

export default DanceEvents;