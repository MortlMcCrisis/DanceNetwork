import React, {useEffect, useState} from 'react'
import {Container, Row, Col, Button, Form} from 'react-bootstrap'
import Card from '../Card'

import addImage from '../../assets/images/page-img/addImage.jpg'
import ImageGalleryImage from "./image-gallery-image";
import {
    fetchData,
    FILES_CLOSED_ENDPOINT
} from "../util/network";
import {useKeycloak} from "@react-keycloak/web";
import {Link} from "react-router-dom";

const DanceImageGallery=({setImage})=>{

    const { keycloak, initialized } = useKeycloak();

    const [imageInfos, setImageInfos] = useState([]);

    const [selectedIndex, setSelectedIndex] = useState(0);

    const handleSetImage = (index) => {
        setSelectedIndex(index);
        setImage(imageInfos[index].path)
    }

    useEffect(() => {
        if(keycloak.authenticated) {
            fetchData(FILES_CLOSED_ENDPOINT + "/list-user-images", setImageInfos, keycloak.token);
            console.log(imageInfos);
        }
    }, [keycloak.authenticated]);

    const handlePhotoUpload = async (event) => {
        const formData = new FormData();
        formData.append("file", event.target.files[0]);
        try {
            //TODO use util method
            const result = await fetch(`${FILES_CLOSED_ENDPOINT}/photo-upload`, {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${keycloak.token}`,
                },
                body: formData,
            });
            const data = await result;
            console.log(data);
        } catch (error) {
            console.error(error);
        }
    }

    return(
        <div id='content-page' className='content-page'>
            <Container>
                <h2>Image gallery</h2>
                <hr/>
                <Row>
                    {//TODO handle correctly when no image exists
                        imageInfos[selectedIndex] ? (
                        <>
                            <Col lg="4">
                                <Card>
                                    <div className="top-bg-image">
                                        <img loading="lazy" src={imageInfos[selectedIndex].path} className="img-fluid" alt="Responsive" />
                                    </div>
                                    <Card.Body className="text-center">
                                        {/*
                                        TODO store title and upload date (and other infos?) in image infos
                                        TODO add possiblility to edit image properties like name
                                        TODO add image crop and edit functionality
                                        <h2 className="text-center">{likephoto[selectedIndex].title}</h2>*/}
                                        <Button variant="primary rounded-pill w-100 mt-3">edit</Button>
                                    </Card.Body>
                                    <Card.Footer>
                                        <ul className="list-inline p-0 m-0">
                                            <li className="mb-md-3 pb-md-3 members-list">
                                                <h4>Used at the following locations</h4>
                                            </li>
                                            <li className="mb-3 d-flex align-items-center">
                                                <div className="me-2"><i className="ri-arrow-right-fill h4"></i></div>
                                                <h6 className="mb-0">here</h6>
                                            </li>
                                            <li className="mb-3 d-flex align-items-center">
                                                <div className="me-2"><i className="ri-arrow-right-fill h4"></i></div>
                                                <h6 className="mb-0">also here</h6>
                                            </li>
                                            <li className="mb-3 d-flex align-items-center">
                                                <div className="me-2"><i className="ri-arrow-right-fill h4"></i></div>
                                                <h6 className="mb-0">and here</h6>
                                            </li>
                                        </ul>
                                    </Card.Footer>
                                </Card>
                            </Col>
                            <Col lg="8">
                                <Row>
                                    <Col sm="4">
                                        <Card>
                                            <Card.Body>
                                                <div className="event-images position-relative">
                                                    <Link to="#">
                                                        <img loading="lazy" src={addImage} className="img-fluid" alt="Responsive" />
                                                    </Link>
                                                </div>
                                                <hr className="hr-horizontal"/>
                                                <h4>
                                                    <Form className="form-horizontal">
                                                        <Form.Group className="form-group">
                                                            <Form.Control type="file" name="pic" id="photo" accept="image/*" onChange={handlePhotoUpload}/>
                                                        </Form.Group>
                                                    </Form>
                                                </h4>
                                            </Card.Body>
                                        </Card>
                                    </Col>
                                    {
                                        imageInfos.map((item,index)=> {
                                            return(
                                                <Col sm="4" key={index}>
                                                    <ImageGalleryImage selectedIndex={selectedIndex} index={index} setSelectedIndex={handleSetImage} item={item} />
                                                </Col>
                                            )
                                        })
                                    }
                                </Row>
                            </Col>
                        </>
                        ) : (
                            <>
                                <Col lg="4">
                                    <Card>

                                    </Card>
                                </Col>
                                <Col lg="8">
                                    <Row>
                                        <Col sm="4">
                                            <Card>
                                                <Card.Body>
                                                    <div className="event-images position-relative">
                                                        <Link to="#">
                                                            <img loading="lazy" src={addImage} className="img-fluid" alt="Responsive" />
                                                        </Link>
                                                    </div>
                                                    <hr className="hr-horizontal"/>
                                                    <h4>
                                                        <Form className="form-horizontal">
                                                            <Form.Group className="form-group">
                                                                <Form.Control type="file" name="pic" id="photo" accept="image/*" onChange={handlePhotoUpload}/>
                                                            </Form.Group>
                                                        </Form>
                                                    </h4>
                                                </Card.Body>
                                            </Card>
                                        </Col>
                                    </Row>
                                </Col>
                            </>
                        )
                    }
                </Row>
            </Container>
        </div>
    )
}
export default DanceImageGallery