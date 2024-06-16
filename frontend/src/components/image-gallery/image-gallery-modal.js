import DanceImageGallery from "./image-gallery";
import {Button, Modal} from "react-bootstrap";
import {React, useState} from "react";

const DanceImageGalleryModal=({setImage})=> {

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
        <Button variant="primary" onClick={handleShow}>
          Set Image
        </Button>
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

export default DanceImageGalleryModal