import React from 'react'
import {Card, Dropdown, Image} from 'react-bootstrap'

import {Link} from "react-router-dom";
import {useKeycloak} from "@react-keycloak/web";
import CustomToggle from "../../../dropdowns";

const DanceUserDropdownLoggedOut = () => {

  const { keycloak} = useKeycloak();

  return (
      <Dropdown as="li" className="nav-item user-dropdown">
        <Dropdown.Toggle
            href="#"
            as={CustomToggle}
            variant="d-flex align-items-center"
        >
          <Image
              src={ '/users/placeholder.jpg'}
              className="img-fluid rounded-circle me-3"
              alt="user"
              loading="lazy"
          />
          <div className="caption d-none d-lg-block">
            <h6 className="mb-0 line-height">Login/Create account</h6>
          </div>
        </Dropdown.Toggle>
        <Dropdown.Menu className="sub-drop caption-menu">
          <Card className="shadow-none m-0">
            <Card.Header>
              <div className="header-title">
                <h5 className="mb-0 ">Login/Create account</h5>
              </div>
            </Card.Header>
            <Card.Body className="p-0 ">
              <div className="d-flex align-items-center iq-sub-card border-0">
                        <span className="material-symbols-outlined">
                          login
                        </span>
                <div className="ms-3">
                  <Link to="#" onClick={() => keycloak.login()} className="mb-0 h6">
                    Login
                  </Link>
                </div>
              </div>
              <div className="d-flex align-items-center iq-sub-card border-0">
                        <span className="material-symbols-outlined">
                          person_add
                        </span>
                <div className="ms-3">
                  <Link to="#" onClick={() => keycloak.login({ action: 'register' })} className="mb-0 h6">
                    Create Account
                  </Link>
                </div>
              </div>
            </Card.Body>
          </Card>
        </Dropdown.Menu>
      </Dropdown>
  )
}

export default DanceUserDropdownLoggedOut