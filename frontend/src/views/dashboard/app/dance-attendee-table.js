import React, {useEffect, useState} from 'react'

// react-boostrap
import {
    Container,
    Col,
    Row,
    Dropdown,
    ProgressBar,
    Button
} from 'react-bootstrap'

// components
import Card from '../../../components/Card'

import {useKeycloak} from "@react-keycloak/web";
import {useParams} from "react-router-dom";
import {
    TICKETS_CLOSED_ENDPOINT,
    fetchData
} from "../../../components/util/network";
import {MaterialReactTable} from "material-react-table";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";
import * as XLSX from "xlsx";

const DanceAttendeeTable = () => {

    const { keycloak, initialized } = useKeycloak();

    const { id } = useParams();

    const [data, setData] = useState([]);

    const [tableData, setTableData] = useState([]);

    useEffect(() => {
        if(keycloak.authenticated) {
            fetchData(`${TICKETS_CLOSED_ENDPOINT}?eventId=${id}`, setData, keycloak.token);
        }
    }, [keycloak.authenticated]);

    useEffect(() => {
        console.log(data)
        if(data){
            setTableData(  data.map(item => ({
                name: `${item.ticket.firstName} ${item.ticket.lastName}`,
                address: item.ticket.address,
                country: item.ticket.country,
                email: item.ticket.email,
                phone: item.ticket.phone,
                gender: item.ticket.gender,
                dancingRole: item.ticket.dancingRole,
                buyDate: item.ticket.buyDate,
                ticketType: item.ticketType.name,
            })));
        }
    }, [data])

    const columns = [
        {
            accessorKey: 'name',
            header: 'Name',
        },
        {
            accessorKey: 'address',
            header: 'Address',
        },
        {
            accessorKey: 'country',
            header: 'Country',
        },
        {
            accessorKey: 'email',
            header: 'E-Mail',
        },
        {
            accessorKey: 'phone',
            header: 'Phone',
        },
        {
            accessorKey: 'gender',
            header: 'Gender',
        },
        {
            accessorKey: 'dancingRole',
            header: 'Role',
        },
        {
            accessorKey: 'buyDate',
            header: 'Buy date',
        },
        {
            accessorKey: 'ticketType',
            header: 'Ticket type',
        },
    ];

    const handleExportPDF = () => {
        const doc = new jsPDF();
        autoTable(doc, {
            head: [columns.map(col => col.header)],
            body: tableData.map(item => [
                item.name,
                item.address,
                item.country,
                item.email,
                item.phone,
                item.gender,
                item.dancingRole,
                item.buyDate,
                item.ticketType
            ]),
        });
        doc.save('table-data.pdf');
    };

    const handleExportExcel = () => {
        const worksheet = XLSX.utils.json_to_sheet(
            tableData.map(item => ({
                'Name': item.name,
                'Address': item.address,
                'Country': item.country,
                'Email': item.email,
                'Phone': item.phone,
                'Gender': item.gender,
                'Role': item.dancingRole,
                'Buy Date': item.buyDate,
                'Ticket Type': item.ticketType
        })));
        const workbook = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(workbook, worksheet, 'Sheet1');
        XLSX.writeFile(workbook, 'table_data.xlsx');
    };

    //TODO show only most relevant data by default und the details only when expanded

    return(
        <>
        <div id='content-page' className='content-page'>
            <Container>
                <Row>
                    <Col>
                        <Card>
                            <Card.Header className="d-flex justify-content-between flex-wrap">
                                <div className="header-title">
                                    <h4 className="card-title">Attendees for Bachatation</h4>
                                </div>
                                <div className="card-header-toolbar d-flex align-items-center mt-1 mt-md-0">
                                    <Button variant="link mr-3" onClick={handleExportPDF}><span className="d-flex align-items-center"><i className="material-symbols-outlined me-1">picture_as_pdf</i> Download as PDF</span></Button>
                                    <Button variant="link mr-3" onClick={handleExportExcel}><span className="d-flex align-items-center"><i className="material-symbols-outlined me-1">table_view</i> Download as Excel</span></Button>
                                </div>
                            </Card.Header>
                            <Card.Body>
                                <MaterialReactTable
                                    columns={columns}
                                    data={tableData}
                                    initialState={{
                                        columnVisibility: {
                                            address: false,
                                            country: false,
                                            phone: false,
                                            gender: false,
                                            dancingRole: false,
                                            buyDate: false
                                        },
                                        density: 'compact',
                                    }}
                                />
                            </Card.Body>
                        </Card>
                    </Col>
                </Row>
            </Container>
            </div>
        </>
    )
} 

export default DanceAttendeeTable