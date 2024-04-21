import {Button, Form, ListGroup, ListGroupItem, Modal} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import {useKeycloak} from "@react-keycloak/web";
import _ from "lodash";
import DanceAbortButton from "./dance-abort-button";
import {EVENTS_CLOSED_ENDPOINT, putData} from "../../../components/util/network";
import {toast} from "react-toastify";
import DanceFormInput from "./dance-form-input";

const DanceEventDetailHeaderEditButton=({data, setData})=> {

  const { keycloak, initialized } = useKeycloak();

  const [showEditMainSettings, setShowEditMainSettings] = useState(false);
  const handleCloseEditMainSettings = () => setShowEditMainSettings(false);
  const handleShowEditMainSettings = () => setShowEditMainSettings(true);

  const resetStateAndCloseMainDialog = () => {
    setForm(INITIAL_STATE);
    setIsMultipleDays(INITIAL_STATE.endDate !== null && INITIAL_STATE.endDate !== '' )
    setShowEditMainSettings(false);
  }

  const [isMultipleDays, setIsMultipleDays] = useState(false);

  const [form, setForm] = useState([]);

  const [INITIAL_STATE, setInitialState] = useState([]);

  const hasAnyDirtyField = () =>
      Object.keys(form).some((key) => form[key] !== INITIAL_STATE[key])
  || ((INITIAL_STATE['endDate'] == null && isMultipleDays) || (INITIAL_STATE['endDate'] !== null && !isMultipleDays));

  useEffect(() => {
    setForm(_.clone(data));
    setInitialState(_.clone(data));
    setIsMultipleDays(data.endDate !== null && data.endDate !== '' )
  }, [data]);

  const handleTemporaryChange = (event) => {
    setForm({
      ...form,
      [event.target.id]: event.target.value,
    });
  };

  const handleCheckboxChange = (event) => {
    setIsMultipleDays(event.target.checked);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    //TODO implement new validation schema
    if(hasAnyDirtyField()) {
      let newEventData = _.clone(form);
      if(!isMultipleDays) {
        newEventData['endDate'] = null;
      }
      else if (new Date(newEventData.startDate) >= new Date(newEventData.endDate)){
        toast.error("Start date must be before end date!");
        return;
      }

      await putData(`${EVENTS_CLOSED_ENDPOINT}/${form.id}`, newEventData, keycloak.token);

      setData(newEventData);
      toast.success("Event successfully updated");
    }

    setShowEditMainSettings(false);
  };

  return(
      <>{/* TODO form validation */}
        <Button variant="info" size="sm" className="d-inline-flex mb-1" onClick={handleShowEditMainSettings}><i className="material-symbols-outlined me-0">edit</i></Button>{' '}
        <Modal centered show={showEditMainSettings} onHide={handleCloseEditMainSettings}>
          <Modal.Header closeButton>
            <Modal.Title>Edit Event</Modal.Title>
          </Modal.Header>
          <Form onSubmit={handleSubmit}>
            <Modal.Body>
              <ListGroup>
                <ListGroupItem>
                  <DanceFormInput id="name" label="Name" placeholder="Name of the event..." type="text" value={form.name} onChange={handleTemporaryChange} required="required"/>
                  <DanceFormInput id="startDate" label={isMultipleDays ? "Start date" : "Date"} type="date" value={form.startDate} onChange={handleTemporaryChange} required="required"/>
                  <div className="d-flex justify-content-end">
                    {/*TODO this element should not be validated*/}
                    <Form.Group className="form-check form-switch ms-auto">
                      <Form.Check type="checkbox" className="bg-primary" defaultChecked={isMultipleDays} onChange={handleCheckboxChange} id="isMultipleDays"/>
                      <Form.Check.Label>Multiple days</Form.Check.Label>
                    </Form.Group>
                  </div>
                  <DanceFormInput id="endDate" label="End date" type="date" value={form.endDate} onChange={handleTemporaryChange} disabled={!isMultipleDays} required={isMultipleDays}/>
                  <DanceFormInput id="location" label="Location" placeholder="Where does it take place..." type="text" value={form.location} onChange={handleTemporaryChange}/>
                  <DanceFormInput id="website" label="Website" placeholder="Website of the event..." type="text" value={form.website} onChange={handleTemporaryChange}/>
                  <DanceFormInput id="email" label="E-Mail" placeholder="Contact e-mail..." type="email" value={form.email} onChange={handleTemporaryChange}/>
                </ListGroupItem>
              </ListGroup>
            </Modal.Body>
            <Modal.Footer>
              <DanceAbortButton hasAnyDirtyField={hasAnyDirtyField} resetStateAndCloseMainDialog={resetStateAndCloseMainDialog}/>
              <Button type="submit" variant="primary">
                Confirm
              </Button>
            </Modal.Footer>
          </Form>
        </Modal>
      </>
  )
}

export default DanceEventDetailHeaderEditButton