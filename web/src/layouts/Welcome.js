import React from 'react';
import {Button, Card, Col, Row} from "antd";
import logo from "../assets/logo.svg";
import '../assets/Welcome.css';

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
            <Col span={4}>
                <img src={logo} width="100" height="100" alt="this is a icon"/>
                <Card title="Find the most interesting things!">
                    <Button type="primary" className="Signup" onClick={handleSignUp}>Signup</Button>
                    <br/>
                    <Button block={true} className="Login" onClick={handleLogin}>Login</Button>
                </Card>
            </Col>
        </Row>
    )
}
