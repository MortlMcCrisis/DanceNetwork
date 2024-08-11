import React, {useEffect, useState} from 'react'
import {Link, useParams} from 'react-router-dom'
import {
    Container,
    Row,
    Col,
    Modal,
    Button,
    Dropdown
} from 'react-bootstrap'
import Card from '../../../components/Card'
import CustomToggle from '../../../components/dropdowns'
import ReactFsLightbox from 'fslightbox-react';

import imgp11 from '../../../assets/images/user/15.jpg'
import imgp12 from '../../../assets/images/user/05.jpg'
import imgp13 from '../../../assets/images/user/06.jpg'
import imgp14 from '../../../assets/images/user/07.jpg'
import imgp25 from '../../../assets/images/user/1.jpg'
import imgp26 from '../../../assets/images/user/02.jpg'
import imgp27 from '../../../assets/images/user/05.jpg'
import imgp28 from '../../../assets/images/user/06.jpg'
import imgp29 from '../../../assets/images/user/07.jpg'
import imgp30 from '../../../assets/images/user/08.jpg'
import imgp31 from '../../../assets/images/user/02.jpg'
import imgp36 from '../../../assets/images/user/02.jpg'
import imgp42 from '../../../assets/images/user/02.jpg'
import imgp43 from '../../../assets/images/user/02.jpg'
import imgp44 from '../../../assets/images/user/02.jpg'
import imgp45 from '../../../assets/images/user/02.jpg'
import imgp46 from '../../../assets/images/user/02.jpg'
import imgp47 from '../../../assets/images/page-img/52.jpg'
import imgp48 from '../../../assets/images/page-img/53.jpg'
import imgp49 from '../../../assets/images/page-img/54.jpg'
import imgp50 from '../../../assets/images/page-img/55.jpg'
import ProfileHeader from '../../../components/profile-header'
import bg3 from '../../../assets/images/page-img/profile-bg3.jpg'
import g1 from '../../../assets/images/page-img/g1.jpg'
import g2 from '../../../assets/images/page-img/g2.jpg'
import g3 from '../../../assets/images/page-img/g3.jpg'
import g4 from '../../../assets/images/page-img/g4.jpg'
import g5 from '../../../assets/images/page-img/g5.jpg'
import g6 from '../../../assets/images/page-img/g6.jpg'
import g7 from '../../../assets/images/page-img/g7.jpg'
import g8 from '../../../assets/images/page-img/g8.jpg'
import g9 from '../../../assets/images/page-img/g9.jpg'
import user9 from '../../../assets/images/user/1.jpg'
import small1 from '../../../assets/images/small/07.png'
import small2 from '../../../assets/images/small/08.png'
import small3 from '../../../assets/images/small/09.png'
import small4 from '../../../assets/images/small/10.png'
import small5 from '../../../assets/images/small/11.png'
import small6 from '../../../assets/images/small/12.png'
import small7 from '../../../assets/images/small/13.png'
import small8 from '../../../assets/images/small/14.png'
import user1 from '../../../assets/images/user/1.jpg'
import small07 from '../../../assets/images/small/07.png'
import small08 from '../../../assets/images/small/08.png'
import small09 from '../../../assets/images/small/09.png'

import DanceEventDetailHeader from "./dance-event-detail-header";
import DanceImageGallerySelectableImage
    from "../../../components/image-gallery/image-gallery-selectable-image";
import {useKeycloak} from "@react-keycloak/web";
import {
    EVENTS_CLOSED_ENDPOINT,
    EVENTS_OPEN_ENDPOINT,
    fetchData, putData, USERS_OPEN_ENDPOINT
} from "../../../components/util/network";
import {toast} from "react-toastify";
import img53 from "../../../assets/images/page-img/53.jpg";
import user05 from "../../../assets/images/user/05.jpg";
import user06 from "../../../assets/images/user/06.jpg";
import user07 from "../../../assets/images/user/07.jpg";
import user08 from "../../../assets/images/user/08.jpg";
import user09 from "../../../assets/images/user/09.jpg";
import DateIcon from "../../../components/text_icons/date";
import TimeIcon from "../../../components/text_icons/time";
import LocationIcon from "../../../components/text_icons/location";
import WebsiteIcon from "../../../components/text_icons/website";
import EmailIcon from "../../../components/text_icons/email";
import OwnerIcon from "../../../components/text_icons/owner";
import loader from "../../../assets/images/page-img/page-load-loader.gif";
import DanceEventDetailHeaderEditButton
    from "./dance-event-detail-header-edit-button";

// Fslightbox plugin
const FsLightbox = ReactFsLightbox.default ? ReactFsLightbox.default : ReactFsLightbox;

