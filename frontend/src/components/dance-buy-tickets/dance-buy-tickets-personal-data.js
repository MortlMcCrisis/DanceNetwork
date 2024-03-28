import Card from "../Card";
import {ListGroupItem} from "react-bootstrap";
import DanceFormInput from "../../views/dashboard/app/dance-form-input";
import DanceFormCheckbox from "../../views/dashboard/app/dance-form-checkbox";
import React from "react";
import * as Util from "./dance-buy-tickets-util";
import {getAmountOfTickets, getNameForId} from "./dance-buy-tickets-util";

const DanceBuyTicketsPersonalData = ({formData, handleFormDataChange}) => {

  const handleChange = (event, idx) => {
    event.target.id = normalizeId(event.target.id)
    handleFormDataChange(event, idx)
  }

  const setGender = (event, idx) => {
    switch(event.target.id) {
      case 'male':
        event.target.value  = 'MALE';
        break;
      case 'female':
        event.target.value  = 'FEMALE';
        break;
      case 'other':
        event.target.value  = 'OTHER';
        break
      default:
        event.target.value = '';
    }

    event.target.id = 'gender';

    handleFormDataChange(event, idx);
  }

  const setRole = (event, idx) => {
    switch(event.target.id) {
      case 'leader':
        event.target.value = 'LEADER';
        break;
      case 'follower':
        event.target.value = 'FOLLOWER';
        break;
      case 'both':
        event.target.value = 'BOTH';
        break
      default:
        event.target.value = '';
    }

    event.target.id = 'role';

    handleFormDataChange(event, idx);
  }

  const validateEmail = (event, idx) => {
    event.target.id = normalizeId(event.target.id)
    handleFormDataChange(event, idx);
  }

  const normalizeId = (id) => {
    const parts = id.split("-");
    return parts.length > 0 ? parts[0] : id;
  }

  return (
      <>
        {formData.map((form, idx) => (
            <Card key={idx}>
              <Card.Header>
                <h4>{form.ticketTypeName}</h4>
              </Card.Header>
              <Card.Body>
                <ListGroupItem>
                  <DanceFormInput id={`firstName-${idx}`} label="First name" placeholder="Enter your first name" type="text" onChange={(event) => handleChange(event, idx)} required="required"/>
                  <DanceFormInput id={`lastName-${idx}`} label="Last name" placeholder="Enter your last name" type="text" onChange={(event) => handleChange(event, idx)} required="required"/>
                  <DanceFormInput id={`address-${idx}`} label="Address" placeholder="Enter your address" type="text" onChange={(event) => handleChange(event, idx)} required="required"/>
                  <DanceFormInput id={`country-${idx}`} label="Country" placeholder="Enter your country" type="text" onChange={(event) => handleChange(event, idx)} required="required"/>
                  <DanceFormCheckbox id={`gender-${idx}`} label="Gender" values={[
                    {label: 'Male', id: 'male'},
                    {label: 'Female', id: 'female'},
                    {label: 'Other', id: 'other'}]}
                                     onChange={(event) => setGender(event, idx)} required="required"/>
                  <DanceFormCheckbox id={`role-${idx}`} label="Role" values={[
                    {label: 'Leader', id: 'leader'},
                    {label: 'Follower', id: 'follower'},
                    {label: 'Both', id: 'both'}]}
                                     onChange={(event) => setRole(event, idx)} required="required"/>
                </ListGroupItem>
                <DanceFormInput id={`email-${idx}`} label="E-mail" placeholder="Enter your e-mail" type="email" onChange={(event) => validateEmail(event, idx)} required="required"/>
                <DanceFormInput id={`emailConfirm-${idx}`} label="Confirm e-mail" placeholder="Confirm your e-mail" type="email" onChange={(event) => validateEmail(event, idx)} required="required"/>
                <DanceFormInput id={`phone-${idx}`} label="Phone" placeholder="Enter your phone" type="text" onChange={(event) => handleChange(event, idx)} />
              </Card.Body>
            </Card>
          ))
        }
    </>
  )
}

export default DanceBuyTicketsPersonalData;