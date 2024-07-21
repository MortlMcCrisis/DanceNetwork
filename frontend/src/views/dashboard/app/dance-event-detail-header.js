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
import DanceEventDetailHeaderEditButton
  from "./dance-event-detail-header-edit-button";
import {useKeycloak} from "@react-keycloak/web";
import {
  EVENTS_CLOSED_ENDPOINT,
  putData
} from "../../../components/util/network";
import LocationIcon from "../../../components/text_icons/location";
import WebsiteIcon from "../../../components/text_icons/website";
import EmailIcon from "../../../components/text_icons/email";
import DateIcon from "../../../components/text_icons/date";
import DanceImageGallerySelectableImage
  from "../../../components/image-gallery/image-gallery-selectable-image";
import {toast} from "react-toastify";
import OwnerIcon from "../../../components/text_icons/owner";

const DanceEventDetailHeader=({data, setData, creator})=> {

  const { id } = useParams();

  const { keycloak, initialized } = useKeycloak();

  const setImage=(path)=>{
    setImageAndSave(path, saveEvent)
  }

  const saveEvent = async (dataToSave) => {
    await putData(`${EVENTS_CLOSED_ENDPOINT}/${id}`, dataToSave, keycloak.token);
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
                    <span>60 630 are going</span>
                  </div>
                  {keycloak.authenticated && initialized && keycloak.idTokenParsed.sub === data.creator &&
                    <div className="item4 ms-1">
                      <div className="d-flex justify-content-between align-items-center ms-1 flex-wrap">
                        <DanceEventDetailHeaderEditButton data={data} setData={setData} />
                      </div>
                    </div>
                  }
                </div>
                <Row>
                  <Col lg="6">
                    <div className="item5 mt-3">
                      <DateIcon startDate={data.startDate} endDate={data.endDate} />
                      {data.location &&
                        <LocationIcon text={data.location} />
                      }
                      {data.website &&
                        <WebsiteIcon text={data.website} />
                      }
                      {data.email &&
                        <EmailIcon text={data.email} />
                      }
                      {creator != undefined && creator != null &&
                        <OwnerIcon text={`${creator.firstName} ${creator.lastName}`} />
                      }
                    </div>
                  </Col>
                  <Col lg="6">
                    <div className="item6 border-light border-start">
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
                  </Col>
                </Row>
              </Col>
            </Row>
          </Card.Body>
        </Card>
      </>
  )
}

export default DanceEventDetailHeader