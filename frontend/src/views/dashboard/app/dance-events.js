import React, {useState} from 'react'
import {
   Button,
   Col,
   Container, Form,
   ListGroup,
   ListGroupItem,
   Modal,
   Row
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
import img6 from '../../../assets/images/page-img/profile-bg6.jpg'
import Datepicker from "../../../components/datepicker";
import {useKeycloak} from "@react-keycloak/web";

const DanceEvents =() =>{
   const [showCreate, setShowCreate] = useState(false);
   const handleCloseCreate = () => setShowCreate(false);
   const handleShowCreate = () => setShowCreate(true);

   const { keycloak, initialized } = useKeycloak();

   const [isMultipleDays, setIsMultipleDays] = useState(false);

   const handleCheckboxChange = (event) => {
      setIsMultipleDays(event.target.checked);
   };

   const [form, setForm] = React.useState({
      name: '',
      startDate: '',
      endDate: ''
   });

   const handleChange = (event) => {
      setForm({
         ...form,
         [event.target.id]: event.target.value,
      });
   };

   const handleSubmit = async (event) => {
      event.preventDefault();

      //TODO validate that start date is before end date

      try {
         const response = await fetch('/api/v1/event', {
            method: 'POST',
            headers: {
               Authorization: `Bearer ${keycloak.token}`,
               'Content-Type': 'application/json',
            },
            body: JSON.stringify({ ...form }),
         });

         console.log(response);

         if (response.status === 201) {
            const resourceUrl = response.headers.get('Location')
            const id = resourceUrl.split('/').pop();
            window.location = `/dashboards/app/dance-event-detail/${id}`;
         }

         if (!response.ok) {
            throw new Error('Network response was not ok');
         }

      } catch (error) {
         console.error('Error saving event:', error);
      }
   };

   return(
      <>
         <div className="btn dance-btn-fixed-bottom btn-danger btn-icon btn-setting" >
            <Button variant="warning" className="rounded-pill mb-1" onClick={handleShowCreate}>Create Event</Button>
            <Modal centered show={showCreate} onHide={handleCloseCreate}>
               <Modal.Header closeButton>
                  <Modal.Title>Create Event</Modal.Title>
               </Modal.Header>
               <Form onSubmit={handleSubmit}>
                  <Modal.Body>
                     <ListGroup>
                        <ListGroupItem>
                           <Form.Group className="form-group">
                              <Form.Label htmlFor="name" className="form-label">Name:</Form.Label>
                              <Form.Control type="text"
                                            className="form-control"
                                            placeholder="Name of the event..."
                                            id="name"
                                            onChange={handleChange}
                                            required/> {/* TODO change to custom (english) error message */ }
                           </Form.Group>
                           <div className="d-flex justify-content-end">
                              <Form.Group className="form-group d-inline-block me-3">
                                 <Form.Label htmlFor="startDate" className="form-label">{isMultipleDays ? "Start date" : "Date"}:</Form.Label>
                                 <Form.Control type="date" className="form-control" id="startDate" onChange={handleChange} required/>
                              </Form.Group>
                              <Form.Group className="form-group d-inline-block me-3">
                                 <Form.Label htmlFor="endDate" className={`form-label ${!isMultipleDays ? 'text-muted' : ''}`}>End date:</Form.Label>
                                 <Form.Control disabled={!isMultipleDays} type="date" className="form-control" id="endDate" onChange={handleChange} required={!isMultipleDays}/>
                              </Form.Group>
                              <Form.Check className="form-check form-switch ms-auto">
                                 {/* TODO align right to prevent the elements jumping around */}
                                 <Form.Check type="checkbox" className="bg-primary" defaultChecked={isMultipleDays} onChange={handleCheckboxChange} id="isMultipleDays" />
                                 <Form.Check.Label>Multiple days</Form.Check.Label>
                              </Form.Check>
                           </div>
                        </ListGroupItem>
                     </ListGroup>
                  </Modal.Body>
                  <Modal.Footer>
                     <Button variant="secondary" onClick={handleCloseCreate}>
                        Abort
                     </Button>
                     <Button type="submit" variant="primary">
                        Create
                     </Button>
                  </Modal.Footer>
               </Form>
            </Modal>
         </div>
         <Container>
            <Row className="mt-1">
               <Col lg="12" md="12">
                  <div id="content-page" className="content-page">
                     <Container>
                        <div className="d-grid gap-3 d-grid-template-1fr-19">
                           <div>
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
                           </div>
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
                     </Container>
                  </div>
               </Col>
            </Row>
         </Container>
      </>
  )

}

export default DanceEvents;