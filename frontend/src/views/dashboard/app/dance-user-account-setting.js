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
import DanceFormInput from "./dance-form-input";
import DanceFormCheckbox from "./dance-form-checkbox";

const DanceUserAccountSetting =() =>{

    const [showA1, setShowA1] = useState(false);

    const { keycloak, initialized } = useKeycloak();

    const [percentage, setPercentage] = useState(0);

    const [form, setForm] = React.useState({
        photoPath: keycloak.idTokenParsed.photo_path,
        username: keycloak.idTokenParsed.custom_username,
        firstName: keycloak.idTokenParsed.given_name,
        lastName: keycloak.idTokenParsed.family_name,
        sex: keycloak.idTokenParsed.sex,
        phone: keycloak.idTokenParsed.phone,
    });

    const handleChange = (event) => {
        setForm({
            ...form,
            [event.target.id]: event.target.value,
        });
    };

    const handleSexChange = (event) => {
        setForm({
            ...form,
            ['sex']: event.target.id === 'male' ? 'MALE' : 'FEMALE',
        });
    };

    const calculatePercentage = () => {
        const idTokenParsed = keycloak.idTokenParsed;
        const properties = ['photo_path', 'custom_username', 'given_name', 'family_name', 'sex', 'email', 'phone'];

        // Filtere die gesetzten Properties
        const setProperties = properties.filter(property => idTokenParsed[property] !== undefined && idTokenParsed[property] !== '');

        // Berechne den Prozentwert
        const percentage = Math.round((setProperties.length / properties.length) * 100);

        return percentage;
    };

    useEffect(() => {
        setPercentage(calculatePercentage());
    }, []);

    const handlePhotoChange = async (event) => {
        const formData = new FormData();
        formData.append("file", event.target.files[0]);
        try {
            const result = await fetch('/api/v1/file/photo-upload', {
                method: "POST",
                headers: {
                    Authorization: `Bearer ${keycloak.token}`,
                },
                body: formData,
            });

            const data= await result;
            console.log(data);

            setForm({
                ...form,
                ['photoPath']: event.target.files[0].name,
            });
        } catch (error) {
            console.error(error);
        }
    }
    const handleSubmit = async (event) => {
        event.preventDefault();

        try {
            const response = await fetch('/api/v1/user', {
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
                                    <div className="user-detail text-center mb-3">
                                        <div className="profile-img">
                                            <img loading="lazy" src={form.photoPath === null || form.photoPath === undefined ? '/users/placeholder.jpg' : `/users/${form.photoPath}`} alt="profile-img" className="avatar-130 img-fluid"/>
                                        </div>
                                    </div>
                                    <Form.Group className="form-group">
                                        <Form.Label htmlFor="photo">Upload Your Photo:</Form.Label>
                                        <Form.Control type="file" name="pic" id="photo" accept="image/*" onChange={handlePhotoChange}/>
                                    </Form.Group>
                                </ListGroupItem>
                                <ListGroupItem>
                                    <DanceFormInput id="username" label="User name: *" placeholder="Enter your user name" type="text" value={form.username} onChange={(event) => handleChange(event)} />
                                    <DanceFormInput id="firstName" label="First name: *" placeholder="Enter your first name" type="text" value={form.firstName} onChange={(event) => handleChange(event)} />
                                    <DanceFormInput id="lastName" label="Last name: *" placeholder="Enter your last name" type="text" value={form.lastName} onChange={(event) => handleChange(event)} />
                                    {/*TODO change to any kind of gender neutral sex*/}
                                    <DanceFormCheckbox id="sex" label="Sex: *" values={[
                                        {label: 'Male', id: 'male', defaultChecked: form.sex === 'MALE'},
                                        {label: 'Female', id: 'female', defaultChecked: form.sex === 'FEMALE'}]}
                                                       onChange={(event) => handleSexChange(event)}/>
                                </ListGroupItem>
                                <ListGroupItem>
                                    <DanceFormInput id="email" label="E-Mail: *" type="email" value={initialized ? keycloak.idTokenParsed.email : ""} disabled={"disabled"} />
                                    <DanceFormInput id="phone" label="Phone:" placeholder="Enter your phone" type="text" value={form.phone} onChange={(event) => handleChange(event)} />
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