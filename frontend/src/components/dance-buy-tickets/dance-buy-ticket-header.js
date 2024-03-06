import {Link} from "react-router-dom";

const DanceBuyTicketHeader = (show) => {

  return (
      <ul id="dance-top-tab-list" className="p-0 row list-inline">
        <li className={`${show === 'PersonalData' || show === 'Payment' || show === 'Finish' ? 'done ' : ''}
                        col-lg-3 col-md-6 text-start mb-2 active`} id="account">
          <Link to="#">
            <i className="material-symbols-outlined">insert_drive_file</i><span>Tickets</span>
          </Link>
        </li>
        <li id="personal"
            className={` ${show === 'Image' ? 'active done' : ''} ${show
            === 'Personal' ? 'active done' : ''} ${show === 'Account'
                ? 'active done' : ''} col-lg-3 col-md-6 mb-2 text-start`}>
          <Link to="#">
            <i className="material-symbols-outlined">person</i><span>Personal Data</span>
          </Link>
        </li>
        <li id="payment"
            className={` ${show === 'Image' ? 'active done' : ''} ${show
            === 'Personal' ? 'active done'
                : ''} col-lg-3 col-md-6 mb-2 text-start`}>
          <Link to="#">
            <i className="material-symbols-outlined">credit_card</i><span>Payment</span>
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