import React from 'react';
import {Button, Card, Col, Row} from "antd";
import logo from "./logo.svg";

export default function (props) {
    function handleSignUp(e) {
        e.preventDefault()
        props.history.push('/signup')
    }

    function handleLogin(e) {
        e.preventDefault()
        props.history.push('/login')
    }

    return (
        <Row type="flex" justify="center" align="middle" style={{height: "100%"}}>
            <Col span={6}>
                <img src={logo} width="50" height="50" alt="this is a icon"/>
                <Card title="Find the most interesting things!" style={{width: "auto"}}>
                    <Button type="primary" block={true} style={{margin: "0px 5px 5px 5px"}}
                            onClick={handleSignUp}>Signup</Button>
                    <br/>
                    <Button block={true} style={{margin: "5px 5px 0px 5px"}}
                            onClick={handleLogin}>Login</Button>
                </Card>
            </Col>
        </Row>
    )
}
