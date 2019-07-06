import React, {useState} from 'react';
import {Button, Card, Col, Form, Input, Row} from "antd";
import {postSignUp} from '../api/api'

export default Form.create()(function (props) {
    const [confirmDirty, setConfirmDirty] = useState(false)
    const {getFieldDecorator} = props.form

    const handleConfirmBlur = e => {
        const {value} = e.target
        setConfirmDirty(confirmDirty || !!value)
    }

    const validateToNextPassword = (rule, value, callback) => {
        const {form} = props
        if (value && confirmDirty) {
            form.validateFields(['confirm'], {
                force: true
            })
        }
        callback()
    }

    const compareToFirstPassword = (rule, value, callback) => {
        const {form} = props
        if (value && value !== form.getFieldValue('password')) {
            callback('Two passwords that you enter is inconsistent!')
        } else {
            callback()
        }
    }

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
                        alert('Successfully register!')
                        props.history.push('/home')
                    } else {
                        alert(res.data.error)
                    }
                }).catch(error => {
                    console.log(`Meet error while trying register: ${error.response.data}`)
                    alert(`Error! Server response: ${JSON.stringify(error.response.data.error)}`)
                })
            }
        }))
    }

    return (
        <Row type="flex" justify="center" align="middle" style={{height: "100%"}}>
            <Col span={6}>
                <Card title="Join pwitter now!" style={{width: "auto"}}>
                    <Form labelCol={{span: 8}} wrapperCol={{span: 16}} labelAlign="left" onSubmit={handleSubmit}>
                        <Form.Item label="Username:">
                            {
                                getFieldDecorator('username', {
                                    rules: [
                                        {
                                            required: true,
                                            message: 'Please input your username!'
                                        },
                                    ],
                                    getValueFromEvent: e => {
                                        return e.target.value.replace(/\s+/g, '')
                                    }
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
                                    ]
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
                                    ]
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
                                    ]
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
                                    ]
                                })(<Input/>)
                            }
                        </Form.Item>
                        <Form.Item wrapperCol={{span: 16, offset: 8}}>
                            <Button type="primary" htmlType="submit">
                                Sign up
                            </Button>
                        </Form.Item>
                    </Form>
                </Card>
            </Col>
        </Row>
    )
})
