import React, {useState} from 'react'
import {
   Button,
   Form,
   ListGroup,
   ListGroupItem,
   Modal,
   Row
} from 'react-bootstrap'

import {useKeycloak} from "@react-keycloak/web";
import DanceAbortButton from "./dance-abort-button";

const DanceEventsCreateButton =() =>{
   const [showCreate, setShowCreate] = useState(false);
   const handleCloseCreate = () => setShowCreate(false);
   const handleShowCreate = () => setShowCreate(true);

   const { keycloak, initialized } = useKeycloak();

   const [isMultipleDays, setIsMultipleDays] = useState(false);

   const hasAnyDirtyField = () =>
       Object.keys(form).some((key) => form[key] !== INITIAL_STATE[key]);

   const resetStateAndCloseMainDialog = () => {
      setIsMultipleDays( false );
      setForm(INITIAL_STATE);
      setShowCreate(false);
   }

   const handleCheckboxChange = (event) => {
      setIsMultipleDays(event.target.checked);
   };

   const INITIAL_STATE = {
      name: '',
      startDate: '',
      endDate: '',
      published: false
   }

   const [form, setForm] = React.useState(INITIAL_STATE);

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
       <div className="btn dance-btn-fixed-bottom btn-danger btn-icon btn-setting" >
         <Button variant="info" className="rounded-pill mb-1" onClick={handleShowCreate}>Create event</Button>
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
                  <DanceAbortButton hasAnyDirtyField={hasAnyDirtyField} resetStateAndCloseMainDialog={resetStateAndCloseMainDialog} />
                  <Button type="submit" variant="primary">
                     Create
                  </Button>
               </Modal.Footer>
            </Form>
         </Modal>
       </div>
  )
}

export default DanceEventsCreateButton;