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
import {
    fetchData,
    NEWSFEED_ENTRIES_ENDPOINT
} from "../../../components/util/network";

// Fslightbox plugin
const FsLightbox = ReactFsLightbox.default ? ReactFsLightbox.default : ReactFsLightbox;

const DanceNewsfeed=()=>{

    //TODO load not only ids but directly the data
    const [newsfeedEntries, setNewsfeedEntries] = useState([]);

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
            fetchData(NEWSFEED_ENTRIES_ENDPOINT, keycloak.token, setNewsfeedEntries);
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
                            {newsfeedEntries.map(newsfeedEntry =>
                                <Col sm={12} key={newsfeedEntry.id}>
                                    <DanceNewsfeedCard newsfeedEntry={newsfeedEntry} />
                                </Col>
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