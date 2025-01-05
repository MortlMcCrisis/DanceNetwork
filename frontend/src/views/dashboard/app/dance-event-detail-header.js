import Card from "../../../components/Card";
import {
  Alert, Button,
  Col,
  Row
} from "react-bootstrap";
import {Link, useParams} from "react-router-dom";
import imgp2 from "../../../assets/images/user/05.jpg";
import imgp3 from "../../../assets/images/user/06.jpg";
import imgp4 from "../../../assets/images/user/07.jpg";
import imgp5 from "../../../assets/images/user/08.jpg";
import {useKeycloak} from "@react-keycloak/web";
import {
  EVENTS_CLOSED_ENDPOINT,
  putData
} from "../../../components/util/network";
import DanceImageGallerySelectableImage
  from "../../../components/image-gallery/image-gallery-selectable-image";
import {toast} from "react-toastify";
import imgpp38 from "../../../assets/images/icon/08.png";
import imgpp39 from "../../../assets/images/icon/09.png";
import imgpp40 from "../../../assets/images/icon/10.png";
import imgpp41 from "../../../assets/images/icon/11.png";
import imgpp42 from "../../../assets/images/icon/12.png";
import imgpp43 from "../../../assets/images/icon/13.png";
import React from "react";

const DanceEventDetailHeader=({data, setData, creator})=> {

  const { id } = useParams();

  const { keycloak, initialized } = useKeycloak();

  const setImage=(path)=>{
    setImageAndSave(path, saveEvent)
  }

  const saveEvent = async (dataToSave) => {
    await putData(`${EVENTS_CLOSED_ENDPOINT}/${id}`, JSON.stringify({...dataToSave}), keycloak.token);
    toast.success("Event successfully updated");
  }

  const setImageAndSave=(path, saveCallback)=>{
    setData(prevData => {
      const newData = {
        ...prevData,
        profileImage: path,
      };
      saveCallback(newData);
      return newData;
    });
  }

  const publishEvent = async () => {
    if(data.profileImage === "" || data.profileImage === null){
      toast.error("Profile image must be set")
    }
    else {
      await putData(`${EVENTS_CLOSED_ENDPOINT}/${id}/publish`, null,
          keycloak.token);
      window.location.reload();
    }
  };

  return(
      <>
        {keycloak.authenticated && data.published === false &&
          <div className="btn dance-btn-fixed-bottom btn-danger btn-icon btn-setting" >
            <Button variant="info" className="rounded-pill mb-1" onClick={publishEvent}>Publish event</Button>
          </div>
        }
        <Card>
            {keycloak.authenticated && data.published === false &&
              <Card.Header>
                <Col lg={12}>
                  <Alert variant="alert alert-left alert-warning show d-flex align-items-center gap-2 mb-3">
                    <span><i className="material-symbols-outlined">notifications</i></span>
                    <span>This Event is not yet published!</span>
                  </Alert>
                </Col>
              </Card.Header>
            }
          <Card.Body>
            <Row>
              <Col lg="2">
                {keycloak.authenticated && initialized && keycloak.idTokenParsed.sub === data.creator ? (
                  <DanceImageGallerySelectableImage setImage={setImage}>
                    <img loading="lazy" src={data.profileImage} className="img-fluid rounded profile-image" alt="profile-img"/>
                  </DanceImageGallerySelectableImage>
                ) : (
                  <img loading="lazy" src={data.profileImage} className="img-fluid rounded profile-image" alt="profile-img"/>
                )
                }
              </Col>
              <Col lg="10">
                <div className="d-flex justify-content-between">
                  <div className="item2 ">
                    <h4 className="">{data.name}</h4>
                  </div>
                </div>
                <Row>
                  <div className="profile-info p-3 d-flex align-items-center justify-content-between position-relative">
                    <div className="item6">
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
                    <div className="social-info">
                      <ul className="social-data-block d-flex align-items-center justify-content-between list-inline p-0 m-0">
                        <li className="text-center pe-3">
                          <Link to="#"><img loading="lazy" src={imgpp38} className="img-fluid rounded" alt="facebook"/></Link>
                        </li>
                        <li className="text-center pe-3">
                          <Link to="#"><img loading="lazy" src={imgpp39} className="img-fluid rounded" alt="Twitter"/></Link>
                        </li>
                        <li className="text-center pe-3">
                          <Link to="#"><img loading="lazy" src={imgpp40} className="img-fluid rounded" alt="Instagram"/></Link>
                        </li>
                        <li className="text-center pe-3">
                          <Link to="#"><img loading="lazy" src={imgpp41} className="img-fluid rounded" alt="Google plus"/></Link>
                        </li>
                        <li className="text-center pe-3">
                          <Link to="#"><img loading="lazy" src={imgpp42} className="img-fluid rounded" alt="You tube"/></Link>
                        </li>
                        <li className="text-center md-pe-3 pe-0">
                          <Link to="#"><img loading="lazy" src={imgpp43} className="img-fluid rounded" alt="linkedin"/></Link>
                        </li>
                      </ul>
                    </div>
                  </div>
                </Row>
              </Col>
            </Row>
          </Card.Body>
        </Card>
      </>
  )
}

export default DanceEventDetailHeader