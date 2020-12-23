import React from 'react';
import './App.css';
import {useTable} from 'react-table';
import {Col, Container, Row} from "react-bootstrap";

function App() {

    const lambdaData = require('./testTableData.json');

    const epColumns = [
        {
            Header: "Expected Points",
            columns: [
                {
                    Header: "Yard Line",
                    accessor: "yardline"
                },
                {
                    Header: "Team 1",
                    accessor: "points1",
                    Cell: ({cell}) => {
                        const {value} = cell;

                        return(
                            <div>
                                {expectedPointsFormat(value)}
                            </div>
                        )
                    }
                },
                {
                    Header: "Team 2",
                    accessor: "points2",
                    Cell: ({cell}) => {
                        const {value} = cell;

                        return(
                            <div>
                                {expectedPointsFormat(value)}
                            </div>
                        )
                    }
                }
            ]
        }
    ];

    function expectedPointsFormat(numberString) {
        let number = parseFloat(numberString);
        if (number >= 0) {
            number = "+" + number.toFixed(2).toString();
        }
        return number;
    }

    const Table = ({ columns, data }) => {
        const {
            getTableProps,
            getTableBodyProps,
            headerGroups,
            rows,
            prepareRow
        } = useTable({
            columns,
            data
        });

        return (
            <table {...getTableProps()}>
                <thead>
                {headerGroups.map(headerGroup => (
                    <tr {...headerGroup.getHeaderGroupProps()}>
                        {headerGroup.headers.map(column => (
                            <th {...column.getHeaderProps()}>{column.render("Header")}</th>
                        ))}
                    </tr>
                ))}
                </thead>
                <tbody {...getTableBodyProps()}>
                {rows.map((row, i) => {
                    prepareRow(row);
                    return (
                        <tr {...row.getRowProps()}>
                            {row.cells.map(cell => {
                                return <td {...cell.getCellProps()}>{cell.render("Cell")}</td>;
                            })}
                        </tr>
                    );
                })}
                </tbody>
            </table>
        );
    };

    return (
        // <div className="App">
        //     <header className="App-header">
                <Container fluid>
                    <Row>
                        <span className="block-example border border-dark">
                            <Col xs={3}>
                                <Table columns={epColumns} data={lambdaData} />
                            </Col>
                        </span>
                        <Col xs={5}>
                            <Row>
                                Team Data Input Section
                            </Row>
                            <Row>
                                4th Down Calculator Section
                            </Row>
                        </Col>
                    </Row>
                </Container>
        //     </header>
        // </div>
    );
}

export default App;
