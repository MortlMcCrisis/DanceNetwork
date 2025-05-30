import React from 'react'
import { Dropdown , Tooltip, OverlayTrigger} from 'react-bootstrap'
import img from '../../assets/images/user/user-1.jpg'
import CustomToggle from "../dropdowns";
import icon1 from "../../assets/images/icon/01.png";
import icon2 from "../../assets/images/icon/02.png";
import icon3 from "../../assets/images/icon/03.png";
import icon4 from "../../assets/images/icon/04.png";
import icon5 from "../../assets/images/icon/05.png";
import icon6 from "../../assets/images/icon/06.png";
import icon7 from "../../assets/images/icon/07.png";
import ShareOffcanvas from "../share-offcanvas";
import DanceNewsfeedCardHeader from "./dance-newsfeed-card-header";
import {Link} from "react-router-dom";
import Card from "../Card";
const DanceNewsfeedCard = ({newsfeedEntry, creator}) => {
   
    return (
        <>
            {creator && newsfeedEntry.creationDate && (
                <Card className=" card-block card-stretch card-height">
                    <Card.Body>
                        <div className="card card-block card-stretch card-height">
                            <div className="card-body">
                                <DanceNewsfeedCardHeader type={newsfeedEntry.type} creator={creator} creationDate={newsfeedEntry.creationDate} />
                                <div className="mt-3">
                                    <p>{newsfeedEntry.textField}</p>
                                </div>
                                {newsfeedEntry.image != null &&
                                    <div className="text-center">
                                        <Link to="#"><img loading="lazy" src={newsfeedEntry.image} alt="gallary-img" className="img-fluid rounded"/></Link>
                                    </div>
                                }
                                <div className="comment-area mt-3">
                                    <div className="d-flex justify-content-between align-items-center flex-wrap">
                                        <div className="like-block position-relative d-flex align-items-center">
                                            <div className="d-flex align-items-center">
                                                <div className="like-data">
                                                    <Dropdown>
                                                        <Dropdown.Toggle  as={CustomToggle} >
                                                            <img src={icon1} className="img-fluid" alt=""/>
                                                        </Dropdown.Toggle>
                                                        <Dropdown.Menu className=" py-2">
                                                            <OverlayTrigger placement="top" overlay={<Tooltip>Like</Tooltip>} className="ms-2 me-2" ><img src={icon1} className="img-fluid me-2" alt=""/></OverlayTrigger>
                                                            <OverlayTrigger placement="top" overlay={<Tooltip>Love</Tooltip>} className="me-2" ><img src={icon2} className="img-fluid me-2" alt=""/></OverlayTrigger>
                                                            <OverlayTrigger placement="top" overlay={<Tooltip>Happy</Tooltip>} className="me-2" ><img src={icon3} className="img-fluid me-2" alt=""/></OverlayTrigger>
                                                            <OverlayTrigger placement="top" overlay={<Tooltip>HaHa</Tooltip>} className="me-2" ><img src={icon4} className="img-fluid me-2" alt=""/></OverlayTrigger>
                                                            <OverlayTrigger placement="top" overlay={<Tooltip>Think</Tooltip>} className="me-2" ><img src={icon5} className="img-fluid me-2" alt=""/></OverlayTrigger>
                                                            <OverlayTrigger placement="top" overlay={<Tooltip>Sade</Tooltip>} className="me-2" ><img src={icon6} className="img-fluid me-2" alt=""/></OverlayTrigger>
                                                            <OverlayTrigger placement="top" overlay={<Tooltip>Lovely</Tooltip>} className="me-2" ><img src={icon7} className="img-fluid me-2" alt=""/></OverlayTrigger>
                                                        </Dropdown.Menu>
                                                    </Dropdown>
                                                </div>
                                                <div className="total-like-block ms-2 me-3">
                                                    <Dropdown>
                                                        <Dropdown.Toggle as={CustomToggle}  id="post-option" >
                                                            140 Likes
                                                        </Dropdown.Toggle>
                                                        <Dropdown.Menu>
                                                            <Dropdown.Item  href="#">Max Emum</Dropdown.Item>
                                                            <Dropdown.Item  href="#">Bill Yerds</Dropdown.Item>
                                                            <Dropdown.Item  href="#">Hap E. Birthday</Dropdown.Item>
                                                            <Dropdown.Item  href="#">Tara Misu</Dropdown.Item>
                                                            <Dropdown.Item  href="#">Midge Itz</Dropdown.Item>
                                                            <Dropdown.Item  href="#">Sal Vidge</Dropdown.Item>
                                                            <Dropdown.Item  href="#">Other</Dropdown.Item>
                                                        </Dropdown.Menu>
                                                    </Dropdown>
                                                </div>
                                            </div>
                                            <div className="total-comment-block">
                                                <Dropdown>
                                                    <Dropdown.Toggle as={CustomToggle}  id="post-option" >
                                                        20 Comment
                                                    </Dropdown.Toggle>
                                                    <Dropdown.Menu>
                                                        <Dropdown.Item  href="#">Max Emum</Dropdown.Item>
                                                        <Dropdown.Item  href="#">Bill Yerds</Dropdown.Item>
                                                        <Dropdown.Item  href="#">Hap E. Birthday</Dropdown.Item>
                                                        <Dropdown.Item  href="#">Tara Misu</Dropdown.Item>
                                                        <Dropdown.Item  href="#">Midge Itz</Dropdown.Item>
                                                        <Dropdown.Item  href="#">Sal Vidge</Dropdown.Item>
                                                        <Dropdown.Item  href="#">Other</Dropdown.Item>
                                                    </Dropdown.Menu>
                                                </Dropdown>
                                            </div>
                                        </div>
                                        <ShareOffcanvas />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </Card.Body>
                </Card>
            )}
        </>
    )
}
export default DanceNewsfeedCard;