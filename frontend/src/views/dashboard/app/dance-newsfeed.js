import React,{useState, useEffect} from 'react'
import {Link} from 'react-router-dom'
import {
    Container,
    Row,
    Col,
    Dropdown,
    OverlayTrigger, Tooltip
} from 'react-bootstrap'
import Card from '../../../components/Card'
import CustomToggle from '../../../components/dropdowns'
import ReactFsLightbox from 'fslightbox-react';

import imgpp3 from '../../../assets/images/page-img/07.jpg'
import imgpp4 from '../../../assets/images/page-img/06.jpg'
import imgpp5 from '../../../assets/images/page-img/g1.jpg'
import imgpp6 from '../../../assets/images/page-img/g2.jpg'
import imgpp7 from '../../../assets/images/page-img/g3.jpg'
import imgpp8 from '../../../assets/images/page-img/g4.jpg'
import imgpp9 from '../../../assets/images/page-img/g5.jpg'
import imgpp10 from '../../../assets/images/page-img/g6.jpg'
import imgpp11 from '../../../assets/images/page-img/g7.jpg'
import imgpp12 from '../../../assets/images/page-img/g8.jpg'
import imgpp13 from '../../../assets/images/page-img/g9.jpg'

// sweet-alert
import Swal from 'sweetalert2'
import user01 from "../../../assets/images/user/01.jpg";
import p2 from "../../../assets/images/page-img/p2.jpg";
import p1 from "../../../assets/images/page-img/p1.jpg";
import p3 from "../../../assets/images/page-img/p3.jpg";
import icon1 from "../../../assets/images/icon/01.png";
import icon2 from "../../../assets/images/icon/02.png";
import icon3 from "../../../assets/images/icon/03.png";
import icon4 from "../../../assets/images/icon/04.png";
import icon5 from "../../../assets/images/icon/05.png";
import icon6 from "../../../assets/images/icon/06.png";
import icon7 from "../../../assets/images/icon/07.png";
import ShareOffcanvas from "../../../components/share-offcanvas";
import user2 from "../../../assets/images/user/02.jpg";
import user3 from "../../../assets/images/user/03.jpg";
import loader from "../../../assets/images/page-img/page-load-loader.gif";
import user4 from "../../../assets/images/user/04.jpg";
import s4 from "../../../assets/images/page-img/s4.jpg";
import s5 from "../../../assets/images/page-img/s5.jpg";
import EmailAppDetail from "../../../components/email-app-detail";
import DanceNewsfeedCard from "../../../components/dance-newsfeed-card";
import {useKeycloak} from "@react-keycloak/web";

// Fslightbox plugin
const FsLightbox = ReactFsLightbox.default ? ReactFsLightbox.default : ReactFsLightbox;

