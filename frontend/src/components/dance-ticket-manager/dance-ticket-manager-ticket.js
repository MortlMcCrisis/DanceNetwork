import React from 'react'

import 'react-quill/dist/quill.snow.css';
import Card from "../Card";
import {Button, Col, Row} from "react-bootstrap";
import DanceRichEditor from "../dance-rich-editor/dance-rich-editor";
import DanceFormInput from "../../views/dashboard/app/dance-form-input";

const DanceTicketManagerTicket = ({name, setName, description, setDescription, price, setPrice, removeCallback}) => {
  return (
      <Card>
        <Card.Header>
          <Col sm="8" md="8">
            <DanceFormInput id="name" label="Name" type="text" required="required" value={name} onChange={setName}/>
          </Col>
          <Col sm="4" md="4">
            <div className="user-detail text-center mb-3">
              <div className="text-center mb-3">
                <button className="btn btn-primary" type="button" onClick={removeCallback} >-</button>
              </div>
            </div>
          </Col>
        </Card.Header>
        <Card.Body>
          <Row>
            <Col sm="6" md="8">
              <DanceRichEditor value={description} setValue={setDescription}/>
            </Col>
            <Col sm="6" md="4">
              {/*TODO validate amount*/}
              <DanceFormInput id="price" label="Price" type="number" required="required" value={price} onChange={setPrice}/>
            </Col>
          </Row>
        </Card.Body>
      </Card>
  )
}
export default DanceTicketManagerTicket;