const DanceEventDetail=()=>{

    const { keycloak, initialized } = useKeycloak();

    const { id } = useParams();

    const [data, setData] = useState([]);

    const [show, setShow] = useState(false);
    const handleClose = () => setShow(false);
    const handleShow = () => setShow(true);

    const [creators, setCreators] = useState([]);

    useEffect(() => {
        if(data.creator != null && data.creator != "") {
            fetchData(`${USERS_OPEN_ENDPOINT}?userUUID=${data.creator}`, setCreators);
        }
    }, [data.creator]);

    useEffect(() => {
        fetchData(`${EVENTS_OPEN_ENDPOINT}/${id}`, setData);
    }, []);

    const setBannerImage=(path)=>{
        setBannerImageAndSave(path, saveEvent)
    }

    const saveEvent = async (dataToSave) => {
        await putData(`${EVENTS_CLOSED_ENDPOINT}/${id}`, JSON.stringify({...dataToSave}), keycloak.token);
        toast.success("Event successfully updated");
    }

    const setBannerImageAndSave=(path, saveCallback)=>{
        setData(prevData => {
            const newData = {
                ...prevData,
                bannerImage: path,
            };
            saveCallback(newData);
            return newData;
        });
    }

    const [imageController, setImageController] = useState({
        toggler: false,
        slide: 1
    });

    function imageOnSlide(number) {
        setImageController({
            toggler: !imageController.toggler,
            slide: number
        });
    }
    return(
        <>
            <div className="btn dance-btn-fixed-bottom btn-danger btn-icon btn-setting" >
                <Link to={`/dashboards/app/dance-event-detail/${id}/dance-buy-ticket`}>
                    <Button variant="success" className="rounded-pill mb-1">Tickets</Button>{' '}
                </Link>
            </div>
            <FsLightbox
                toggler={imageController.toggler}
                sources={[g1,g2,g3,g4,g5,g6,g7,g8,g9]}
                slide={imageController.slide}
            />
            {keycloak.authenticated && initialized && keycloak.idTokenParsed.sub === data.creator ? (
                <DanceImageGallerySelectableImage setImage={setBannerImage}>
                    <ProfileHeader title="Profile 2" img={data.bannerImage != null ? data.bannerImage : bg3}/>
                </DanceImageGallerySelectableImage>
            ) : (
                <ProfileHeader title="Profile 2" img={data.bannerImage != null ? data.bannerImage : bg3}/>
            )
            }
            <div className='profile-2'>
                <div id='content-page' className='content-page'>
                    <Container>
                        <Row>
                            <Col lg="12">
                                <DanceEventDetailHeader data={data} setData={setData} creator={creators[0]}/>
                            </Col>
                        </Row>
                        <Row>
                            <Col lg="4">
                                <Card>
                                    <Card.Header className="d-flex align-items-center justify-content-between">
                                        <div className="header-title">
                                            <h4>Admin</h4>
                                        </div>
                                    </Card.Header>
                                    <Card.Body>
                                        <div className="d-grid gap-2">
                                            <Link to={`/dashboards/app/dance-ticket-manager/${id}`} className="btn btn-primary">
                                                Manage Tickets
                                            </Link>
                                        </div>
                                    </Card.Body>
                                </Card>
                                <Card>
                                    <Card.Header>
                                        <h4>Info</h4>
                                        {keycloak.authenticated && initialized && keycloak.idTokenParsed.sub === data.creator &&
                                            <div className="item4 ms-1">
                                                <div className="d-flex justify-content-between align-items-center ms-1 flex-wrap">
                                                    <DanceEventDetailHeaderEditButton data={data} setData={setData} />
                                                </div>
                                            </div>
                                        }
                                    </Card.Header>
                                    <Card.Body>
                                        <div className="item5 mt-3">
                                            <DateIcon startDate={data.startDate} endDate={data.endDate} />
                                            <TimeIcon text={data.startTime} />
                                            {data.location &&
                                                <LocationIcon text={data.location} />
                                            }
                                            {data.website &&
                                                <WebsiteIcon text={data.website} />
                                            }
                                            {data.email &&
                                                <EmailIcon text={data.email} />
                                            }
                                            {creators[0] != undefined && creators[0] != null &&
                                                <OwnerIcon text={`${creators[0].firstName} ${creators[0].lastName}`} />
                                            }
                                        </div>
                                    </Card.Body>
                                </Card>
                                <Card>
                                    <Card.Header>
                                        <h4>Location</h4>
                                    </Card.Header>
                                    <Card.Body>
                                        <iframe
                                            src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2670.130191453018!2d7.8458112759482725!3d47.99187106129442!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x47911c9f0c3b3f45%3A0x476333bdfce6ca5d!2sFriedrichsbau%20Lichtspiele!5e0!3m2!1sde!2sde!4v1723219250978!5m2!1sde!2sde"
                                            allowFullScreen=""
                                            width="290"
                                            style={{border:0}}
                                            loading="lazy"
                                            referrerPolicy="no-referrer-when-downgrade" />
                                    </Card.Body>
                                </Card>
                                <Card>
                                    <Card.Header>
                                        <h4>Similar events</h4>
                                    </Card.Header>
                                    <Card.Body>
                                        <Card>
                                            <div className="event-images">
                                                <Link to="#">
                                                    <img src={img53} className="img-fluid" alt="Responsive "/>
                                                </Link>
                                            </div>
                                            <Card.Body>
                                                <div className="d-flex">
                                                    <div className="date-of-event">
                                                        <span>Jan</span>
                                                        <h5>26</h5>
                                                    </div>
                                                    <div className="events-detail ms-3">
                                                        <h5><Link to="/dashboards/app/event-detail">Republic Day</Link></h5>
                                                        <p>Lorem Ipsum is simply dummy text</p>
                                                        <div className="event-member">
                                                            <div className="iq-media-group">
                                                                <Link to="#" className="iq-media">
                                                                    <img className="img-fluid avatar-40 rounded-circle" src={user05} alt=""/>
                                                                </Link>
                                                                <Link to="#" className="iq-media">
                                                                    <img className="img-fluid avatar-40 rounded-circle" src={user06} alt=""/>
                                                                </Link>
                                                                <Link to="#" className="iq-media">
                                                                    <img className="img-fluid avatar-40 rounded-circle" src={user07} alt=""/>
                                                                </Link>
                                                                <Link to="#" className="iq-media">
                                                                    <img className="img-fluid avatar-40 rounded-circle" src={user08} alt=""/>
                                                                </Link>
                                                                <Link to="#" className="iq-media">
                                                                    <img className="img-fluid avatar-40 rounded-circle" src={user09} alt=""/>
                                                                </Link>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </Card.Body>
                                        </Card>
                                        <Card>
                                            <div className="event-images">
                                                <Link to="#">
                                                    <img src={img53} className="img-fluid" alt="Responsive "/>
                                                </Link>
                                            </div>
                                            <Card.Body>
                                                <div className="d-flex">
                                                    <div className="date-of-event">
                                                        <span>Jan</span>
                                                        <h5>26</h5>
                                                    </div>
                                                    <div className="events-detail ms-3">
                                                        <h5><Link to="/dashboards/app/event-detail">Republic Day</Link></h5>
                                                        <p>Lorem Ipsum is simply dummy text</p>
                                                        <div className="event-member">
                                                            <div className="iq-media-group">
                                                                <Link to="#" className="iq-media">
                                                                    <img className="img-fluid avatar-40 rounded-circle" src={user05} alt=""/>
                                                                </Link>
                                                                <Link to="#" className="iq-media">
                                                                    <img className="img-fluid avatar-40 rounded-circle" src={user06} alt=""/>
                                                                </Link>
                                                                <Link to="#" className="iq-media">
                                                                    <img className="img-fluid avatar-40 rounded-circle" src={user07} alt=""/>
                                                                </Link>
                                                                <Link to="#" className="iq-media">
                                                                    <img className="img-fluid avatar-40 rounded-circle" src={user08} alt=""/>
                                                                </Link>
                                                                <Link to="#" className="iq-media">
                                                                    <img className="img-fluid avatar-40 rounded-circle" src={user09} alt=""/>
                                                                </Link>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </Card.Body>
                                        </Card>
                                    </Card.Body>
                                </Card>
                                <Card>
                                    <Card.Header className="d-flex align-items-center justify-content-between">
                                        <div className="header-title">
                                            <h4 className="card-title">Photos</h4>
                                        </div>
                                        <Link to="#">See all photos</Link>
                                    </Card.Header>
                                    <Card.Body>
                                        <div className="d-grid gap-2 grid-cols-3">
                                            <Link onClick={() => imageOnSlide(1)} to="#"><img loading="lazy" src={g1} alt="gallary" className="img-fluid" /></Link>
                                            <Link onClick={() => imageOnSlide(2)} to="#"><img loading="lazy" src={g2} alt="gallary" className="img-fluid" /></Link>
                                            <Link onClick={() => imageOnSlide(3)} to="#"><img loading="lazy" src={g3} alt="gallary" className="img-fluid" /></Link>
                                            <Link onClick={() => imageOnSlide(4)} to="#"><img loading="lazy" src={g4} alt="gallary" className="img-fluid" /></Link>
                                            <Link onClick={() => imageOnSlide(5)} to="#"><img loading="lazy" src={g5} alt="gallary" className="img-fluid" /></Link>
                                            <Link onClick={() => imageOnSlide(6)} to="#"><img loading="lazy" src={g6} alt="gallary" className="img-fluid" /></Link>
                                            <Link onClick={() => imageOnSlide(7)} to="#"><img loading="lazy" src={g7} alt="gallary" className="img-fluid" /></Link>
                                            <Link onClick={() => imageOnSlide(8)} to="#"><img loading="lazy" src={g8} alt="gallary" className="img-fluid" /></Link>
                                            <Link onClick={() => imageOnSlide(9)} to="#"><img loading="lazy" src={g9} alt="gallary" className="img-fluid" /></Link>
                                        </div>
                                    </Card.Body>
                                </Card>
                                <Card>
                                    <div className="card-header d-flex align-items-center justify-content-between">
                                        <div className="header-title">
                                            <h4 className="card-title">Videos</h4>
                                        </div>
                                        <Link to="#">See all videos</Link>
                                    </div>
                                    <Card.Body>
                                        <div className="d-grid gap-2">
                                            <Link to="#" className="ratio ">
                                                <iframe title="myFrame" className="rounded embed-responsive-item" src="https://www.youtube.com/embed/zpOULjyy-n8?rel=0" allowFullScreen></iframe>
                                            </Link>
                                            <Link to="#" className="ratio">
                                                <iframe title="myFrame" className="rounded embed-responsive-item" src="https://www.youtube.com/embed/zpOULjyy-n8?rel=0" allowFullScreen></iframe>
                                            </Link>
                                        </div>
                                    </Card.Body>
                                </Card>
                            </Col>
                            <Col lg="8">
                                <Card id="post-modal-data" >
                                    <div className="card-header d-flex justify-content-between">
                                        <div className="header-title">
                                            <h4 className="card-title">Create Post</h4>
                                        </div>
                                    </div>
                                    <Card.Body>
                                        <div className="d-flex align-items-center">
                                            <div className="user-img">
                                                <img loading="lazy" src={user1} alt="userimg" className="avatar-60 rounded-circle"/>
                                            </div>
                                            <form className="post-text ms-3 w-100 " onClick={handleShow}>
                                                <input type="text" className="form-control rounded" placeholder="Write something here..." style={{border:"none"}}/>
                                            </form>
                                        </div>
                                        <hr/>
                                        <ul className=" post-opt-block d-flex list-inline m-0 p-0 flex-wrap">
                                            <li className="bg-soft-primary rounded p-2 pointer d-flex align-items-center me-3 mb-md-0 mb-2"><img loading="lazy" src={small07} alt="icon" className="img-fluid me-2"/> Photo/Video</li>
                                            <li className="bg-soft-primary rounded p-2 pointer d-flex align-items-center me-3 mb-md-0 mb-2"><img loading="lazy" src={small08} alt="icon" className="img-fluid me-2"/> Tag Friend</li>
                                            <li className="bg-soft-primary rounded p-2 pointer d-flex align-items-center me-3"><img loading="lazy" src={small09} alt="icon" className="img-fluid me-2"/> Feeling/Activity</li>
                                            <li className="bg-soft-primary rounded p-2 pointer text-center">
                                                <div className="card-header-toolbar d-flex align-items-center">
                                                    <Dropdown>
                                                        <Dropdown.Toggle as={CustomToggle}  id="post-option"  >
                                            <span className="material-symbols-outlined">
                                            more_horiz
                                            </span>
                                                        </Dropdown.Toggle>
                                                        <Dropdown.Menu className=" dropdown-menu-right" aria-labelledby="post-option" >
                                                            <Dropdown.Item onClick={handleShow}  href="#" >Check in</Dropdown.Item>
                                                            <Dropdown.Item onClick={handleShow}  href="#" >Live Video</Dropdown.Item>
                                                            <Dropdown.Item onClick={handleShow}  href="#" >Gif</Dropdown.Item>
                                                            <Dropdown.Item onClick={handleShow}  href="#" >Watch Party</Dropdown.Item>
                                                            <Dropdown.Item onClick={handleShow}  href="#" >Play with Friend</Dropdown.Item>
                                                        </Dropdown.Menu>
                                                    </Dropdown>
                                                </div>
                                            </li>
                                        </ul>
                                    </Card.Body>
                                    <Modal show={show} onHide={handleClose} size="lg">
                                        <Modal.Header className="d-flex justify-content-between">
                                            <h5 className="modal-title" id="post-modalLabel">Create Post</h5>
                                            <button type="button" className="btn btn-secondary lh-1"  onClick={handleClose} ><span className="material-symbols-outlined">close</span></button>
                                        </Modal.Header>
                                        <Modal.Body>
                                            <div className="d-flex align-items-center">
                                                <div className="user-img">
                                                    <img loading="lazy" src={user9} alt="userimg" className="avatar-60 rounded-circle img-fluid"/>
                                                </div>
                                                <form className="post-text ms-3 w-100" action="">
                                                    <input type="text" className="form-control rounded" placeholder="Write something here..." style={{border: "none"}}/>
                                                </form>
                                            </div>
                                            <hr/>
                                            <ul className="d-flex flex-wrap align-items-center list-inline m-0 p-0">
                                                <li className="col-md-6 mb-3">
                                                    <div className="bg-soft-primary rounded p-2 pointer me-3"><Link to="#"></Link><img loading="lazy" src={small1} alt="icon" className="img-fluid"/> Photo/Video</div>
                                                </li>
                                                <li className="col-md-6 mb-3">
                                                    <div className="bg-soft-primary rounded p-2 pointer me-3"><Link to="#"></Link><img loading="lazy" src={small2} alt="icon" className="img-fluid"/> Tag Friend</div>
                                                </li>
                                                <li className="col-md-6 mb-3">
                                                    <div className="bg-soft-primary rounded p-2 pointer me-3"><Link to="#"></Link><img loading="lazy" src={small3} alt="icon" className="img-fluid"/> Feeling/Activity</div>
                                                </li>
                                                <li className="col-md-6 mb-3">
                                                    <div className="bg-soft-primary rounded p-2 pointer me-3"><Link to="#"></Link><img loading="lazy" src={small4} alt="icon" className="img-fluid"/> Check in</div>
                                                </li>
                                                <li className="col-md-6 mb-3">
                                                    <div className="bg-soft-primary rounded p-2 pointer me-3"><Link to="#"></Link><img loading="lazy" src={small5} alt="icon" className="img-fluid"/> Live Video</div>
                                                </li>
                                                <li className="col-md-6 mb-3">
                                                    <div className="bg-soft-primary rounded p-2 pointer me-3"><Link to="#"></Link><img loading="lazy" src={small6} alt="icon" className="img-fluid"/> Gif</div>
                                                </li>
                                                <li className="col-md-6 mb-3">
                                                    <div className="bg-soft-primary rounded p-2 pointer me-3"><Link to="#"></Link><img loading="lazy" src={small7} alt="icon" className="img-fluid"/> Watch Party</div>
                                                </li>
                                                <li className="col-md-6 mb-3">
                                                    <div className="bg-soft-primary rounded p-2 pointer me-3"><Link to="#"></Link><img loading="lazy" src={small8} alt="icon" className="img-fluid"/> Play with Friends</div>
                                                </li>
                                            </ul>
                                            <hr/>
                                            <div className="other-option">
                                                <div className="d-flex align-items-center justify-content-between">
                                                    <div className="d-flex align-items-center">
                                                        <div className="user-img me-3">
                                                            <img loading="lazy" src={user9} alt="userimg" className="avatar-60 rounded-circle img-fluid"/>
                                                        </div>
                                                        <h6>Your Story</h6>
                                                    </div>
                                                    <div className="card-post-toolbar">
                                                        <Dropdown>
                                                            <Dropdown.Toggle className="dropdown-toggle" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" role="button">
                                                                <span className="btn btn-primary">Friend</span>
                                                            </Dropdown.Toggle>
                                                            <Dropdown.Menu clemassName="dropdown-menu m-0 p-0">
                                                                <Dropdown.Item className="dropdown-item p-3" href="#">
                                                                    <div className="d-flex align-items-top">
                                                                        <i className="ri-save-line h4"></i>
                                                                        <div className="data ms-2">
                                                                            <h6>Public</h6>
                                                                            <p className="mb-0">Anyone on or off Facebook</p>
                                                                        </div>
                                                                    </div>
                                                                </Dropdown.Item>
                                                                <Dropdown.Item className="dropdown-item p-3" href="#">
                                                                    <div className="d-flex align-items-top">
                                                                        <i className="ri-close-circle-line h4"></i>
                                                                        <div className="data ms-2">
                                                                            <h6>Friends</h6>
                                                                            <p className="mb-0">Your Friend on facebook</p>
                                                                        </div>
                                                                    </div>
                                                                </Dropdown.Item>
                                                                <Dropdown.Item className="dropdown-item p-3" href="#">
                                                                    <div className="d-flex align-items-top">
                                                                        <i className="ri-user-unfollow-line h4"></i>
                                                                        <div className="data ms-2">
                                                                            <h6>Friends except</h6>
                                                                            <p className="mb-0">Don't show to some friends</p>
                                                                        </div>
                                                                    </div>
                                                                </Dropdown.Item>
                                                                <Dropdown.Item className="dropdown-item p-3" href="#">
                                                                    <div className="d-flex align-items-top">
                                                                        <i className="ri-notification-line h4"></i>
                                                                        <div className="data ms-2">
                                                                            <h6>Only Me</h6>
                                                                            <p className="mb-0">Only me</p>
                                                                        </div>
                                                                    </div>
                                                                </Dropdown.Item>
                                                            </Dropdown.Menu>
                                                        </Dropdown>
                                                    </div>
                                                </div>
                                            </div>
                                            <Button variant="primary" className="d-block w-100 mt-3">Post</Button>
                                        </Modal.Body>
                                    </Modal>
                                </Card>
                                <div className="card-2">
                                    <Card.Body>
                                        <ul className="post-comments p-0 m-0">
                                            <li className="">
                                                <div className="d-flex justify-content-between">
                                                    <div className="user-img">
                                                        <img loading="lazy" src={imgp25} alt="userimg" className="avatar-60 me-3 rounded-circle img-fluid"/>
                                                    </div>
                                                    <div className="w-100">
                                                        <form className="post-text mt-2">
                                                            <input type="text" className="form-control rounded" placeholder="Write something here..." style={{border: "none"}}/>
                                                        </form>
                                                        <hr/>
                                                        <div className="d-flex justify-content-between">
                                                            <div className="">
                                                                Add:
                                                            </div>
                                                            <div className="">
                                                                <svg fill="none" width="18px" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"></path></svg>
                                                            </div>
                                                            <div className="">
                                                                <svg fill="none" width="18px" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15 10l4.553-2.276A1 1 0 0121 8.618v6.764a1 1 0 01-1.447.894L15 14M5 18h8a2 2 0 002-2V8a2 2 0 00-2-2H5a2 2 0 00-2 2v8a2 2 0 002 2z"></path></svg>
                                                            </div>
                                                            <div className="">
                                                                <svg fill="none" width="18px" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M5 19a2 2 0 01-2-2V7a2 2 0 012-2h4l2 2h4a2 2 0 012 2v1M5 19h14a2 2 0 002-2v-5a2 2 0 00-2-2H9a2 2 0 00-2 2v5a2 2 0 01-2 2z"></path></svg>
                                                            </div>
                                                            <button className="btn btn-primary btn-sm">Publish</button>
                                                        </div>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </Card.Body>
                                </div>
                                <Card>
                                    <Card.Body>
                                        <ul className="post-comments p-0 m-0">
                                            <li className="mb-2">
                                                <div className="d-flex justify-content-between">
                                                    <div className="user-img">
                                                        <img loading="lazy" src={imgp26} alt="userimg" className="avatar-60 me-3 rounded-circle img-fluid"/>
                                                    </div>
                                                    <div className="w-100 text-margin">
                                                        <h5>Mathilda Gvarliana</h5>
                                                        <small className=" d-flex align-items-center "> <i className="material-symbols-outlined md-14 me-1">
                                                            schedule
                                                        </i> March 14, 23:00</small>
                                                        <p>Hi, I am flying to Los Angeles to attend #VSFS2020 castings. I hope it will happen and my dream comes true. Wish me luck. </p>
                                                        <hr/>
                                                        <div className="d-flex justify-content-between align-items-center flex-wrap">
                                                            <div className="d-flex justify-content-between align-items-center">
                                                                <div className="d-flex align-items-center me-3">
                                                            <span className="material-symbols-outlined md-18">
                                                            favorite_border
                                                            </span>
                                                                    <span className="card-text-1 ms-1">Love it</span>
                                                                </div>
                                                                <div className="d-flex align-items-center me-3">
                                                            <span className="material-symbols-outlined md-18">
                                                            comment
                                                            </span>
                                                                    <span className="card-text-1 ms-1">Comment</span>
                                                                </div>
                                                                <div className="d-flex align-items-center">
                                                            <span className="material-symbols-outlined md-18">
                                                            share
                                                            </span>
                                                                    <span className="card-text-1 ms-1">Share</span>
                                                                </div>
                                                            </div>
                                                            <span className="card-text-2">
                                                            5.2k people love it
                                                    </span>

                                                            <div className="d-flex justify-content-between align-items-center">
                                                                <span className="card-text-1 me-1">5.2k people love it</span>
                                                                <div className="iq-media-group ms-2">
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp27} alt=""/>
                                                                    </Link>
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp28} alt=""/>
                                                                    </Link>
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp29} alt=""/>
                                                                    </Link>
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp30} alt=""/>
                                                                    </Link>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <form className="d-flex align-items-center mt-3" action="#">
                                                            <input type="text" className="form-control rounded" placeholder="Write your comment"/>
                                                            <div className="comment-attagement d-flex align-items-center me-4">
                                                    <span className="material-symbols-outlined md-18 me-1">
                                                        comment
                                                    </span>
                                                                <h6 className="card-text-1">Comment</h6>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </Card.Body>
                                </Card>
                                <Card>
                                    <Card.Body>
                                        <ul className="post-comments p-0 m-0">
                                            <li className="mb-2">
                                                <div className="d-flex justify-content-between">
                                                    <div className="user-img">
                                                        <img loading="lazy" src={imgp31} alt="userimg" className="avatar-60 me-3 rounded-circle img-fluid"/>
                                                    </div>
                                                    <div className="w-100 text-margin">
                                                        <h5>Mathilda Gvarliana</h5>
                                                        <small className=" d-flex align-items-center "> <i className="material-symbols-outlined md-14 me-1">
                                                            schedule
                                                        </i> March 14, 23:00</small>
                                                        <div className="mt-2 mb-2 ratio">
                                                            <iframe title="myFrame" className="rounded embed-responsive-item" src="https://www.youtube.com/embed/zpOULjyy-n8?rel=0" allowFullScreen></iframe>
                                                        </div>
                                                        <p>Dolce Spring Summer 2020 - Women's Show</p>
                                                        <hr/>
                                                        <div className="d-flex justify-content-between align-items-center flex-wrap">
                                                            <div className="d-flex justify-content-between align-items-center">
                                                                <div className="d-flex align-items-center me-3">
                                                            <span className="material-symbols-outlined md-18">
                                                            favorite_border
                                                            </span>
                                                                    <span className="card-text-1 ms-1">Love it</span>
                                                                </div>
                                                                <div className="d-flex align-items-center me-3">
                                                            <span className="material-symbols-outlined md-18">
                                                            comment
                                                            </span>
                                                                    <span className="card-text-1 ms-1">Comment</span>
                                                                </div>
                                                                <div className="d-flex align-items-center">
                                                            <span className="material-symbols-outlined md-18">
                                                            share
                                                            </span>
                                                                    <span className="card-text-1 ms-1">Share</span>
                                                                </div>
                                                            </div>
                                                            <span className="card-text-2">
                                                            5.2k people love it
                                                    </span>

                                                            <div className="d-flex justify-content-between align-items-center">
                                                                <span className="card-text-1 me-1">5.2k people love it</span>
                                                                <div className="iq-media-group ms-2">
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp27} alt=""/>
                                                                    </Link>
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp28} alt=""/>
                                                                    </Link>
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp29} alt=""/>
                                                                    </Link>
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp30} alt=""/>
                                                                    </Link>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <form className="d-flex align-items-center mt-3" action="#">
                                                            <input type="text" className="form-control rounded" placeholder="Write your comment"/>
                                                            <div className="comment-attagement d-flex align-items-center me-4">
                                                    <span className="material-symbols-outlined md-18 me-1">
                                                        comment
                                                    </span>
                                                                <h6 className="card-text-1">Comment</h6>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </Card.Body>
                                </Card>
                                <Card>
                                    <Card.Body>
                                        <ul className="post-comments p-0 m-0">
                                            <li className="mb-2">
                                                <div className="d-flex justify-content-between">
                                                    <div className="user-img">
                                                        <img loading="lazy" src={imgp36} alt="userimg" className="avatar-60 me-3 rounded-circle img-fluid"/>
                                                    </div>
                                                    <div className="w-100 text-margin">
                                                        <h5>Mathilda Gvarliana</h5>
                                                        <small className=" d-flex align-items-center "> <i className="material-symbols-outlined md-14 me-1">
                                                            schedule
                                                        </i> March 14, 23:00</small>
                                                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. </p>
                                                        <hr/>
                                                        <div className="d-flex justify-content-between align-items-center flex-wrap">
                                                            <div className="d-flex justify-content-between align-items-center">
                                                                <div className="d-flex align-items-center me-3">
                                                            <span className="material-symbols-outlined md-18">
                                                            favorite_border
                                                            </span>
                                                                    <span className="card-text-1 ms-1">Love it</span>
                                                                </div>
                                                                <div className="d-flex align-items-center me-3">
                                                            <span className="material-symbols-outlined md-18">
                                                            comment
                                                            </span>
                                                                    <span className="card-text-1 ms-1">Comment</span>
                                                                </div>
                                                                <div className="d-flex align-items-center">
                                                            <span className="material-symbols-outlined md-18">
                                                            share
                                                            </span>
                                                                    <span className="card-text-1 ms-1">Share</span>
                                                                </div>
                                                            </div>
                                                            <span className="card-text-2">
                                                            5.2k people love it
                                                    </span>

                                                            <div className="d-flex justify-content-between align-items-center">
                                                                <span className="card-text-1 me-1">5.2k people love it</span>
                                                                <div className="iq-media-group ms-2">
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp27} alt=""/>
                                                                    </Link>
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp28} alt=""/>
                                                                    </Link>
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp29} alt=""/>
                                                                    </Link>
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp30} alt=""/>
                                                                    </Link>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div className="text-center mt-4">
                                                            <p>Hide 203 comments</p>
                                                        </div>
                                                        <ul className="post-comments p-2  card rounded">
                                                            <li className="mb-2">
                                                                <div className="d-flex justify-content-between">
                                                                    <div className="user-img">
                                                                        <img src={imgp45} alt="userimg" className="avatar-60 me-3 rounded-circle img-fluid" />
                                                                    </div>
                                                                    <div className="w-100 text-margin">
                                                                        <div className="">
                                                                            <h5 className="mb-0 d-inline-block me-1">Emma Labelle</h5>
                                                                            <h6 className=" mb-0 d-inline-block">2 weeks ago</h6>
                                                                        </div>
                                                                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. </p>
                                                                        <div className="d-flex justify-content-between align-items-center flex-wrap">
                                                                            <div className="d-flex justify-content-between align-items-center">
                                                                                <div className="d-flex align-items-center me-3">
                                                                    <span className="material-symbols-outlined md-18">
                                                                    favorite_border
                                                                    </span>
                                                                                    <span className="card-text-1 ms-1">Love it</span>
                                                                                </div>
                                                                                <div className="d-flex align-items-center me-3">
                                                                    <span className="material-symbols-outlined md-18">
                                                                    comment
                                                                    </span>
                                                                                    <span className="card-text-1 ms-1">Comment</span>
                                                                                </div>
                                                                                <div className="d-flex align-items-center">
                                                                    <span className="material-symbols-outlined md-18">
                                                                    share
                                                                    </span>
                                                                                    <span className="card-text-1 ms-1">Share</span>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                        </ul>
                                                        <ul className="post-comments p-2 m-0 bg-soft-light text-dark">
                                                            <li className="mb-2">
                                                                <div className="d-flex justify-content-between">
                                                                    <div className="user-img">
                                                                        <img loading="lazy" src={imgp42} alt="userimg" className="avatar-60 me-3 rounded-circle img-fluid"/>
                                                                    </div>
                                                                    <div className="w-100 text-margin">
                                                                        <div className="">
                                                                            <h5 className="mb-0 d-inline-block me-1">Emma Labelle</h5>
                                                                            <span className=" mb-0 d-inline-block">2 weeks ago</span>
                                                                        </div>
                                                                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s</p>
                                                                        <div className="d-flex justify-content-between align-items-center flex-wrap">
                                                                            <div className="d-flex justify-content-around align-items-center">
                                                                                <div className="d-flex align-items-center me-3">
                                                                    <span className="material-symbols-outlined md-18">
                                                                    favorite_border
                                                                    </span>
                                                                                    <span className="card-text-1 ms-1">Love it</span>
                                                                                </div>
                                                                                <div className="d-flex align-items-center me-3">
                                                                    <span className="material-symbols-outlined md-18">
                                                                    comment
                                                                    </span>
                                                                                    <span className="card-text-1 ms-1">Comment</span>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                        <ul className="post-comments p-0 mt-4 text-dark">
                                                                            <li className="mb-2">
                                                                                <div className="d-flex justify-content-between">
                                                                                    <div className="user-img">
                                                                                        <img loading="lazy" src={imgp43} alt="userimg" className="avatar-60 me-3 rounded-circle img-fluid avatar-1"/>
                                                                                    </div>
                                                                                    <div className="w-100 text-margin">
                                                                                        <div className="">
                                                                                            <h5 className="mb-0 d-inline-block me-1">Emma Labelle</h5>
                                                                                            <h6 className=" mb-0 d-inline-block">2 weeks ago</h6>
                                                                                        </div>
                                                                                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s.</p>
                                                                                        <div className="d-flex justify-content-between align-items-center flex-wrap">
                                                                                            <div className="d-flex justify-content-around align-items-center">
                                                                                                <div className="d-flex align-items-center me-3">
                                                                                        <span className="material-symbols-outlined md-18">
                                                                                        favorite_border
                                                                                        </span>
                                                                                                    <span className="card-text-1 ms-1">Love it</span>
                                                                                                </div>
                                                                                                <div className="d-flex align-items-center me-3">
                                                                                        <span className="material-symbols-outlined md-18">
                                                                                        comment
                                                                                        </span>
                                                                                                    <span className="card-text-1 ms-1">Comment</span>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </li>
                                                                        </ul>
                                                                        <ul className="post-comments p-0 mt-4 text-dark">
                                                                            <li className="mb-2">
                                                                                <div className="d-flex justify-content-between">
                                                                                    <div className="user-img">
                                                                                        <img loading="lazy" src={imgp44} alt="userimg" className="avatar-60 me-3 rounded-circle img-fluid avatar-1"/>
                                                                                    </div>
                                                                                    <div className="w-100 text-margin">
                                                                                        <div className="">
                                                                                            <h5 className="mb-0 d-inline-block me-1">Emma Labelle</h5>
                                                                                            <h6 className=" mb-0 d-inline-block">2 weeks ago</h6>
                                                                                        </div>
                                                                                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry. </p>
                                                                                        <div className="d-flex justify-content-between align-items-center flex-wrap">
                                                                                            <div className="d-flex justify-content-around align-items-center">
                                                                                                <div className="d-flex align-items-center me-3">
                                                                                        <span className="material-symbols-outlined md-18">
                                                                                        favorite_border
                                                                                        </span>
                                                                                                    <span className="card-text-1 ms-1">Love it</span>
                                                                                                </div>
                                                                                                <div className="d-flex align-items-center me-3">
                                                                                        <span className="material-symbols-outlined md-18">
                                                                                        comment
                                                                                        </span>
                                                                                                    <span className="card-text-1 ms-1">Comment</span>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                        <form className="d-flex align-items-center mt-3" action="#">
                                                                                            <input type="text" className="form-control rounded" placeholder="Write your comment"/>
                                                                                            <div className="comment-attagement d-flex align-items-center me-4">
                                                                                    <span className="material-symbols-outlined md-18 me-1">
                                                                                        comment
                                                                                    </span>
                                                                                                <h6 className="card-text-1 me-2" >Comment</h6>
                                                                                            </div>
                                                                                        </form>
                                                                                    </div>
                                                                                </div>
                                                                            </li>
                                                                        </ul>
                                                                    </div>

                                                                </div>
                                                            </li>
                                                        </ul>
                                                        <ul className="post-comments p-2 mt-4 text-dark">
                                                            <li className="mb-2">
                                                                <div className="d-flex justify-content-between">
                                                                    <div className="user-img">
                                                                        <img loading="lazy" src={imgp45} alt="userimg" className="avatar-60 me-3 rounded-circle img-fluid "/>
                                                                    </div>
                                                                    <div className="w-100 text-margin">
                                                                        <div className="">
                                                                            <h5 className="mb-0 d-inline-block me-1">Emma Labelle</h5>
                                                                            <span className=" mb-0 d-inline-block">2 weeks ago</span>
                                                                        </div>
                                                                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry.</p>
                                                                        <div className="d-flex justify-content-between align-items-center flex-wrap">
                                                                            <div className="d-flex justify-content-around align-items-center">
                                                                                <div className="d-flex align-items-center me-3">
                                                                        <span className="material-symbols-outlined md-18">
                                                                        favorite_border
                                                                        </span>
                                                                                    <span className="card-text-1 ms-1">Love it</span>
                                                                                </div>
                                                                                <div className="d-flex align-items-center me-3">
                                                                        <span className="material-symbols-outlined md-18">
                                                                        comment
                                                                        </span>
                                                                                    <span className="card-text-1 ms-1">Comment</span>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </li>
                                                            <form className="d-flex align-items-center mt-3" action="#">
                                                                <input type="text" className="form-control rounded" placeholder="Write your comment"/>
                                                                <div className="comment-attagement d-flex align-items-center me-4">
                                                        <span className="material-symbols-outlined md-18 me-1">
                                                            comment
                                                        </span>
                                                                    <h6 className="card-text-1 me-2" >Comment</h6>
                                                                </div>
                                                            </form>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </Card.Body>
                                </Card>
                                <Card>
                                    <Card.Body>
                                        <ul className="post-comments p-0 m-0">
                                            <li className="mb-2">
                                                <div className="d-flex justify-content-between">
                                                    <div className="user-img">
                                                        <img loading="lazy" src={imgp46} alt="userimg" className="avatar-60 me-3 rounded-circle img-fluid"/>
                                                    </div>
                                                    <div className="w-100 text-margin">
                                                        <h4>Mathilda Gvarliana</h4>
                                                        <p className="mb-4">June 30, 12: 26</p>
                                                        <div className="d-grid gap-2 grid-cols-2 mb-2">
                                                            <Link to="#">
                                                                <img loading="lazy" src={imgp47} className="img-fluid bg-soft-info rounded  image-size" alt="profile-img"/>
                                                            </Link>
                                                            <Link to="#">
                                                                <img loading="lazy" src={imgp48} className="img-fluid bg-soft-info rounded  image-size" alt="profile-img"/>
                                                            </Link>
                                                            <Link to="#">
                                                                <img loading="lazy" src={imgp49} className="img-fluid bg-soft-info rounded  image-size" alt="profile-img"/>
                                                            </Link>
                                                            <Link to="#">
                                                                <img loading="lazy" src={imgp50} className="img-fluid bg-soft-info rounded  image-size" alt="profile-img"/>
                                                            </Link>
                                                        </div>
                                                        <span className="">Photoshoot for Buyers Magazine - 2019</span>
                                                        <hr/>
                                                        <div className="d-flex justify-content-between align-items-center flex-wrap">
                                                            <div className="d-flex justify-content-between align-items-center">
                                                                <div className="d-flex justify-content-between align-items-center">
                                                                    <div className="d-flex align-items-center me-3">
                                                            <span className="material-symbols-outlined md-18">
                                                            favorite_border
                                                            </span>
                                                                        <span className="card-text-1 ms-1">Love it</span>
                                                                    </div>
                                                                    <div className="d-flex align-items-center me-3">
                                                            <span className="material-symbols-outlined md-18">
                                                            comment
                                                            </span>
                                                                        <span className="card-text-1 ms-1">Comment</span>
                                                                    </div>
                                                                    <div className="d-flex align-items-center">
                                                            <span className="material-symbols-outlined md-18">
                                                            share
                                                            </span>
                                                                        <span className="card-text-1 ms-1">Share</span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div className="d-flex justify-content-between align-items-center">
                                                                <span className="card-text-1 me-1">5.2k people love it</span>
                                                                <div className="iq-media-group ms-2">
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp27} alt=""/>
                                                                    </Link>
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp28} alt=""/>
                                                                    </Link>
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp29} alt=""/>
                                                                    </Link>
                                                                    <Link to="#" className="iq-media ">
                                                                        <img loading="lazy" className="img-fluid avatar-30 rounded-circle" src={imgp30} alt=""/>
                                                                    </Link>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <form className="d-flex align-items-center mt-3" action="#">
                                                            <input type="text" className="form-control rounded" placeholder="Write your comment"/>
                                                            <div className="comment-attagement d-flex align-items-center me-4">
                                                    <span className="material-symbols-outlined md-18 me-1">
                                                        comment
                                                    </span>
                                                                <h6 className="card-text-1 me-2" >Comment</h6>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </Card.Body>
                                </Card>
                            </Col>
                        </Row>
                    </Container>
                </div>
            </div>
        </>
    )
}
export default DanceEventDetail