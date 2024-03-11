import {Col, Form, Row} from "react-bootstrap";
import React from "react";
import DanceUserAccountSetting from "./dance-user-account-setting";

const DanceFormInput = ({id, label, type, value, onChange, placeholder, disabled}) => {
  return (
    <Form.Group as={Row} className="form-group">
      <Form.Label htmlFor={id} column sm={3}
                  className="control-label align-self-center mb-0">{label}
      </Form.Label>
      <Col sm={9}>
        <Form.Control type={type} className="form-control" id={id}
                      value={value} onChange={onChange}
                      placeholder={placeholder} disabled={disabled}/>
      </Col>
    </Form.Group>
  )
}

export default DanceFormInput;