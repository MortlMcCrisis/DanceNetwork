import {Button, Form, ListGroup, ListGroupItem, Modal} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import { format } from 'date-fns';
import {useKeycloak} from "@react-keycloak/web";
import _ from "lodash";
import DanceAbortButton from "./dance-abort-button";

const DanceEventDetailHeaderEditButton=({data, setData})=> {

  const { keycloak, initialized } = useKeycloak();

  const [showEditMainSettings, setShowEditMainSettings] = useState(false);
  const handleCloseEditMainSettings = () => setShowEditMainSettings(false);
  const handleShowEditMainSettings = () => setShowEditMainSettings(true);

  const resetStateAndCloseMainDialog = () => {
    setForm(INITIAL_STATE);
    setShowEditMainSettings(false);
  }

  const [isMultipleDays, setIsMultipleDays] = useState(false);

  const [form, setForm] = useState([]);

  const [INITIAL_STATE, setInitialState] = useState([]);

  const hasAnyDirtyField = () =>
      Object.keys(form).some((key) => form[key] !== INITIAL_STATE[key])
  || ((INITIAL_STATE['enddate'] == null && isMultipleDays) || (INITIAL_STATE['enddate'] !== null && !isMultipleDays));

  useEffect(() => {
    setForm(_.clone(data));
    setInitialState(_.clone(data));
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

    try {
      if(hasAnyDirtyField()) {
        //TODO validate startDate < endDate
        let newEventData = _.clone(form);
        if(!isMultipleDays)
        {
          newEventData['endDate'] = null;
        }

        const response = await fetch(`/api/v1/event/${form.id}`, {
          method: 'PUT',
          headers: {
            Authorization: `Bearer ${keycloak.token}`,
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({...newEventData}),
        });

        console.log("response:");
        console.log(response);

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }

        setData(newEventData);
      }

      setShowEditMainSettings(false);
    } catch (error) {
      console.error('Error saving event:', error);
    }
  };

  return(
      <>
        <button type="button" className="btn btn-primary ms-2 btn-sm d-flex align-items-center" onClick={handleShowEditMainSettings}>
          <span className="material-symbols-outlined  md-16">edit</span>
        </button>
        <Modal centered show={showEditMainSettings} onHide={handleCloseEditMainSettings}>
          <Modal.Header closeButton>
            <Modal.Title>Edit Event</Modal.Title>
          </Modal.Header>
          <Form onSubmit={handleSubmit}>
            <Modal.Body>
              <ListGroup>
                <ListGroupItem>
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="name" className="form-label">Name:</Form.Label>
                    <Form.Control type="text"
                                  className="vform-control"
                                  id="name"
                                  placeholder="Name of the event..."
                                  value={form.name}
                                  onChange={handleTemporaryChange}
                                  required/> {/* TODO change to custom (english) error message */ }
                  </Form.Group>
                  <div className="d-flex justify-content-end">
                    <Form.Group className="form-group d-inline-block me-3">
                      <Form.Label htmlFor="startDate"
                                  className="form-label">{isMultipleDays
                          ? "Start date" : "Date"}:</Form.Label>
                      <Form.Control type="date"
                                    className="form-control"
                                    id="startDate"
                                    value={form.startDate ? format(form.startDate, 'yyyy-MM-dd') : ''}
                                    onChange={handleTemporaryChange}
                                    required/>
                    </Form.Group>
                    <Form.Group className="form-group d-inline-block me-3">
                      <Form.Label htmlFor="endDate"
                                  className={`form-label ${!isMultipleDays
                                      ? 'text-muted' : ''}`}>End
                        date:</Form.Label>
                      <Form.Control disabled={!isMultipleDays}
                                    type="date"
                                    className="form-control"
                                    id="endDate"
                                    value={form.endDate ? format(form.endDate, 'yyyy-MM-dd') : ''}
                                    onChange={handleTemporaryChange}
                                    required={isMultipleDays}/>
                    </Form.Group>
                    <Form.Check className="form-check form-switch ms-auto">
                      <Form.Check type="checkbox" className="bg-primary"
                                  defaultChecked={isMultipleDays}
                                  onChange={handleCheckboxChange}
                                  id="isMultipleDays"/>
                      <Form.Check.Label>Multiple days</Form.Check.Label>
                    </Form.Check>
                  </div>
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="location" className="form-label">Location:</Form.Label>
                    <Form.Control type="text"
                                  id="location"
                                  className="form-control"
                                  placeholder="Where does it take place..."
                                  value={form.location != null ?  form.location : ''}
                                  onChange={handleTemporaryChange}/>
                  </Form.Group>
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="website" className="form-label">Website:</Form.Label>
                    <Form.Control type="text"
                                  id="website"
                                  className="form-control"
                                  placeholder="Website of the event..."
                                  value={form.website != null ?  form.website : ''}
                                  onChange={handleTemporaryChange}/>
                  </Form.Group>
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="mail" className="form-label">E-Mail:</Form.Label>
                    <Form.Control type="email"
                                  id="email"
                                  className="form-control"
                                  placeholder="Contact e-mail..."
                                  value={form.email != null ?  form.email : ''}
                                  onChange={handleTemporaryChange}/>
                  </Form.Group>
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