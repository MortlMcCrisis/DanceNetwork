import {Col, Form, Row} from "react-bootstrap";
import React from "react";

// TODO all validation must be better.
//  - When a custom validation check fails the field should be red
//  - non required fields and and control elements should be not validated
const DanceFormInput = ({id, label, type, value, onChange, placeholder, disabled, required}) => {
  return (
    <Form.Group as={Row} className="form-group">
      <Form.Label htmlFor={id} column sm={3}
                  className={`control-label align-self-center mb-0 ${disabled ? 'text-muted' : ''}`}>{label}{required ? ": *" : ":"}
      </Form.Label>
      <Col sm={9}>
          <Form.Control type={type}
                        className="form-control"
                        id={id}
                        value={value}
                        onChange={onChange}
                        placeholder={placeholder}
                        disabled={disabled}
                        required={required}/>
      </Col>
    </Form.Group>
  )
}

export default DanceFormInput;