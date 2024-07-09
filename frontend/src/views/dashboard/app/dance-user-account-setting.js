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
import DanceFormInput from "./dance-form-input";
import DanceFormCheckbox from "./dance-form-checkbox";
import {USERS_CLOSED_ENDPOINT} from "../../../components/util/network";
import {Link} from "react-router-dom";
import ImageGalleryModal
    from "../../../components/image-gallery/image-gallery-modal";
import DanceImageGallerySelectableImage
    from "../../../components/image-gallery/image-gallery-selectable-image";

const DanceUserAccountSetting =() =>{

    const [showA1, setShowA1] = useState(false);

    const { keycloak, initialized } = useKeycloak();

    const [percentage, setPercentage] = useState(0);

    const [form, setForm] = React.useState({
        photoPath: keycloak.idTokenParsed.photo_path,
        username: keycloak.idTokenParsed.custom_username,
        firstName: keycloak.idTokenParsed.given_name,
        lastName: keycloak.idTokenParsed.family_name,
        gender: keycloak.idTokenParsed.gender,
        phone: keycloak.idTokenParsed.phone,
    });

    const handleChange = (event) => {
        setForm({
            ...form,
            [event.target.id]: event.target.value,
        });
    };

    const handleGenderChange = (event) => {
        switch(event.target.id) {
            case 'male':
                event.target.value  = 'MALE';
                break;
            case 'female':
                event.target.value  = 'FEMALE';
                break;
            case 'other':
                event.target.value  = 'OTHER';
                break;
            default:
                event.target.value = '';
        }

        setForm({
            ...form,
            ['gender']: event.target.value,
        });
    };

    const calculatePercentage = () => {
        const idTokenParsed = keycloak.idTokenParsed;
        const properties = ['photo_path', 'custom_username', 'given_name', 'family_name', 'gender', 'email', 'phone'];

        // Filtere die gesetzten Properties
        const setProperties = properties.filter(property => idTokenParsed[property] !== undefined && idTokenParsed[property] !== '');

        // Berechne den Prozentwert
        const percentage = Math.round((setProperties.length / properties.length) * 100);

        return percentage;
    };

    useEffect(() => {
        setPercentage(calculatePercentage());
    }, []);

    const setImage=(path)=>{
        setForm({
            ...form,
            ['photoPath']: path,
        });
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        console.log(keycloak.token)

        //TODO use util method
        try {
            const response = await fetch(USERS_CLOSED_ENDPOINT, {
                method: 'PUT',
                headers: {
                    Authorization: `Bearer ${keycloak.token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ ...form }),
            });
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            /*const body = await response.json();

            setShowA1(true);
            setPercentage(calculatePercentage());*/

            window.location.reload();
        } catch (error) {
            console.error('Error saving user:', error);
        }
    };

    //TODO: hier sollte alle daten definiert werden können, die im kaufprozess angegeben werden können.
    // Diese sollen dann durch das selektieren eines freundes direkt ausgefüllt werden

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
                        <Form className="form-horizontal" onSubmit={handleSubmit}>
                            <ListGroup className=" list-group-flush">
                                <ListGroupItem>
                                    {/*<div className="user-detail text-center mb-3">
                                        <Link className="profile-img">
                                            <img loading="lazy" src={form.photoPath === null || form.photoPath === undefined ? '/users/placeholder.jpg' : `${form.photoPath}`} alt="profile-img" className="avatar-130 img-fluid"/>
                                        </Link>
                                    </div>
                                    <div className="user-detail text-center mb-3">
                                        <ImageGalleryModal setImage={setImage}/>
                                    </div>*/}
                                    <div className="user-detail text-center mb-3">
                                        <Link className="profile-img">
                                            <DanceImageGallerySelectableImage startImage={form.photoPath} setImage={setImage} />
                                        </Link>
                                    </div>
                                </ListGroupItem>
                                <ListGroupItem>
                                    <DanceFormInput id="username" label="User name" placeholder="Enter your user name" type="text" value={form.username} onChange={(event) => handleChange(event)} />
                                    <DanceFormInput id="firstName" label="First name" placeholder="Enter your first name" type="text" value={form.firstName} onChange={(event) => handleChange(event)} />
                                    <DanceFormInput id="lastName" label="Last name" placeholder="Enter your last name" type="text" value={form.lastName} onChange={(event) => handleChange(event)} />
                                    <DanceFormCheckbox id="gender" label="Gender" values={[
                                        {label: 'Male', id: 'male', defaultChecked: form.gender === 'MALE'},
                                        {label: 'Female', id: 'female', defaultChecked: form.gender === 'FEMALE'},
                                        {label: 'Other', id: 'other', defaultChecked: form.gender === 'OTHER'}]}
                                                       onChange={(event) => handleGenderChange(event)}/>
                                </ListGroupItem>
                                <ListGroupItem>
                                    <DanceFormInput id="email" label="E-Mail" type="email" value={initialized ? keycloak.idTokenParsed.email : ""} disabled={"disabled"} required="required"/>
                                    <DanceFormInput id="phone" label="Phone" placeholder="Enter your phone" type="text" value={form.phone} onChange={(event) => handleChange(event)} />
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