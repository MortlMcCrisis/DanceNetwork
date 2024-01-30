import React, {useEffect, useState} from 'react'
import {
    Container,
    Row,
    Col,
    Card,
    Form,
    Button,
    ProgressBar, ListGroup, ListGroupItem, Alert
} from 'react-bootstrap'
import {useKeycloak} from "@react-keycloak/web";
import img20 from "../../../assets/images/user/07.jpg";
import imgpp2 from "../../../assets/images/user/11.png";

const DanceUserAccountSetting =() =>{

    const [showA1, setShowA1] = useState(false);

    const { keycloak, initialized } = useKeycloak();

    const [percentage, setPercentage] = useState(0);

    const calculatePercentage = () => {
        const idTokenParsed = keycloak.idTokenParsed;
        const properties = ['photoPath', 'custom_username', 'given_name', 'family_name', 'sex', 'email', 'phone'];

        // Filtere die gesetzten Properties
        const setProperties = properties.filter(property => idTokenParsed[property] !== undefined && idTokenParsed[property] !== '');

        // Berechne den Prozentwert
        const percentage = Math.round((setProperties.length / properties.length) * 100);

        console.log(percentage);

        return percentage;
    };

    useEffect(() => {
        setPercentage(calculatePercentage());
    }, []);

    const handleSubmit = async (event) => {
        event.preventDefault();

        const requestBody = {
            photoPath: event.target.elements.photo.files[0] === undefined ? "" : event.target.elements.photo.files[0].name,
            username: event.target.elements.username.value,
            firstName: event.target.elements.firstName.value,
            lastName: event.target.elements.lastName.value,
            sex: event.target.elements.male.checked === true ? "MALE" : "FEMALE",
            phone: event.target.elements.phone.value,
        };

        keycloak.idTokenParsed.photoPath = event.target.elements.photo.files[0] === undefined ? "" : event.target.elements.photo.files[0].name;
        keycloak.idTokenParsed.custom_username = event.target.elements.username.value;
        keycloak.idTokenParsed.given_name = event.target.elements.firstName.value;
        keycloak.idTokenParsed.family_name = event.target.elements.lastName.value;
        keycloak.idTokenParsed.sex = event.target.elements.male.checked === true ? "MALE" : "FEMALE";
        keycloak.idTokenParsed.phone = event.target.elements.phone.value;

        try {
            const response = await fetch('/api/v1/user', {
                method: 'PUT',
                headers: {
                    Authorization: `Bearer ${keycloak.token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ ...requestBody }),
            });
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            const body = await response.json();

            setShowA1(true);
            setPercentage(calculatePercentage());
        } catch (error) {
            console.error('Error saving user:', error);
        }
    };

  return(
     <>
        <Container>
        <Row>
            <Col lg="3">
            </Col>
            <Col lg="6">
                <Card>
                <Card.Header className="card-header d-flex justify-content-between">
                    <div className="header-title">
                        <h4 className="card-title">Account Setting</h4>
                    </div>
                </Card.Header>
                <Card.Body>
                    <div className="acc-edit">
                        <div className="progress mb-3">
                            <ProgressBar style={{width: `${percentage}%`}} label={`${percentage}%`} now={percentage}/>
                        </div>
                        <Form onSubmit={handleSubmit}>
                            <ListGroup className=" list-group-flush">
                                <ListGroupItem>
                                    <div className="user-detail text-center mb-3">
                                        <div className="profile-img">
                                            <img loading="lazy" src={`/users/${keycloak.idTokenParsed.photoPath}`} alt="profile-img" className="avatar-130 img-fluid"/>
                                        </div>
                                    </div>
                                    <Form.Group className="form-group">
                                        <Form.Label htmlFor="photo">Upload Your Photo:</Form.Label>
                                        <Form.Control type="file" name="pic" id="photo" accept="image/*"/>
                                    </Form.Group>
                                </ListGroupItem>
                                <ListGroupItem>
                            <Form.Group className="form-group">
                                <Form.Label htmlFor="username" className="form-label">User Name: *</Form.Label>
                                <Form.Control type="text" className="form-control" id="username" defaultValue={initialized ? keycloak.idTokenParsed.custom_username : ""}/>
                            </Form.Group>
                            <Form.Group className="form-group">
                                <Form.Label htmlFor="firstName" className="form-label">Name:</Form.Label>
                                <Form.Control type="text" className="form-control" id="firstName"  defaultValue={initialized ? keycloak.idTokenParsed.given_name : ""}/>
                            </Form.Group>
                            <Form.Group className="form-group">
                                <Form.Label htmlFor="lastName" className="form-label">Last name:</Form.Label>
                                <Form.Control type="text" className="form-control" id="lastName" defaultValue={initialized ? keycloak.idTokenParsed.family_name : ""}/>
                            </Form.Group>
                            <Form.Group className="form-group">
                                <Form.Label >Sex: *</Form.Label>
                                <Form.Check className="form-check">
                                    <Form.Check className="form-check form-check-inline">
                                        <Form.Check.Label> Male</Form.Check.Label>
                                        <Form.Check.Input type="radio" defaultChecked={initialized ? keycloak.idTokenParsed.sex === 'MALE' : ""} className="form-check-input" name="customRadio" id="male"/>
                                    </Form.Check>
                                    <Form.Check className="form-check form-check-inline">
                                        <Form.Check.Input type="radio" defaultChecked={initialized ? keycloak.idTokenParsed.sex === 'FEMALE' : ""} className="form-check-input" name="customRadio" id="female"/>
                                        <Form.Check.Label> Female</Form.Check.Label>
                                    </Form.Check>
                                </Form.Check>
                            </Form.Group>
                                </ListGroupItem>
                                <ListGroupItem>
                            <Form.Group className="form-group">
                                <Form.Label htmlFor="email" className="form-label">Email Id:</Form.Label>
                                <Form.Control type="email" className="form-control" id="email" disabled defaultValue={initialized ? keycloak.idTokenParsed.email : ""}/>
                            </Form.Group>
                            <Form.Group className="form-group">
                                <Form.Label htmlFor="phone" className="form-label">Phone:</Form.Label>
                                <Form.Control type="text" className="form-control" id="phone" defaultValue={initialized ? keycloak.idTokenParsed.phone : ""}/>
                            </Form.Group>
                                    </ListGroupItem>
                            </ListGroup>
                            <div className="user-detail text-center mb-3">
                                <Button type="submit" className="btn btn-primary me-2">Save</Button>
                            </div>
                        </Form>
                    </div>
                </Card.Body>
                </Card>
            </Col>
            <Col lg="3">
                <Alert variant="alert alert-success alert-dismissible d-flex align-items-center gap-2 fade show mb-3" show={showA1}  onClose={() => setShowA1(false)} dismissible>
                    <span><i className="material-symbols-outlined">thumb_up</i></span>
                    <span> This is a success alert—check it out!</span>
                </Alert>
            </Col>
        </Row>
    </Container>
     </>
  )
}

export default DanceUserAccountSetting;