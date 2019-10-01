import React from "react";
import {Button, Card, Col, Form, Input, message, Row} from "antd";
import {home, welcome} from "./Const";
import '../assets/Login.less'
import {postLogin} from "../api/api";

export default Form.create()(function (props) {
    const {getFieldDecorator} = props.form

    const handleSubmit = e => {
        e.preventDefault()
        props.form.validateFields(((errors, values) => {
            if (!errors) {
                postLogin({
                    username: values.username,
                    password: values.password
                }).then(res => {
                    if (res.data.code === 0) {
                        localStorage.token = res.data.data
                        message.success("Successfully login!")
                        props.history.push(home)
                    } else {
                        message.error(res.data.error)
                    }
                }).catch(error => {
                    console.log(`Meet error while trying login: ${error.response.data}`)
                    message.error(`Error! Server response: ${JSON.stringify(error.response.data.error)}`)
                })
            }
        }))
    }

    const goBack = e => {
        e.preventDefault()
        props.history.push(welcome)
    }

    return (
        <Row type="flex" justify="center" align="middle" className="Login-Form">
            <Col span={5}>
                <Card title="Welcome to pwitter!">
                    <Form labelCol={{span: 6}} wrapperCol={{span: 18}} labelAlign="left" onSubmit={handleSubmit}>
                        <Form.Item label="Username">
                            {
                                getFieldDecorator('username', {
                                    rules: [
                                        {
                                            required: true,
                                            message: 'Please input your username!'
                                        },
                                    ],
                                    getValueFromEvent: e => e.target.value.replace(/\s+/g, '')
                                })(<Input/>)
                            }
                        </Form.Item>
                        <Form.Item label="Password">
                            {
                                getFieldDecorator('password', {
                                    rules: [
                                        {
                                            required: true,
                                            message: 'Please input your password!'
                                        }
                                    ],
                                    getValueFromEvent: e => e.target.value.replace(/\s+/g, '')
                                })(<Input.Password/>)
                            }
                        </Form.Item>
                        <Row type="flex" justify="center" className="Login-Buttons">
                            <Button type="primary" className="Login-Login" htmlType="submit">Login</Button>
                            <Button className="Login-GoBack" onClick={goBack}>Go back</Button>
                        </Row>
                    </Form>
                </Card>
            </Col>
        </Row>
    )
});
