import DanceImageGallery from "./image-gallery";
import {Button, Modal, OverlayTrigger, Tooltip} from "react-bootstrap";
import {React, useState} from "react";
import imgp1 from "../../assets/images/user/15.jpg";
import {Link} from "react-router-dom";

const DanceImageGallerySelectableImage=({setImage, children})=> {

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

  return (
      <>
        <div className="item-1 ms-1 user-images position-relative">
          {children}
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