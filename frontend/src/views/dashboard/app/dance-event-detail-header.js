import Card from "../../../components/Card";
import {
  Button,
  Col, Dropdown,
  Form,
  ListGroup,
  ListGroupItem,
  Modal,
  Row
} from "react-bootstrap";
import imgp1 from "../../../assets/images/user/15.jpg";
import {format} from "date-fns";
import {Link} from "react-router-dom";
import imgp2 from "../../../assets/images/user/05.jpg";
import imgp3 from "../../../assets/images/user/06.jpg";
import imgp4 from "../../../assets/images/user/07.jpg";
import imgp5 from "../../../assets/images/user/08.jpg";
import user9 from "../../../assets/images/user/1.jpg";
import small1 from "../../../assets/images/small/07.png";
import small2 from "../../../assets/images/small/08.png";
import small3 from "../../../assets/images/small/09.png";
import small4 from "../../../assets/images/small/10.png";
import small5 from "../../../assets/images/small/11.png";
import small6 from "../../../assets/images/small/12.png";
import small7 from "../../../assets/images/small/13.png";
import small8 from "../../../assets/images/small/14.png";
import React, {useState} from "react";
import DanceEventDetailHeaderEditButton
  from "./dance-event-detail-header-edit-button";

const DanceEventDetailHeader=()=> {

  const handleChange = (event) => {
  };

  return(
      <Card>
        <Card.Body>
          <Row>
            <Col lg="2">
              <div className="item1 ms-1">
                <img loading="lazy" src={imgp1} className="img-fluid rounded profile-image" alt="profile-img"/>
              </div>
            </Col>
            <Col lg="10">
              <div className="d-flex justify-content-between">
                <div className="item2 ">
                  <h4 className="">{data.name}</h4>
                  <span>60 630 are going</span>
                </div>
                <div className="item4 ms-1">
                  <div className="d-flex justify-content-between align-items-center ms-1 flex-wrap">
                    <DanceEventDetailHeaderEditButton data={data} handleChange={handleChange} />
                  </div>
                </div>
              </div>
              <Row>
                <Col lg="6">
                  <div className="item5 mt-3">
                    <div className="d-flex align-items-center mb-1">
                                            <span className="material-symbols-outlined md-18">
                                            date_range
                                            </span>
                      <span className="ms-2">{data.date ? format(new Date(data.date), "MMMM d, yyyy") : ''}</span>
                    </div>
                    {data.address && ( // TODO hier weitermachen
                        //TODO namen mal gleichziehen (location, address...)
                        <div className="d-flex align-items-center mb-1">
                                            <span className="material-symbols-outlined md-18">
                                            location_on
                                            </span>
                          <span className="ms-2">Augustinerweg 18, 79098 Freiburg</span>
                        </div>
                    )}
                    <div className="d-flex align-items-center mb-1">
                                            <span className="material-symbols-outlined md-18">
                                            language
                                            </span>
                      <Link to="#" className="link-primary h6 ms-2">https://www.bacharoyale.com</Link>
                    </div>
                    <div className="d-flex align-items-center mb-1">
                                            <span className="material-symbols-outlined md-18">
                                            mail
                                            </span>
                      <span className="ms-2">florence.vouriot@gmx.de</span>
                    </div>
                  </div>
                </Col>
                <Col lg="6">
                  <div className="item6 border-light border-start">
                    <div className="ms-2">
                      <h6 className="mb-2">Those people are going</h6>
                    </div>
                    <div className="iq-media-group ms-2">
                      <Link to="#" className="iq-media">
                        <img loading="lazy" className="img-fluid avatar-40 rounded-circle" src={imgp2} alt=""/>
                      </Link>
                      <Link to="#" className="iq-media">
                        <img loading="lazy" className="img-fluid avatar-40 rounded-circle" src={imgp3} alt=""/>
                      </Link>
                      <Link to="#" className="iq-media">
                        <img loading="lazy" className="img-fluid avatar-40 rounded-circle" src={imgp4} alt=""/>
                      </Link>
                      <Link to="#" className="iq-media">
                        <img loading="lazy" className="img-fluid avatar-40 rounded-circle" src={imgp5} alt=""/>
                      </Link>
                    </div>
                  </div>
                </Col>
              </Row>
            </Col>
          </Row>
        </Card.Body>
      </Card>
  )
}

export default DanceEventDetailHeader