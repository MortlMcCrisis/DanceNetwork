import DanceImageGallery from "./image-gallery";
import {Button, Modal, OverlayTrigger, Tooltip} from "react-bootstrap";
import {React, useState} from "react";
import imgp1 from "../../assets/images/user/15.jpg";
import {Link} from "react-router-dom";

const DanceImageGallerySelectableImage=({startImage, setImage})=> {

  //TODO how to get current selected?
  const [currentSelected, setCurrentSelected] = useState("");

  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  const handleSave = () => {
    setImage(currentSelected);
    handleClose();
  }

  const handleSetImage = (path) => {
    setCurrentSelected(path);
  }

  //TODO I need 3 versions
  // - rectangled version e.g for event profile
  // - round version e.g. for user profile
  // - custom size version for generic images
  // maybe I only need the third. The first and second then can be just used by setting the heigh and length to same size and the roundness can be configured form the outside

  return (
      <>
        <div className="item-1 ms-1 user-images position-relative">
          <img loading="lazy" src={currentSelected === "" ? (startImage === "" || startImage === null ? '/users/placeholder.jpg' : startImage) : `${currentSelected}`} className="img-fluid rounded profile-image" alt="profile-img"/>
          <OverlayTrigger placement="top" overlay={<Tooltip>Change</Tooltip>}>
            <Link onClick={handleShow} className="image-edit-btn material-symbols-outlined md-16">
              drive_file_rename_outline
            </Link>
          </OverlayTrigger>
        </div>
        <Modal size="xl" scrollable={true} show={show} onHide={handleClose}>
          <Modal.Header>
          </Modal.Header>
          <Modal.Body>
            <DanceImageGallery setImage={handleSetImage}/>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              Close
            </Button>
            <Button variant="primary" onClick={handleSave}>
              Save Changes
            </Button>
          </Modal.Footer>
        </Modal>
      </>
  )
}

export default DanceImageGallerySelectableImage