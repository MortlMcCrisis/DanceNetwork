import React, {useEffect, useState} from 'react'
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
import placeholder from '../../../assets/images/event/placeholder.png'
import {useKeycloak} from "@react-keycloak/web";
import {format} from "date-fns";

const DanceEventCard =({id}) => {

   const { keycloak, initialized } = useKeycloak();

   const [event, setEvent] = useState([]);

   useEffect(() => {
      if(keycloak.authenticated) {
         const fetchNewsfeedEntry = async () => {
            try {
               const response = await fetch(`/api/v1/event/${id}`, {
                  headers: {
                     Authorization: `Bearer ${keycloak.token}`,
                     'Content-Type': 'application/json',
                  },
               });
               if (!response.ok) {
                  throw new Error('Network response was not ok');
               }
               const body = await response.json();
               console.log(body);
               setEvent(body);
            } catch (error) {
               console.error(`Error fetching newsfeed with id ${id}:`, error);
            }
         };

         fetchNewsfeedEntry();
      }
   }, [keycloak.authenticated]);

   return(
      <>
         <Card className=" rounded  mb-0">
            <div className="event-images">
               <Link to={`/dashboards/app/dance-event-detail/${event.id}`}>
                  <img src={placeholder} className="img-fluid" alt="Responsive"/>
               </Link>
            </div>
            <Card.Body>
               <div className="d-flex">
                  <div className="date-of-event">
                     <span>{event.startDate !== undefined ? format(new Date(event.startDate), "d") : ''}</span>
                     <h5>{event.startDate !== undefined ? format(new Date(event.startDate), "MMM") : ''}</h5>
                     <span>{event.startDate !== undefined ? format(new Date(event.startDate), "y") : ''}</span>
                  </div>
                  <div className="events-detail ms-3">
                     <h5><Link to={`/dashboards/app/dance-event-detail/${event.id}`}>{event.name}</Link></h5>
                     <p>{event.location}</p>
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
      </>
  )
}

export default DanceEventCard;