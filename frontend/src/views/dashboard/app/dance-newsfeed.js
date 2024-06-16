import React,{useState, useEffect} from 'react'
import {Link} from 'react-router-dom'
import {
    Container,
    Row,
    Col,
    Dropdown,
} from 'react-bootstrap'
import Card from '../../../components/Card'
import CustomToggle from '../../../components/dropdowns'

import imgpp3 from '../../../assets/images/page-img/07.jpg'
import imgpp4 from '../../../assets/images/page-img/06.jpg'

import loader from "../../../assets/images/page-img/page-load-loader.gif";
import s4 from "../../../assets/images/page-img/s4.jpg";
import s5 from "../../../assets/images/page-img/s5.jpg";
import DanceNewsfeedCard from "../../../components/dance-newsfeed-card";
import {useKeycloak} from "@react-keycloak/web";
import {
    NEWSFEED_ENTRIES_CLOSED_ENDPOINT,
    fetchData,
    NEWSFEED_ENTRIES_OPEN_ENDPOINT, USERS_OPEN_ENDPOINT
} from "../../../components/util/network";
import DanceImageGalleryModal
    from "../../../components/image-gallery/image-gallery-modal";

const DanceNewsfeed=()=>{

    const { keycloak, initialized } = useKeycloak();

    const [newsfeedEntries, setNewsfeedEntries] = useState([]);

    const [creators, setCreators] = useState([]);

    useEffect(() => {
        if(keycloak.authenticated) {
            fetchData(NEWSFEED_ENTRIES_CLOSED_ENDPOINT, setNewsfeedEntries, keycloak.token);
        }
        else {
            fetchData(NEWSFEED_ENTRIES_OPEN_ENDPOINT, setNewsfeedEntries);
        }
    }, [keycloak.authenticated]);

    useEffect(() => {
        if(newsfeedEntries.length > 0) {
            const uniqueCreators = new Set(newsfeedEntries.map(newsfeedEntry => newsfeedEntry.creator));
            fetchData(`${USERS_OPEN_ENDPOINT}?${[...uniqueCreators].map(creator => `userUUID=${creator}`).join('&')}`, setCreators);
        }
    }, [newsfeedEntries]);

    return(
        <>
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
                                    <DanceNewsfeedCard newsfeedEntry={newsfeedEntry} creator={creators.find(creator => creator.uuid === newsfeedEntry.creator)}/>
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