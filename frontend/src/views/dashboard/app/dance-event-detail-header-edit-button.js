import {Button, Form, ListGroup, ListGroupItem, Modal} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import { format } from 'date-fns';
import {useKeycloak} from "@react-keycloak/web";
import _ from "lodash";

const DanceEventDetailHeaderEditButton=({data, setData})=> {

  const { keycloak, initialized } = useKeycloak();

  const [showEditMainSettings, setShowEditMainSettings] = useState(false);
  const handleCloseEditMainSettings = () => setShowEditMainSettings(false);
  const handleShowEditMainSettings = () => setShowEditMainSettings(true);

  const [showAbortWarning, setShowAbortWarning] = useState(false);
  const handleCloseAbortWarning = () => setShowAbortWarning(false);

  const abortAndDiscardChange = () => {
    setForm(INITIAL_STATE);

    setShowEditMainSettings(false);
    setShowAbortWarning(false);
  }
  const handleAbort = () => {
    if(hasAnyDirtyField(form)){
      setShowAbortWarning(true);
    }
    else {
      setShowEditMainSettings(false);
    }
  }

  const [isMultipleDays, setIsMultipleDays] = useState(false);

  const [form, setForm] = useState([]);

  const [INITIAL_STATE, setInitialState] = useState([]);

  const hasAnyDirtyField = (form) =>
      Object.keys(form).some((key) => form[key] !== INITIAL_STATE[key]);

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
      if(hasAnyDirtyField(form)) {
        const response = await fetch(`/api/v1/event/${form.id}`, {
          method: 'PUT',
          headers: {
            Authorization: `Bearer ${keycloak.token}`,
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({...form}),
        });

        console.log("response:");
        console.log(response);

        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
      }

      setData(form);
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
                      <Form.Label htmlFor="date"
                                  className="form-label">{isMultipleDays
                          ? "Start date" : "Date"}:</Form.Label>
                      <Form.Control type="date"
                                    className="form-control"
                                    id="date"
                                    value={form.date ? format(form.date, 'yyyy-MM-dd') : ''}
                                    onChange={handleTemporaryChange} required/>
                    </Form.Group>
                    <Form.Group className="form-group d-inline-block me-3">
                      <Form.Label htmlFor="enddate"
                                  className={`form-label ${!isMultipleDays
                                      ? 'text-muted' : ''}`}>End
                        date:</Form.Label>
                      <Form.Control disabled={!isMultipleDays}
                                    type="date"
                                    className="form-control"
                                    id="enddate"
                                    value={form.enddate ? format(form.enddate, 'yyyy-MM-dd') : ''}
                                    onChange={handleTemporaryChange}
                                    required={!isMultipleDays}/>
                    </Form.Group>
                    <Form.Check className="form-check form-switch ms-auto">
                      {/* TODO save null, when checkbox is not set */}
                      <Form.Check type="checkbox" className="bg-primary"
                                  defaultChecked={isMultipleDays}
                                  onChange={handleCheckboxChange}
                                  id="isMultipleDays"/>
                      <Form.Check.Label>Multiple days</Form.Check.Label>
                    </Form.Check>
                  </div>
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="address" className="form-label">Location:</Form.Label>
                    <Form.Control type="text"
                                  id="address"
                                  className="form-control"
                                  placeholder="Where does it take place..."
                                  value={form.address != null ?  form.address : ''}
                                  onChange={handleTemporaryChange}/>
                  </Form.Group>
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="url" className="form-label">Website:</Form.Label>
                    <Form.Control type="text"
                                  id="url"
                                  className="form-control"
                                  placeholder="Website of the event..."
                                  value={form.url != null ?  form.url : ''}
                                  onChange={handleTemporaryChange}/>
                  </Form.Group>
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="mail" className="form-label">E-Mail:</Form.Label>
                    <Form.Control type="email"
                                  id="mail"
                                  className="form-control"
                                  placeholder="Contact e-mail..."
                                  value={form.mail != null ?  form.mail : ''}
                                  onChange={handleTemporaryChange}/>
                  </Form.Group>
                </ListGroupItem>
              </ListGroup>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={handleAbort}>
                Abort{/* TODO show warning when dirty and reset values*/}
              </Button>
              <Modal size="sm" show={showAbortWarning} onHide={handleCloseAbortWarning}>
                <Modal.Header closeButton>{/*TODO das hier als separate komponente raus ziehen?*/}
                  <Modal.Title>Modal heading</Modal.Title>
                </Modal.Header>
                <Modal.Body>Changes will not be saved.</Modal.Body>
                <Modal.Footer>
                  <Button variant="secondary" onClick={handleCloseAbortWarning}>
                    Cancel
                  </Button>
                  <Button variant="primary" onClick={abortAndDiscardChange}>
                    Ok
                  </Button>
                </Modal.Footer>
              </Modal>
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