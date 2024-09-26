import React, {useEffect, useState} from 'react'

// react-boostrap
import {Container, Col, Row, Dropdown,ProgressBar } from 'react-bootstrap'

// components
import Card from '../../../components/Card'
import CustomToggle from '../../../components/dropdowns'

// Datepicker
import Datepicker from '../../../components/datepicker'

// apex-chart
import Chart from "react-apexcharts"
import {useKeycloak} from "@react-keycloak/web";
import {useParams} from "react-router-dom";
import {
    ADMIN_CLOSED_ENDPOINT,
    fetchData
} from "../../../components/util/network";

const DanceAdmin = () => {
    const { keycloak, initialized } = useKeycloak();

    const { id } = useParams();

    const [data, setData] = useState([]);

    useEffect(() => {
        if(keycloak.authenticated) {
            fetchData(`${ADMIN_CLOSED_ENDPOINT}/event-statistics/${id}`, setData, keycloak.token);
        }

        console.log(data)
    }, [keycloak.authenticated]);

    const getTotalTicketSales = () => {
        const ticketSales = Object.values(data.ticketSalesPerMonth);
        return ticketSales.reduce((total, current) => total + current, 0);
    };

    const getTotalTickets = () => {
        return data.ticketTypesSoldFromContingent.reduce((sum, item) => {
            return sum + item.second.second;
        }, 0);
    };

    const [adminChart, setAdminChart] = useState({
        options: {
            colors: ["#50b5ff", "#FAC4A3"],
            chart: {
                toolbar: {
                    show: false
                },
            },
            forecastDataPoints: {
                count: 1,
            },
            stroke: {
                width: 3,
            },
            grid: {
                show:true,
                strokeDashArray: 7,
            },
            markers: {
                size: 6,
                colors:  '#FFFFFF',
                strokeColors: ["#50b5ff"],
                strokeWidth: 2,
                strokeOpacity: 0.9,
                strokeDashArray: 0,
                fillOpacity: 0,
                shape: "circle",
                radius: 2,
                offsetX: 0,
                offsetY: 0,
            },
            xaxis: {
                categories: [], // This will be set dynamically
                axisBorder: {
                    show: false,
                },
                axisTicks: {
                    show: false,
                },
                tooltip: {
                    enabled: false,
                }
            },
        },
        series: [
            {
                name: 'Absolute',
                type: 'line',
                data: [] // This will be set dynamically
            },
            {
                name: 'per Month',
                type: 'column',
                data: [] // This will be set dynamically
            }
        ],
    });

    useEffect(() => {

        if(data && data.ticketSalesPerMonth) {
            const categories = Object.keys(data.ticketSalesPerMonth).map(date => formatDate(date))

            const salesPerMonth = Object.values(data.ticketSalesPerMonth)

            const absoluteSales = salesPerMonth.reduce((acc, current, index) => {
                if (index === 0) {
                    acc.push(current)
                } else {
                    acc.push(acc[index - 1] + current)
                }
                return acc
            }, [])

            // Update the chart data dynamically
            setAdminChart(prevChart => ({
                ...prevChart,
                options: {
                    ...prevChart.options,
                    xaxis: {
                        ...prevChart.options.xaxis,
                        categories: categories, // Set dynamic categories
                    },
                },
                series: [
                    {
                        ...prevChart.series[0],
                        data: absoluteSales, // Set dynamic data for "Absolute" sales
                    },
                    {
                        ...prevChart.series[1],
                        data: salesPerMonth, // Set dynamic data for "per Month" sales
                    },
                ],
            }));
        }
    }, [data]);

    const customerGenders = {
        options: {
          legend: {
            show: true,
            position: 'bottom',
            horizontalAlign: 'center'
          },
          labels: ['Male', 'Female', 'Other'],
          responsive: [{
            breakpoint: 480,
            options: {
              chart: {
                width: 200
              },
              legend: {
                show: false
              }
            }
          }]
          }
    }

    const getGenderSeriesForChart=()=> {
        const genderOrder = ["MALE", "FEMALE", "OTHER"];
        const genderDistribution = data.genderDistribution;

        console.log(genderOrder.map(gender => genderDistribution[gender]))

        return genderOrder.map(gender => genderDistribution[gender]);
    }

    const customerRoles = {
        options: {
            legend: {
                show: true,
                position: 'bottom',
                horizontalAlign: 'center'
            },
            labels: ['Leader', 'Follower', 'Both'],
            responsive: [{
                breakpoint: 480,
                options: {
                    chart: {
                        width: 200
                    },
                    legend: {
                        show: false
                    }
                }
            }]
        },
        series: [46, 30, 24],
    }

    const getRoleSeriesForChart=()=> {
        const roleOrder = ["LEADER", "FOLLOWER", "BOTH"];
        const roleDistribution = data.roleDistribution;

        console.log(roleOrder.map(role => roleDistribution[role]))

        return roleOrder.map(role => roleDistribution[role]);
    }

    const getRandomColorVariant = () => {
        const strings = ["danger", "info", "warning", "primary", "success"];
        const randomIndex = Math.floor(Math.random() * strings.length);
        return strings[randomIndex];
    };

    function formatDate(dateString) {
        // Erzeuge ein neues Datum mit dem ersten Tag des Monats
        const [year, month] = dateString.split("-");
        const date = new Date(year, month - 1); // Monat ist nullbasiert

        // Formatiere das Datum zu "Month YY"
        const formattedDate = date.toLocaleDateString("en-US", {
            year: "2-digit",
            month: "long",
        });

        return formattedDate;
    }

    return(
        <>
        <div id='content-page' className='content-page'>
            <Container>
                <Row as="ul" className="list-unstyled mb-0">
                    <Col md="6" lg="3" as="li">
                        <Card>
                            <Card.Body>
                                <div className="points">
                                    <span>Sold tickets</span>
                                    <div className="d-flex align-items-center">
                                        <h3>{data && data.ticketSalesPerMonth ? getTotalTicketSales() : 0 }/{data && data.ticketTypesSoldFromContingent ? getTotalTickets() : 0 }</h3>
                                        <small className="text-success ms-3">+ {data != undefined && data.ticketSalesPerMonth != undefined ? Object.values(data.ticketSalesPerMonth).pop() : 0 } this month</small>
                                    </div>
                                </div>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col md="6" lg="3" as="li">
                        <Card>
                            <Card.Body>
                                <div className="points">
                                    <span>Page views</span>
                                    <div className="d-flex align-items-center">
                                        <h3>4,50,623</h3>
                                        <small className="text-danger ms-3">- 12,562</small>
                                    </div>
                                </div>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col md="6" lg="3" as="li">
                        <Card>
                            <Card.Body>
                                <div className="points">
                                    <span>Followers</span>
                                    <div className="d-flex align-items-center">
                                        <h3>16,502</h3>
                                        <small className="text-success ms-3">+ 1,056</small>
                                    </div>
                                </div>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col md="6" lg="3" as="li">
                        <Card>
                            <Card.Body>
                                <div className="points">
                                    <span>Last Month Comments</span>
                                    <div className="d-flex align-items-center">
                                        <h3>3,90,822</h3>
                                        <small className="text-success ms-3">+ 28,476</small>
                                    </div>
                                </div>
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
                <Row className="justify-content-md-center">
                    <Col lg="12">
                        <Card className="card-block card-stretch card-height">
                            <Card.Header>
                                <h4 className="card-title">Tickets sold</h4>
                                <Dropdown>
                                    <Dropdown.Toggle as={CustomToggle} variant="text-secondary">
                                    This year
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu className="dropdown-menu-end">
                                        <li><Dropdown.Item href="#">Year</Dropdown.Item></li>
                                        <li><Dropdown.Item href="#">Month</Dropdown.Item></li>
                                        <li><Dropdown.Item href="#">Week</Dropdown.Item></li>
                                    </Dropdown.Menu>
                                </Dropdown>
                            </Card.Header>
                            <Card.Body>
                                <Chart options={adminChart.options} series={adminChart.series} height="198"  />
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col lg="4">
                        <Card>
                            <Card.Header>
                                <div className="header-title">
                                    <h4 className="card-title">Genders</h4>
                                </div>
                            </Card.Header>
                            <Card.Body className="text-center">
                                <Chart options={customerGenders.options} className="col-md-8 col-lg-8" series={data.genderDistribution != undefined ? getGenderSeriesForChart() : []} width="290" type="pie"/>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col lg="4">
                        <Card>
                            <Card.Header>
                                <div className="header-title">
                                    <h4 className="card-title">Roles</h4>
                                </div>
                            </Card.Header>
                            <Card.Body className="text-center">
                                <Chart options={customerRoles.options} className="col-md-8 col-lg-8" series={data.roleDistribution != undefined ? getRoleSeriesForChart() : []}   width="290" type="pie"/>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col lg="4" md="6">
                        <Card>
                            <Card.Header>
                                <div className="header-title">
                                    <h4 className="card-title">Ticket types sold</h4>
                                </div>
                            </Card.Header>
                            <Card.Body>
                                {data.ticketTypesSoldFromContingent && data.ticketTypesSoldFromContingent.map((item, index) => (
                                    <div className="mb-3" key={index}>
                                        <div className="d-flex justify-content-between mt-2 text-dark">
                                            <h6>{item.first}</h6>
                                            <small>{item.second.first}/{item.second.second}</small>
                                        </div>
                                        <ProgressBar variant={getRandomColorVariant()} className="mt-2" now={(item.second.first/item.second.second)*100} style={{height: "6px"}}/>
                                    </div>
                                ))}
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
                <Row>
                    <Col>
                        <Card>
                            <Card.Body>
                                a
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Container>
            </div>
        </>
    )
} 

export default DanceAdmin