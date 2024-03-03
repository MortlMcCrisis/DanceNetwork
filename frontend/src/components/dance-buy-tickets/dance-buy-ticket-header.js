import React, {useState} from "react";
import {Link} from "react-router-dom";

const DanceBuyTicketHeader = () => {

  const [show, setShow] = useState('')
  return (
      <ul id="dance-top-tab-list" className="p-0 row list-inline">
        <li className={` ${show === 'Image' ? 'active done' : ''} ${show
        === 'Personal' ? 'active done' : ''} ${show === 'Account'
            ? 'active done' : ''} ${show === 'A' ? 'active '
            : ''} col-lg-3 col-md-6 text-start mb-2 active`} id="account">
          <Link to="#">
            <i className="material-symbols-outlined">lock_open</i><span>Account</span>
          </Link>
        </li>
        <li id="personal"
            className={` ${show === 'Image' ? 'active done' : ''} ${show
            === 'Personal' ? 'active done' : ''} ${show === 'Account'
                ? 'active done' : ''} col-lg-3 col-md-6 mb-2 text-start`}>
          <Link to="#">
            <i className="material-symbols-outlined">person</i><span>Personal</span>
          </Link>
        </li>
        <li id="payment"
            className={` ${show === 'Image' ? 'active done' : ''} ${show
            === 'Personal' ? 'active done'
                : ''} col-lg-3 col-md-6 mb-2 text-start`}>
          <Link to="#">
            <i className="material-symbols-outlined">photo_camera</i><span>Image</span>
          </Link>
        </li>
        <li id="confirm" className={` ${show === 'Image' ? 'active done'
            : ''} col-lg-3 col-md-6 mb-2 text-start`}>
          <Link to="#">
            <i className="material-symbols-outlined">done</i><span>Finish</span>
          </Link>
        </li>
      </ul>
  )
}

export default DanceBuyTicketHeader;