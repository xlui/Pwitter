import React, {useState} from 'react';
import {Button, Card, Col, Form, Input, message, Row} from "antd";
import {postSignUp} from '../api/api'
import {home, welcome} from "./Const";
import '../assets/SignUp.less'

export default Form.create()(function (props) {
    const [confirmDirty, setConfirmDirty] = useState(false)
    const {getFieldDecorator} = props.form

    const handleConfirmBlur = e => {
        const {value} = e.target
        setConfirmDirty(confirmDirty || !!value)
    }

    /**
     * Validate that password is equals to confirm
     *
     * @param rule validate rule
     * @param value password value
     * @param callback success callback
     */
    const validateToNextPassword = (rule, value, callback) => {
        const {form} = props
        if (value && confirmDirty) {
            form.validateFields(['confirm'], {
                force: true
            })
        }
        callback()
    }

    /**
     * Validate that confirm password is equal to origin password
     * @param rule validate rule
     * @param value confirm password value
     * @param callback success callback
     */
    const compareToFirstPassword = (rule, value, callback) => {
        const {form} = props
        if (value && value !== form.getFieldValue('password')) {
            callback('Two passwords that you enter is inconsistent!')
        } else {
            callback()
        }
    }

    /**
     * handle submit signup
     *
     * @param e event
     */
    const handleSubmit = e => {
        e.preventDefault()
        props.form.validateFields(((errors, values) => {
            if (!errors) {
                postSignUp({
                    username: values.username,
                    password: values.password,
                    email: values.email,
                    nickname: values.nickname
                }).then(res => {
                    if (res.data.code === 0) {
                        message.success('Successfully register!')
                        props.history.push(home)
                    } else {
                        message.error(res.data.error)
                    }
                }).catch(error => {
                    console.log(`Meet error while trying register: ${error.response.data}`)
                    message.error(`Error! Server response: ${JSON.stringify(error.response.data.error)}`)
                })
            }
        }))
    }

    const goBack = e => {
        e.preventDefault();
        props.history.push(welcome)
    }

    return (
        <Row type="flex" justify="center" align="middle" className="SignUp-Form">
            <Col span={5}>
                <Card title="Join pwitter now!">
                    <Form labelCol={{span: 6}} wrapperCol={{span: 18}} labelAlign="left" onSubmit={handleSubmit}>
                        <Form.Item label="Username:">
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
                        <Form.Item label="E-mail:">
                            {
                                getFieldDecorator('email', {
                                    rules: [
                                        {
                                            type: 'email',
                                            message: 'The input is not a valid E-mail!'
                                        },
                                        {
                                            required: true,
                                            message: 'Please input your E-mail!'
                                        }
                                    ],
                                    getValueFromEvent: e => e.target.value.replace(/\s+/g, '')
                                })(<Input/>)
                            }
                        </Form.Item>
                        <Form.Item label="Password:" hasFeedback>
                            {
                                getFieldDecorator('password', {
                                    rules: [
                                        {
                                            required: true,
                                            message: 'Please input your password!'
                                        },
                                        {
                                            validator: validateToNextPassword
                                        }
                                    ],
                                    getValueFromEvent: e => e.target.value.replace(/\s+/g, '')
                                })(<Input.Password/>)
                            }
                        </Form.Item>
                        <Form.Item label="Confirm:" hasFeedback>
                            {
                                getFieldDecorator('confirm', {
                                    rules: [
                                        {
                                            required: true,
                                            message: 'Please confirm your password!'
                                        },
                                        {
                                            validator: compareToFirstPassword
                                        }
                                    ],
                                    getValueFromEvent: e => e.target.value.replace(/\s+/g, '')
                                })(<Input.Password onBlur={handleConfirmBlur}/>)
                            }
                        </Form.Item>
                        <Form.Item label="Nickname:">
                            {
                                getFieldDecorator('nickname', {
                                    rules: [
                                        {
                                            required: true,
                                            message: 'Please input your nickname!',
                                            whiteSpace: true
                                        }
                                    ],
                                    getValueFromEvent: e => e.target.value.replace(/\s+/g, '')
                                })(<Input/>)
                            }
                        </Form.Item>

                        <Row type="flex" justify="center" className="SignUp-Buttons">
                            <Button type="primary" className="SignUp-SignUp" htmlType="submit">Sign up</Button>
                            <Button className="SignUp-GoBack" onClick={goBack}>Go back</Button>
                        </Row>
                    </Form>
                </Card>
            </Col>
        </Row>
    )
})
