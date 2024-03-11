import {Button, Card, Col, Row} from "react-bootstrap";
import React from "react";
import {format} from "date-fns";
import * as Util from "./dance-buy-tickets-util";

const DanceBuyTicketsSummary = ({ticketTypes, formData}) => {

  const  item_invoice = [
    {
      row: '1',
      name: 'Web Design',
      desc: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
      price: '$120.00',
      quantity: '5',
      total: '$2,880.00',
    },
    {
      row: '2',
      name: 'Web Design',
      desc: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
      price: '$120.00',
      quantity: '5',
      total: '$2,880.00',
    },
    {
      row: '3',
      name: 'Web Design',
      desc: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
      price: '$120.00',
      quantity: '5',
      total: '$2,880.00',
    },
    {
      row: '4',
      name: 'Web Design',
      desc: 'Lorem Ipsum is simply dummy text of the printing and typesetting industry.',
      price: '$120.00',
      quantity: '5',
      total: '$2,880.00',
    }
  ]

  return (
   <>
     <Col sm="12">
       <Card>
         <Card.Body>
           <Row>
             <Col lg="8">
               <div>
                <strong>Order date:</strong> {format(new Date(), 'MMMM d, yyyy')}
               </div>
               <div>
                 <strong>Order id:</strong> {Math.floor(Math.random() * 999999999) + 1000000000}
               </div>
             </Col>
             <Col lg="4" className="align-self-center">
               <h4 className="mb-0 float-end">Order summary</h4>
             </Col>
           </Row>
           <hr/>
           <Row>
             <Col sm="12">
               <h5>Overview</h5>
               <div className="table-responsive-sm">
                 <table className="table table-striped">
                   <thead>
                   <tr>
                     <th className="col-4" scope="col">Ticket</th>
                     <th className="col-6" scope="col">Personal Data</th>
                     <th className="col-2 text-end" scope="col">Price</th>
                   </tr>
                   </thead>
                   <tbody>
                   {formData.map((form, idx) => (
                           <tr key={idx}>
                             <th className="col">{Util.getNameForId(ticketTypes, form.ticketTypeId)}</th>
                             <td className="col">
                               <div>Name: {form.firstName} {form.lastName}</div>
                               <div>Address: {form.address}</div>
                               <div>Country: {form.country}</div>
                               <div>Gender: {form.gender}</div>
                               <div>Role: {form.role}</div>
                               <div>E-Mail: {form.email}</div>
                               <div>{form.phone !== undefined && form.phone !== null && form.phone !== '' ? "Phone: " + form.phone : ''}</div>
                             </td>
                             <td className="text-end"><b>${Util.getPriceForId(ticketTypes, form.ticketTypeId)}</b></td>
                           </tr>
                       ))}
                   </tbody>
                 </table>
               </div>
               <h5 className="mt-5">Payment</h5>
               <div className="table-responsive-sm">
                 <table className="table table-striped">
                   <thead>
                   <tr>
                     <th scope="col">Bank</th>
                     <th scope="col">.Acc.No</th>
                     <th scope="col">Due Date</th>
                     <th scope="col">Sub-total</th>
                     <th scope="col">Discount</th>
                     <th scope="col">Total</th>
                   </tr>
                   </thead>
                   <tbody>
                   <tr>
                     <th scope="row">Threadneedle St</th>
                     <td>12333456789</td>
                     <td>12 November 2019</td>
                     <td>$4597.50</td>
                     <td>10%</td>
                     <td><b>$4137.75 USD</b></td>
                   </tr>
                   </tbody>
                 </table>
               </div>
             </Col>
             <Col sm="6"></Col>
             <Col sm="6" className="text-end">
               <Button variant="link mr-3"><span className="d-flex align-items-center"><i className="material-symbols-outlined me-1">print</i> Download Print</span></Button>
               <Button>Submit</Button>
             </Col>
             <Col sm="12" className="mt-5">
               <b className="text-danger">Notes:</b>
               <p>It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English.</p>
             </Col>
           </Row>
         </Card.Body>
       </Card>
     </Col>
   </>
  )
}

export default DanceBuyTicketsSummary