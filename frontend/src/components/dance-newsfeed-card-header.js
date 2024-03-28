import React, {useEffect, useState} from 'react'
import { Dropdown } from 'react-bootstrap'
import img from '../assets/images/user/user-1.jpg'
import {useKeycloak} from "@react-keycloak/web";
import {USERS_ENDPOINT} from "./util/network";

const DanceNewsfeedCardHeader = ({type, creatorUUID, creationDate}) => {

    const { keycloak, initialized } = useKeycloak();

    const [creator, setCreator] = useState( null );

    useEffect(() => {
        if(keycloak.authenticated) {
            const fetchCreator = async () => {
                try {
                    const response = await fetch(`${USERS_ENDPOINT}/${creatorUUID}`, {
                        headers: {
                            Authorization: `Bearer ${keycloak.token}`,
                            'Content-Type': 'application/json',
                        },
                    });
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    const body = await response.json();
                    console.log(body);
                    setCreator(body);
                } catch (error) {
                    console.error(`Error fetching creator with id ${creatorUUID}:`, error);
                }
            };

            fetchCreator();
        }
    }, [keycloak.authenticated]);

    function formatTimestamp(timestamp) {
        const now = new Date();
        const pastDate = new Date(timestamp);

        const timeDifference = now - pastDate;
        const seconds = Math.floor(timeDifference / 1000);
        const minutes = Math.floor(seconds / 60);
        const hours = Math.floor(minutes / 60);
        const days = Math.floor(hours / 24);

        if (seconds < 60) {
            return "Less than a minute";
        } else if (minutes < 60) {
            return `${minutes} ${minutes === 1 ? "minute" : "minutes"} ago`;
        } else if (hours < 24) {
            return `${hours} ${hours === 1 ? "hour" : "hours"} ago`;
        } else if (days < 7) {
            return `${days} ${days === 1 ? "day" : "days"} ago`;
        } else {
            // Wenn es länger als 7 Tage ist, zeige das Datum im Format "yyyy MM dd"
            const year = pastDate.getFullYear();
            const month = String(pastDate.getMonth() + 1).padStart(2, "0");
            const day = String(pastDate.getDate()).padStart(2, "0");
            return `${year} ${month} ${day}`;
        }
    }
   
    return (
        <>
            {creator !== null && (
                <div className="user-post-data">
                    <div className="d-flex justify-content-between">
                        <div className="me-3">
                            <img className="rounded-circle img-fluid" src={creator.photoPath === null ? '/users/placeholder.jpg' : `/users/${creator.photoPath}`} alt="" style={{ width: '65px', height: '55px' }}/>
                        </div>
                        <div className="w-100">
                            <div className=" d-flex  justify-content-between">
                                <div>
                                    <h5 className="mb-0 d-inline-block">{creator.username}</h5>
                                    {
                                        (() => {
                                            switch (type) {
                                                case "POST":
                                                    return (
                                                        <p className="mb-0 ps-1 d-inline-block">Created a post</p>);
                                                    break;
                                                case "EVENT_CREATION":
                                                    return (
                                                        <p className="mb-0 ps-1 d-inline-block">Created an event</p>);
                                                    break;
                                                case "STATUS_UPDATE":
                                                    return (
                                                        <p className="mb-0 ps-1 d-inline-block">Updated {creator.sex
                                                        === 'MALE' ? 'his'
                                                            : 'her'} status</p>);
                                                    break;
                                                default:
                                                    return (
                                                        <p className="mb-0 ps-1 d-inline-block">Blub</p>);
                                            }
                                        }
                                        )()
                                    }
                                    <p className="mb-0 text-primary">{formatTimestamp(creationDate)}</p>
                                </div>
                                <div className="card-post-toolbar">
                                    <Dropdown>
                                        <Dropdown.Toggle variant="bg-transparent">
                                                            <span className="material-symbols-outlined">
                                                                more_horiz
                                                            </span>
                                        </Dropdown.Toggle>
                                        <Dropdown.Menu className="dropdown-menu m-0 p-0">
                                            <Dropdown.Item className=" p-3" to="#">
                                                <div className="d-flex align-items-top">
                                                    <div className="h4">
                                                        <i className="ri-save-line"></i>
                                                    </div>
                                                    <div className="data ms-2">
                                                        <h6>Save Post</h6>
                                                        <p className="mb-0">Add this to your saved items</p>
                                                    </div>
                                                </div>
                                            </Dropdown.Item>
                                            <Dropdown.Item className= "p-3" to="#">
                                                <div className="d-flex align-items-top">
                                                    <i className="ri-close-circle-line h4"></i>
                                                    <div className="data ms-2">
                                                        <h6>Hide Post</h6>
                                                        <p className="mb-0">See fewer posts like this.</p>
                                                    </div>
                                                </div>
                                            </Dropdown.Item>
                                            <Dropdown.Item className=" p-3" to="#">
                                                <div className="d-flex align-items-top">
                                                    <i className="ri-user-unfollow-line h4"></i>
                                                    <div className="data ms-2">
                                                        <h6>Unfollow User</h6>
                                                        <p className="mb-0">Stop seeing posts but stay friends.</p>
                                                    </div>
                                                </div>
                                            </Dropdown.Item>
                                            <Dropdown.Item className=" p-3" to="#">
                                                <div className="d-flex align-items-top">
                                                    <i className="ri-notification-line h4"></i>
                                                    <div className="data ms-2">
                                                        <h6>Notifications</h6>
                                                        <p className="mb-0">Turn on notifications for this post</p>
                                                    </div>
                                                </div>
                                            </Dropdown.Item>
                                        </Dropdown.Menu>
                                    </Dropdown>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </>
    )
}
export default DanceNewsfeedCardHeader;