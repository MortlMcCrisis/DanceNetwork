import {Link} from "react-router-dom";

import * as BuyProcessStates from "./dance-buy-tickets-util";

const DanceBuyTicketsHeader = ({state, setState}) => {

  return (
      <ul id="dance-top-tab-list" className="p-0 row list-inline">
        <li id="tickets" className={`
                            ${state === BuyProcessStates.PERSONAL_DATA_STEP || state === BuyProcessStates.PAYMENT_STEP || state === BuyProcessStates.FINISH_STEP ? 'done ' : ''}
                            col-lg-3 col-md-6 text-start mb-2 active`}>
          <Link to="#" onClick={() => setState(BuyProcessStates.TICKET_STEP)}>
            <i className="material-symbols-outlined">insert_drive_file</i><span>Tickets</span>
          </Link>
        </li>
        <li id="personal" className={`
                            ${state === BuyProcessStates.PERSONAL_DATA_STEP ? 'active ' : ''} 
                            ${state === BuyProcessStates.PAYMENT_STEP || state === BuyProcessStates.FINISH_STEP ? 'active done ' : ''} 
                            col-lg-3 col-md-6 mb-2 text-start`}>
          <Link to="#" onClick={() => setState(BuyProcessStates.PERSONAL_DATA_STEP)}>
            <i className="material-symbols-outlined">person</i><span>Personal Data</span>
          </Link>
        </li>
        <li id="payment" className={` 
                            ${state === BuyProcessStates.PAYMENT_STEP ? 'active ' : ''} 
                            ${state === BuyProcessStates.FINISH_STEP ? 'active done ' : ''} 
                            col-lg-3 col-md-6 mb-2 text-start`}>
          <Link to="#" onClick={() => setState(BuyProcessStates.PAYMENT_STEP)}>
            <i className="material-symbols-outlined">credit_card</i><span>Payment</span>
          </Link>
        </li>
        <li id="confirm" className={`
                            ${state === BuyProcessStates.FINISH_STEP ? 'active done ' : ''} 
                            col-lg-3 col-md-6 mb-2 text-start`}>
          <Link to="#">
            <i className="material-symbols-outlined">done</i><span>Finish</span>
          </Link>
        </li>
      </ul>
  )
}

export default DanceBuyTicketsHeader;