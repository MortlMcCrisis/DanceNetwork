import {Col, Form, Row} from "react-bootstrap";
import React from "react";

const DanceFormCheckbox = ({id, label, values, onChange, disabled, required}) => {
  return (
      <Form.Group as={Row} className="form-group" noValidate>
        <Form.Label htmlFor={id} column sm={3}
                    className="control-label align-self-center mb-0">{label}{required ? ": *" : ":"}
        </Form.Label>
        <Col sm={9}>
          <Form.Check className="form-check" id={id}>
            {values.map((value, index) => (
                <Form.Check className="form-check form-check-inline" key={index}>
                  <Form.Check.Label disabled={disabled}> {value.label} </Form.Check.Label>
                  <Form.Check.Input disabled={disabled}
                                    defaultChecked={value.defaultChecked}
                                    type="radio"
                                    onChange={onChange}
                                    className="form-check-input"
                                    name={id} id={value.id}
                                    required={required}/>
                </Form.Check>
            ))}
          </Form.Check>
        </Col>
      </Form.Group>
  )
}

export default DanceFormCheckbox;