import {Button, Modal} from "react-bootstrap";
import React, {useState} from "react";

const DanceAbortButton=({hasAnyDirtyField, resetStateAndCloseMainDialog})=> {

  const [showAbortWarning, setShowAbortWarning] = useState(false);
  const handleCloseAbortWarning = () => setShowAbortWarning(false);

  const abortAndDiscardChange = () => {
    resetStateAndCloseMainDialog();
    setShowAbortWarning(false);
  }

  const handleAbort = () => {
    if(hasAnyDirtyField()){
      setShowAbortWarning(true);
    }
    else {
      resetStateAndCloseMainDialog();
    }
  }

  return(
      <>
        <Button variant="secondary" onClick={handleAbort}>
          Abort
        </Button>
        <Modal size="sm" show={showAbortWarning} onHide={handleCloseAbortWarning}>
          <Modal.Header closeButton>
            <Modal.Title>Modal heading</Modal.Title>
          </Modal.Header>
          <Modal.Body>Changes will not be saved.</Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseAbortWarning}>
              Cancel
            </Button>
            <Button variant="primary" onClick={abortAndDiscardChange}>
              Ok
            </Button>
          </Modal.Footer>
        </Modal>
    </>
  )
}

export default DanceAbortButton