const DanceNewsfeed=()=>{

    const [ids, setIds] = useState([]);

    const { keycloak, initialized } = useKeycloak();

    const questionAlert = () => {
        const swalWithBootstrapButtons = Swal.mixin({
            customClass: {
                cancelButton: 'btn btn-outline-primary btn-lg ms-2',
                confirmButton: 'btn btn-primary btn-lg',


            },
            buttonsStyling: false
        })

        swalWithBootstrapButtons.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            cancelButtonText: 'cancel',
            confirmButtonText: 'Yes, delete it!',

            reverseButtons: false,
            showClass: {
                popup: 'animate__animated animate__zoomIn'
            },
            hideClass: {
                popup: 'animate__animated animate__zoomOut'
            }

        }).then((result) => {
            if (result.isConfirmed) {
                swalWithBootstrapButtons.fire({
                    title: 'Deleted!',
                    text: 'Your Request has been deleted.',
                    icon: 'success',
                    showClass: {
                        popup: 'animate__animated animate__zoomIn'
                    },
                    hideClass: {
                        popup: 'animate__animated animate__zoomOut'
                    }
                })
            } else if (
                /* Read more about handling dismissals below */
                result.dismiss === Swal.DismissReason.cancel
            ) {
                swalWithBootstrapButtons.fire({
                    title: 'Your Request is safe!',
                    showClass: {
                        popup: 'animate__animated animate__zoomIn'
                    },
                    hideClass: {
                        popup: 'animate__animated animate__zoomOut'
                    }
                })
            }
        })
    }

    // fsLightbox
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

    useEffect(() => {
        if(keycloak.authenticated) {
            const fetchIds = async () => {
                try {
                    const response = await fetch('/api/v1/newsfeed-entries', {
                        headers: {
                            Authorization: `Bearer ${keycloak.token}`,
                            'Content-Type': 'application/json',
                        },
                    });
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    const body = await response.json();
                    console.log(body);
                    setIds(body);
                } catch (error) {
                    console.error('Error fetching newsfeed ids:', error);
                }
            };

            fetchIds();
        }
    }, [keycloak.authenticated]);

    return(
        <>
            <FsLightbox
                toggler={imageController.toggler}
                sources={[imgpp5,imgpp6,imgpp7,imgpp8,imgpp9,imgpp10,imgpp11,imgpp12,imgpp13]}
                slide={imageController.slide}
            />
            <div id="content-page" className="content-page">
                <Container>
                    <Row className="mt-1">
                        <Col lg="4" md="4">
                            <Card>
                                <div className="card-header d-flex justify-content-between">
                                    <div className="header-title">
                                        <h4 className="card-title">Today's Schedule</h4>
                                    </div>
                                    <div className="card-header-toolbar d-flex align-items-center">
                                        <Dropdown>
                                            <Dropdown.Toggle as={CustomToggle} id="dropdownMenuButton" data-bs-toggle="dropdown" aria-expanded="false" role="button">
                                                <i className="ri-more-fill h4"></i>
                                            </Dropdown.Toggle>
                                            <Dropdown.Menu className=" dropdown-menu-right" aria-labelledby="dropdownMenuButton">
                                                <Dropdown.Item  href="#"><i className="ri-eye-fill me-2"></i>View</Dropdown.Item>
                                                <Dropdown.Item  href="#"><i className="ri-delete-bin-6-fill me-2"></i>Delete</Dropdown.Item>
                                                <Dropdown.Item  href="#"><i className="ri-pencil-fill me-2"></i>Edit</Dropdown.Item>
                                                <Dropdown.Item  href="#"><i className="ri-printer-fill me-2"></i>Print</Dropdown.Item>
                                                <Dropdown.Item  href="#"><i className="ri-file-download-fill me-2"></i>Download</Dropdown.Item>
                                            </Dropdown.Menu>
                                        </Dropdown>
                                    </div>
                                </div>
                                <Card.Body>
                                    <ul className="media-story list-inline m-0 p-0">
                                        <li className="d-flex mb-4 align-items-center ">
                                            <img src={s4} alt="story1" className="rounded-circle img-fluid"/>
                                            <div className="stories-data ms-3">
                                                <h5>Web Workshop</h5>
                                                <p className="mb-0">1 hour ago</p>
                                            </div>
                                        </li>
                                        <li className="d-flex align-items-center">
                                            <img src={s5} alt="story2" className="rounded-circle img-fluid"/>
                                            <div className="stories-data ms-3">
                                                <h5>Fun Events and Festivals</h5>
                                                <p className="mb-0">1 hour ago</p>
                                            </div>
                                        </li>
                                    </ul>
                                </Card.Body>
                            </Card>
                            <Card>
                                <div className="card-header d-flex justify-content-between">
                                    <div className="header-title">
                                        <h4 className="card-title">Upcoming Event</h4>
                                    </div>
                                    <div className="card-header-toolbar d-flex align-items-center">
                                        <p className="m-0"><Link to="javacsript:void();"> Create </Link></p>
                                    </div>
                                </div>
                                <Card.Body>
                                    <Row>
                                        <Col sm="12">
                                            <div className="event-post position-relative">
                                                <Link to="#"><img loading="lazy" src={imgpp3} alt="gallary-img" className="img-fluid rounded"/></Link>
                                                <div className="job-icon-position">
                                                    <div className="job-icon bg-primary p-2 d-inline-block rounded-circle material-symbols-outlined text-white">
                                                        local_mall
                                                    </div>
                                                </div>
                                                <div className="card-body text-center p-2">
                                                    <h5>Started New Job at Apple</h5>
                                                    <p>January 24, 2019</p>
                                                </div>
                                            </div>
                                        </Col>
                                        <Col sm="12">
                                            <div className="event-post position-relative">
                                                <Link to="#"><img loading="lazy" src={imgpp4} alt="gallary-img" className="img-fluid rounded"/></Link>
                                                <div className="job-icon-position">
                                                    <div className="job-icon bg-primary p-2 d-inline-block rounded-circle material-symbols-outlined text-white">
                                                        local_mall
                                                    </div>
                                                </div>
                                                <div className="card-body text-center p-2">
                                                    <h5>Freelance Photographer</h5>
                                                    <p className="mb-0">January 24, 2019</p>
                                                </div>
                                            </div>
                                        </Col>
                                    </Row>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col lg="8" md="8">
                            <Col sm={12}>
                                <Card className=" card-block card-stretch card-height">
                                    <Card.Body>
                                        <div className="user-post-data">
                                            <div className="d-flex justify-content-between">
                                                <div className="me-3">
                                                    <img className="rounded-circle img-fluid" src={user01} alt=""/>
                                                </div>
                                                <div className="w-100">
                                                    <div className="d-flex justify-content-between">
                                                        <div>
                                                            <h5 className="mb-0 d-inline-block">Anna Sthesia</h5>
                                                            <span className="mb-0 ps-1 d-inline-block">Add New Post</span>
                                                            <p className="mb-0 text-primary">Just Now</p>
                                                        </div>
                                                        <div className="card-post-toolbar">
                                                            <Dropdown>
                                                                <Dropdown.Toggle variant="bg-transparent">
                                                                    <span className="material-symbols-outlined">
                                                                        more_horiz
                                                                    </span>
                                                                </Dropdown.Toggle>
                                                                <Dropdown.Menu className="dropdown-menu m-0 p-0">
                                                                    <Dropdown.Item className=" p-3" to="#">
                                                                        <div className="d-flex align-items-top">
                                                                            <div className="h4 material-symbols-outlined">
                                                                                <i className="ri-save-line"></i>
                                                                            </div>
                                                                            <div className="data ms-2">
                                                                                <h6>Save Post</h6>
                                                                                <p className="mb-0">Add this to your saved items</p>
                                                                            </div>
                                                                        </div>
                                                                    </Dropdown.Item>
                                                                    <Dropdown.Item className= "p-3" to="#">
                                                                        <div className="d-flex align-items-top">
                                                                            <i className="ri-close-circle-line h4"></i>
                                                                            <div className="data ms-2">
                                                                                <h6>Hide Post</h6>
                                                                                <p className="mb-0">See fewer posts like this.</p>
                                                                            </div>
                                                                        </div>
                                                                    </Dropdown.Item>
                                                                    <Dropdown.Item className=" p-3" to="#">
                                                                        <div className="d-flex align-items-top">
                                                                            <i className="ri-user-unfollow-line h4"></i>
                                                                            <div className="data ms-2">
                                                                                <h6>Unfollow User</h6>
                                                                                <p className="mb-0">Stop seeing posts but stay friends.</p>
                                                                            </div>
                                                                        </div>
                                                                    </Dropdown.Item>
                                                                    <Dropdown.Item className=" p-3" to="#">
                                                                        <div className="d-flex align-items-top">
                                                                            <i className="ri-notification-line h4"></i>
                                                                            <div className="data ms-2">
                                                                                <h6>Notifications</h6>
                                                                                <p className="mb-0">Turn on notifications for this post</p>
                                                                            </div>
                                                                        </div>
                                                                    </Dropdown.Item>
                                                                </Dropdown.Menu>
                                                            </Dropdown>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div className="mt-3">
                                            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi nulla dolor, ornare at commodo non, feugiat non nisi. Phasellus faucibus mollis pharetra. Proin blandit ac massa sed rhoncus</p>
                                        </div>
                                        <div className="user-post">
                                            <div className=" d-grid grid-rows-2 grid-flow-col gap-3">
                                                <div className="row-span-2 row-span-md-1">
                                                    <img src={p2} alt="post1" className="img-fluid rounded w-100"/>
                                                </div>
                                                <div className="row-span-1">
                                                    <img src={p1} alt="post2" className="img-fluid rounded w-100"/>
                                                </div>
                                                <div className="row-span-1 ">
                                                    <img src={p3} alt="post3" className="img-fluid rounded w-100"/>
                                                </div>
                                            </div>
                                        </div>
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
                                            <hr/>
                                            <ul className="post-comments list-inline p-0 m-0">
                                                <li className="mb-2">
                                                    <div className="d-flex">
                                                        <div className="user-img">
                                                            <img src={user2} alt="user1" className="avatar-35 rounded-circle img-fluid"/>
                                                        </div>
                                                        <div className="comment-data-block ms-3">
                                                            <h6>Monty Carlo</h6>
                                                            <p className="mb-0">Lorem ipsum dolor sit amet</p>
                                                            <div className="d-flex flex-wrap align-items-center comment-activity">
                                                                <Link to="#">like</Link>
                                                                <Link to="#">reply</Link>
                                                                <Link to="#">translate</Link>
                                                                <span> 5 min </span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div className="d-flex">
                                                        <div className="user-img">
                                                            <img src={user3} alt="user1" className="avatar-35 rounded-circle img-fluid"/>
                                                        </div>
                                                        <div className="comment-data-block ms-3">
                                                            <h6>Paul Molive</h6>
                                                            <p className="mb-0">Lorem ipsum dolor sit amet</p>
                                                            <div className="d-flex flex-wrap align-items-center comment-activity">
                                                                <Link to="#">like</Link>
                                                                <Link to="#">reply</Link>
                                                                <Link to="#">translate</Link>
                                                                <span> 5 min </span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </li>
                                            </ul>
                                            <form className="comment-text d-flex align-items-center mt-3" >
                                                <input type="text" className="form-control rounded" placeholder="Enter Your Comment"/>
                                                <div className="comment-attagement d-flex">
                                                    <Link to="#"><i className="ri-link me-3"></i></Link>
                                                    <Link to="#"><i className="ri-user-smile-line me-3"></i></Link>
                                                    <Link to="#"><i className="ri-camera-line me-3"></i></Link>
                                                </div>
                                            </form>
                                        </div>
                                    </Card.Body>
                                </Card>
                            </Col>
                            <Col sm={12}>
                                <Card className=" card-block card-stretch card-height">
                                    <Card.Body>
                                        <div className="card card-block card-stretch card-height">
                                            <div className="card-body">
                                                <div className="user-post-data">
                                                    <div className="d-flex justify-content-between">
                                                        <div className="me-3">
                                                            <img className="rounded-circle img-fluid" src={user4} alt=""/>
                                                        </div>
                                                        <div className="w-100">
                                                            <div className=" d-flex  justify-content-between">
                                                                <div>
                                                                    <h5 className="mb-0 d-inline-block">Ira Membrit</h5>
                                                                    <p className="mb-0 ps-1 d-inline-block">Update her Status</p>
                                                                    <p className="mb-0 text-primary">6 hour ago</p>
                                                                </div>
                                                                <div className="card-post-toolbar">
                                                                    <Dropdown>
                                                                        <Dropdown.Toggle variant="bg-transparent">
                                                                        <span className="material-symbols-outlined">
                                                                            more_horiz
                                                                        </span>
                                                                        </Dropdown.Toggle>
                                                                        <Dropdown.Menu className="dropdown-menu m-0 p-0">
                                                                            <Dropdown.Item className=" p-3" to="#">
                                                                                <div className="d-flex align-items-top">
                                                                                    <div className="h4">
                                                                                        <i className="ri-save-line"></i>
                                                                                    </div>
                                                                                    <div className="data ms-2">
                                                                                        <h6>Save Post</h6>
                                                                                        <p className="mb-0">Add this to your saved items</p>
                                                                                    </div>
                                                                                </div>
                                                                            </Dropdown.Item>
                                                                            <Dropdown.Item className= "p-3" to="#">
                                                                                <div className="d-flex align-items-top">
                                                                                    <i className="ri-close-circle-line h4"></i>
                                                                                    <div className="data ms-2">
                                                                                        <h6>Hide Post</h6>
                                                                                        <p className="mb-0">See fewer posts like this.</p>
                                                                                    </div>
                                                                                </div>
                                                                            </Dropdown.Item>
                                                                            <Dropdown.Item className=" p-3" to="#">
                                                                                <div className="d-flex align-items-top">
                                                                                    <i className="ri-user-unfollow-line h4"></i>
                                                                                    <div className="data ms-2">
                                                                                        <h6>Unfollow User</h6>
                                                                                        <p className="mb-0">Stop seeing posts but stay friends.</p>
                                                                                    </div>
                                                                                </div>
                                                                            </Dropdown.Item>
                                                                            <Dropdown.Item className=" p-3" to="#">
                                                                                <div className="d-flex align-items-top">
                                                                                    <i className="ri-notification-line h4"></i>
                                                                                    <div className="data ms-2">
                                                                                        <h6>Notifications</h6>
                                                                                        <p className="mb-0">Turn on notifications for this post</p>
                                                                                    </div>
                                                                                </div>
                                                                            </Dropdown.Item>
                                                                        </Dropdown.Menu>
                                                                    </Dropdown>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div className="mt-3">
                                                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi nulla dolor, ornare at commodo non, feugiat non nisi. Phasellus faucibus mollis pharetra. Proin blandit ac massa sed rhoncus</p>
                                                </div>
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
                                                    <hr/>
                                                    <ul className="post-comments list-inline p-0 m-0">
                                                        <li className="mb-2">
                                                            <div className="d-flex">
                                                                <div className="user-img">
                                                                    <img src={user2} alt="user1" className="avatar-35 rounded-circle img-fluid"/>
                                                                </div>
                                                                <div className="comment-data-block ms-3">
                                                                    <h6>Monty Carlo</h6>
                                                                    <p className="mb-0">Lorem ipsum dolor sit amet</p>
                                                                    <div className="d-flex flex-wrap align-items-center comment-activity">
                                                                        <Link to="#">like</Link>
                                                                        <Link to="#">reply</Link>
                                                                        <Link to="#">translate</Link>
                                                                        <span> 5 min </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </li>
                                                        <li>
                                                            <div className="d-flex ">
                                                                <div className="user-img">
                                                                    <img src={user3} alt="user1" className="avatar-35 rounded-circle img-fluid"/>
                                                                </div>
                                                                <div className="comment-data-block ms-3">
                                                                    <h6>Paul Molive</h6>
                                                                    <p className="mb-0">Lorem ipsum dolor sit amet</p>
                                                                    <div className="d-flex flex-wrap align-items-center comment-activity">
                                                                        <Link to="#">like</Link>
                                                                        <Link to="#">reply</Link>
                                                                        <Link to="#">translate</Link>
                                                                        <span> 5 min </span>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </li>
                                                    </ul>
                                                    <form className="comment-text d-flex align-items-center mt-3" >
                                                        <input type="text" className="form-control rounded" placeholder="Enter Your Comment"/>
                                                        <div className="comment-attagement d-flex">
                                                            <Link to="#"><i className="ri-link me-3"></i></Link>
                                                            <Link to="#"><i className="ri-user-smile-line me-3"></i></Link>
                                                            <Link to="#"><i className="ri-camera-line me-3"></i></Link>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </Card.Body>
                                </Card>
                            </Col>
                            {ids.map(id =>
                                <div key={id}>
                                    <Col sm={12}>
                                        <DanceNewsfeedCard id={id} />
                                    </Col>
                                </div>
                            )}
                        </Col>
                        <div className="col-sm-12 text-center">
                            <img src={loader} alt="loader" style={{height: "100px"}}/>
                        </div>
                    </Row>
                </Container>
            </div>
        </>
    )
}
export default DanceNewsfeed