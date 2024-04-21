import React, {useState} from 'react'
import {
   Button,
   Form,
   ListGroup,
   ListGroupItem,
   Modal,
} from 'react-bootstrap'

import {useKeycloak} from "@react-keycloak/web";
import DanceAbortButton from "./dance-abort-button";
import DanceFormInput from "./dance-form-input";
import {toast} from "react-toastify";
import {EVENTS_CLOSED_ENDPOINT, postData} from "../../../components/util/network";

const DanceEventsCreateButton =() =>{
   const [showCreate, setShowCreate] = useState(false);
   const handleCloseCreate = () => setShowCreate(false);
   const handleShowCreate = () => setShowCreate(true);

   const { keycloak, initialized } = useKeycloak();

   const [isMultipleDays, setIsMultipleDays] = useState(false);

   const [isValidated, setValidated] = useState(false);

   const hasAnyDirtyField = () =>
       Object.keys(form).some((key) => form[key] !== INITIAL_STATE[key]);

   const resetStateAndCloseMainDialog = () => {
      setIsMultipleDays( false );
      setForm(INITIAL_STATE);
      setShowCreate(false);

      setValidated(false);
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

      if (event.currentTarget.checkValidity() === false) {
         event.stopPropagation();
         setValidated(true);

         toast.error("Please enter all required information!");

         return;
      }
      setValidated(true);

      if(new Date(form.startDate) >= new Date(form.endDate)){
         event.stopPropagation();
         setValidated(true);

         toast.error("Start date must be before end date!");

         return;
      }

      const response = await postData(EVENTS_CLOSED_ENDPOINT, form, keycloak.token);
      if (response.status === 201) {
         const resourceUrl = response.headers.get('Location');
         const id = resourceUrl.split('/').pop();
         window.location = `/dashboards/app/dance-event-detail/${id}`;
      }
   };

   return(
       <div className="btn dance-btn-fixed-bottom btn-danger btn-icon btn-setting" >
         <Button variant="info" className="rounded-pill mb-1" onClick={handleShowCreate}>Create event</Button>
         <Modal centered show={showCreate} onHide={handleCloseCreate}>
            <Modal.Header closeButton>
               <Modal.Title>Create Event</Modal.Title>
            </Modal.Header>
            <Form onSubmit={handleSubmit} className="needs-validation" noValidate validated={isValidated}>
               <Modal.Body>
                  <ListGroup>
                     <ListGroupItem>
                        <DanceFormInput id="name" label="Name" placeholder="Name of the event..." type="text" value={form.name} onChange={handleChange} required="required"/>
                        <DanceFormInput id="startDate" label={isMultipleDays ? "Start date" : "Date"} type="date" onChange={handleChange} required="required"/>
                        <div className="d-flex justify-content-end">
                           {/*TODO this element should not be validated*/}
                           <Form.Group className="form-check form-switch ms-auto">
                              <Form.Check type="checkbox" className="bg-primary" defaultChecked={isMultipleDays} onChange={handleCheckboxChange} id="isMultipleDays"/>
                              <Form.Check.Label>Multiple days</Form.Check.Label>
                           </Form.Group>
                        </div>
                        <DanceFormInput id="endDate" label="End date" type="date" onChange={handleChange} disabled={!isMultipleDays} required={isMultipleDays}/>
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