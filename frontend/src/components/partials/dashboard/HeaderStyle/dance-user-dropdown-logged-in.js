import React from 'react'
import {Card, Dropdown, Image} from 'react-bootstrap'

//image
import {Link} from "react-router-dom";
import {useKeycloak} from "@react-keycloak/web";
import CustomToggle from "../../../dropdowns";

const DanceUserDropdownLoggedIn = () => {

  const { keycloak, initialized } = useKeycloak();

  return (
      <Dropdown as="li" className="nav-item user-dropdown">
        <Dropdown.Toggle
            href="#"
            as={CustomToggle}
            variant="d-flex align-items-center"
        >
          <Image
              src={keycloak.idTokenParsed.photo_path === undefined || keycloak.idTokenParsed.photo_path === null ? '/users/placeholder.jpg' : `${keycloak.idTokenParsed.photo_path}`}
              className="img-fluid rounded-circle me-3"
              alt="user"
              loading="lazy"
          />
          <div className="caption d-none d-lg-block">
            <h6 className="mb-0 line-height">{keycloak.idTokenParsed.custom_username}</h6>
          </div>
        </Dropdown.Toggle>
        <Dropdown.Menu className="sub-drop caption-menu">
          <Card className="shadow-none m-0">
            <Card.Header>
              <div className="header-title">
                <h5 className="mb-0 ">Hello {keycloak.idTokenParsed.custom_username}</h5>
              </div>
            </Card.Header>
            <Card.Body className="p-0 ">
              <div className="d-flex align-items-center iq-sub-card border-0">
                        <span className="material-symbols-outlined">
                          line_style
                        </span>
                <div className="ms-3">
                  <Link to="/dashboard/app/profile" className="mb-0 h6">
                    My Profile
                  </Link>
                </div>
              </div>
              <div className="d-flex align-items-center iq-sub-card border-0">
                        <span className="material-symbols-outlined">
                          edit_note
                        </span>
                <div className="ms-3">
                  <Link to="#" className="mb-0 h6">
                    Edit Profile
                  </Link>
                </div>
              </div>
              <div className="d-flex align-items-center iq-sub-card border-0">
                        <span className="material-symbols-outlined">
                          manage_accounts
                        </span>
                <div className="ms-3">
                  <Link
                      to="/dashboard/app/dance-user-account-setting"
                      className="mb-0 h6"
                  >
                    Account settings
                  </Link>
                </div>
              </div>
              <div className="d-flex align-items-center iq-sub-card border-0">
                <span className="material-symbols-outlined">lock</span>
                <div className="ms-3">
                  <Link
                      to="/dashboard/app/user-privacy-setting"
                      className="mb-0 h6"
                  >
                    Privacy Settings
                  </Link>
                </div>
              </div>
              <div className="d-flex align-items-center iq-sub-card">
                <span className="material-symbols-outlined">logout</span>
                <div className="ms-3">
                  <Link
                      to="/"
                      className="mb-0 h6"
                      onClick={() => keycloak.logout()}
                  >
                    Logout
                  </Link>
                </div>
              </div>
            </Card.Body>
          </Card>
        </Dropdown.Menu>
      </Dropdown>
  )
}

export default DanceUserDropdownLoggedIn