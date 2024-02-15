import {Button, Form, ListGroup, ListGroupItem, Modal} from "react-bootstrap";
import React, {useState} from "react";
import { format } from 'date-fns';
import {useKeycloak} from "@react-keycloak/web";

const DanceEventDetailHeaderEditButton=({data, handleChange})=> {

  const { keycloak, initialized } = useKeycloak();

  const [showEditMainSettings, setShowEditMainSettings] = useState(false);
  const handleCloseEditMainSettings = () => setShowEditMainSettings(false);
  const handleShowEditMainSettings = () => setShowEditMainSettings(true);

  const [isMultipleDays, setIsMultipleDays] = useState(false);

  const INITIAL_STATE = {
    name: data.name,
    date: data.date,
    enddate: data.enddate,
    address: data.address,
    url: data.url,
    mail: data.mail
  };

  const [form, setForm] = React.useState(INITIAL_STATE);

  const hasAnyDirtyField = (form) =>
      Object.keys(form).some((key) => form[key] !== INITIAL_STATE[key]);

  const handleCheckboxChange = (event) => {
    setIsMultipleDays(event.target.checked);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      if(hasAnyDirtyField(form)){}
      //TODO submit only when dirty
      const response = await fetch(`/api/v1/event/${data.id}`, {
        method: 'PUT',
        headers: {
          Authorization: `Bearer ${keycloak.token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ ...form }),
      });

      console.log(response);

      if (!response.ok) {
        throw new Error('Network response was not ok');
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
                                  className="form-control"
                                  id="name"
                                  placeholder="Name of the event..."
                                  value={data.name}
                                  onChange={handleChange}
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
                                    value={data.date ? format(data.date, 'yyyy-MM-dd') : ''}
                                    onChange={handleChange} required/>
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
                                    value={data.enddate ? format(data.enddate, 'yyyy-MM-dd') : ''}
                                    onChange={handleChange}
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
                                  value={data.address != null ?  data.address : ''}
                                  onChange={handleChange}/>
                  </Form.Group>
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="url" className="form-label">Website:</Form.Label>
                    <Form.Control type="text"
                                  id="url"
                                  className="form-control"
                                  placeholder="Website of the event..."
                                  value={data.url != null ?  data.url : ''}
                                  onChange={handleChange}/>
                  </Form.Group>
                  <Form.Group className="form-group">
                    <Form.Label htmlFor="mail" className="form-label">E-Mail:</Form.Label>
                    <Form.Control type="email"
                                  id="mail"
                                  className="form-control"
                                  placeholder="Contact e-mail..."
                                  value={data.mail != null ?  data.mail : ''}
                                  onChange={handleChange}/>
                  </Form.Group>
                </ListGroupItem>
              </ListGroup>
            </Modal.Body>
            <Modal.Footer>
              <Button variant="secondary" onClick={handleCloseEditMainSettings}>
                Abort{/* TODO show warning when dirty and reset values*/}
              </Button>
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