import React, { useState } from "react";
import {Link} from 'react-router-dom'
import {Container, Row, Col, Form, Button} from 'react-bootstrap'
import Card from '../../../components/Card'

//IMG
import store1 from '../../../assets/images/store/01.jpg'
import store2 from '../../../assets/images/store/02.jpg'
import store3 from '../../../assets/images/store/03.jpg'
import store4 from '../../../assets/images/store/04.jpg'
import cart from '../../../assets/images/icon/cart.png'
import pageimg from '../../../assets/images/page-img/profile-bg7.jpg' 

//profile-header
import ProfileHeader from '../../../components/profile-header'
import DanceTicket
    from "../../../components/dance-buy-tickets/dance-ticket";
import DanceBuyTicketHeader
    from "../../../components/dance-buy-tickets/dance-buy-ticket-header";

const DanceBuyTickets = () => {

  // Set the initial count state to zero, 0
  const [count, setCount] = useState(0);
  const [count1, setCount1] = useState(0);
  const [count2, setCount2] = useState(0);
  const [count3, setCount3] = useState(0);
  const [show,setShow] = useState('')
    return (
        <>
            <div id="content-page" className="content-page">
                <Container>
                    <DanceBuyTicketHeader/>
                    <Row>
                        <div id="cart" className={`cart-card-block p-0 col-12 ${show === 'address' || show === 'payment' ? '' :'show' }`}>
                            <Row className="align-item-center">
                                <Col lg="8">
                                    <DanceTicket/>
                                    <Card>
                                        <Card.Header className=" d-flex justify-content-between iq-border-bottom mb-0">
                                            <div className="header-title">
                                                <h4 className="card-title">Shopping Cart</h4>
                                            </div>
                                        </Card.Header>
                                        <Card.Body>
                                            <div className="checkout-product">
                                                <Row className=" align-items-center">
                                                    <Col sm="2">
                                                        <span className="checkout-product-img">
                                                        <Link to="#"><img className="img-fluid rounded" src={store1} alt=""/></Link>
                                                        </span>
                                                    </Col>
                                                    <Col sm="4">
                                                        <div className="checkout-product-details">
                                                        <h5>The Raze night book</h5>
                                                        <p className="text-success">In stock</p>
                                                        </div>
                                                    </Col>
                                                    <Col sm="6">
                                                        <Row>
                                                            <Col sm="10" className="col-10">
                                                                <Row className=" align-items-center ">
                                                                    <Col sm="7" md="6" className=" col-8">
                                                                        <div className="quantity buttons_added">
                                                                            <input type="button" defaultValue="-" className="minus h5" onClick={() => setCount(count - 1)} />
                                                                            <input type="button" defaultValue={count} title="Qty" className="input-text qty text ms-1"  />
                                                                            <input type="button" defaultValue="+" className="plus h5" onClick={() => setCount(count + 1)}/>
                                                                        </div>
                                                                    </Col>
                                                                    <Col sm="5" md="6" className=" col-4">
                                                                        <span className="product-price">$15</span>
                                                                    </Col>
                                                                </Row>
                                                            </Col>
                                                            <Col sm="2" className=" col-2">
                                                                <Link to="#" className="text-dark material-symbols-outlined">delete</Link>
                                                            </Col>
                                                        </Row>
                                                    </Col>
                                                </Row>
                                            </div>
                                        </Card.Body>
                                    </Card>
                                    <Card>
                                        <Card.Body>
                                            <div className="checkout-product">
                                                <Row className=" align-items-center">
                                                    <Col sm="2">
                                                        <span className="checkout-product-img">
                                                            <Link to="#">
                                                                <img className="img-fluid rounded" src={store2} alt=""/>
                                                            </Link>
                                                        </span>
                                                    </Col>
                                                    <Col sm="4">
                                                        <div className="checkout-product-details">
                                                            <h5>Harsh Reality book</h5>
                                                            <p className="text-success">In stock</p>
                                                        </div>
                                                    </Col>
                                                    <Col sm="6">
                                                        <Row>
                                                            <Col sm="10" className="col-10">
                                                                <Row className=" align-items-center ">
                                                                    <Col sm="7" md="6" className=" col-8">
                                                                        <div className="quantity buttons_added">
                                                                            <input type="button" defaultValue="-" className="minus h5" onClick={() => setCount1(count1 - 1)} />
                                                                            <input type="button" defaultValue={count1} title="Qty" className="input-text qty text ms-1"  />
                                                                            <input type="button" defaultValue="+" className="plus h5" onClick={() => setCount1(count1 + 1)}/>
                                                                        </div>
                                                                    </Col>
                                                                    <Col sm="5" md="6" className=" col-4">
                                                                        <span className="product-price">$25</span>
                                                                    </Col>
                                                                </Row>
                                                            </Col>
                                                            <Col sm="2" className=" col-2">
                                                            <Link to="#" className="text-dark material-symbols-outlined">delete</Link>
                                                            </Col>
                                                        </Row>
                                                    </Col>
                                                </Row>
                                            </div>
                                        </Card.Body>
                                    </Card>
                                    <Card>
                                        <Card.Body>
                                            <div className="checkout-product">
                                                <Row className=" align-items-center">
                                                    <Col sm="2">
                                                        <span className="checkout-product-img">
                                                            <Link to="#">
                                                                <img className="img-fluid rounded" src={store3} alt=""/>
                                                            </Link>
                                                        </span>
                                                    </Col>
                                                    <Col sm="4">
                                                        <div className="checkout-product-details">
                                                            <h5>The House in the Fog</h5>
                                                            <p className="text-success">In stock</p>
                                                        </div>
                                                    </Col>
                                                    <Col sm="6">
                                                        <Row>
                                                            <Col sm="10" className="col-10">
                                                                <Row className=" align-items-center ">
                                                                    <Col sm="7" md="6" className=" col-8">
                                                                        <div className="quantity buttons_added">
                                                                            <input type="button" defaultValue="-" className="minus h5" onClick={() => setCount2(count2 - 1)} />
                                                                            <input type="button" defaultValue={count2} title="Qty" className="input-text qty text ms-1"  />
                                                                            <input type="button" defaultValue="+" className="plus h5" onClick={() => setCount2(count2 + 1)}/>
                                                                        </div>
                                                                    </Col>
                                                                    <Col sm="5" md="6" className=" col-4">
                                                                        <span className="product-price">$18</span>
                                                                    </Col>
                                                                </Row>
                                                            </Col>
                                                            <Col sm="2" className=" col-2">
                                                            <Link to="#" className="text-dark material-symbols-outlined">delete</Link>
                                                            </Col>
                                                        </Row>
                                                    </Col>
                                                </Row>
                                            </div>
                                        </Card.Body>
                                    </Card>
                                    <Card>
                                        <Card.Body>
                                            <div className="checkout-product">
                                                <Row className=" align-items-center">
                                                    <Col sm="2">
                                                        <span className="checkout-product-img">
                                                            <Link to="#">
                                                                <img className="img-fluid rounded" src={store4} alt=""/>
                                                            </Link>
                                                        </span>
                                                    </Col>
                                                    <Col sm="4">
                                                        <div className="checkout-product-details">
                                                            <h5>The badges</h5>
                                                            <p className="text-success">In stock</p>
                                                        </div>
                                                    </Col>
                                                    <Col sm="6">
                                                        <Row>
                                                            <Col sm="10"  className="col-10">
                                                                <Row className="align-items-center">
                                                                    <Col sm="7" md="6"  className="col-8">
                                                                        <div className="quantity buttons_added">
                                                                            <input type="button" defaultValue="-" className="minus h5" onClick={() => setCount3(count3 - 1)} />
                                                                            <input type="button" defaultValue={count3} title="Qty" className="input-text qty text ms-1"  />
                                                                            <input type="button" defaultValue="+" className="plus h5" onClick={() => setCount3(count3 + 1)}/>
                                                                        </div>
                                                                    </Col>
                                                                    <Col sm="5" md="6" className="col-4">
                                                                        <span className="product-price">$28</span>
                                                                    </Col>
                                                                </Row>
                                                            </Col>
                                                            <Col sm="2" className="col-2">
                                                            <Link to="#" className="text-dark material-symbols-outlined">delete</Link>
                                                            </Col>
                                                        </Row>
                                                    </Col>
                                                </Row>
                                            </div>
                                        </Card.Body>
                                    </Card>
                                </Col>
                                <Col lg="4">
                                    <Card>
                                        <Card.Body>
                                            <p><b>Order Details</b></p>
                                            <div className="d-flex justify-content-between mb-2">
                                                <span>cart(4)</span>
                                                <span>$86</span>
                                            </div>
                                            <div className="d-flex justify-content-between mb-2">
                                                <span>Bag Discount</span>
                                                <span className="text-success">-10$</span>
                                            </div>
                                            <div className="d-flex justify-content-between mb-4">
                                                <span>Delivery Charges</span>
                                                <span className="text-success">Free</span>
                                            </div>
                                            <hr/>
                                            <div className="d-flex justify-content-between mb-4">
                                                <span className="text-dark"><strong>Total</strong></span>
                                                <span className="text-dark"><strong>$76</strong></span>
                                            </div>
                                            <Link id="place-order" to="#" className="btn btn-primary d-block mt-3 next" onClick={() => setShow('address')}>Place order</Link>
                                        </Card.Body>
                                    </Card>
                                </Col>
                            </Row>
                        </div>
                    </Row>
                </Container>
            </div>
        </>
    )
}

export default DanceBuyTickets