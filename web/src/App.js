import React from 'react';
import './App.css';
import {Card, Col, Layout, Row} from 'antd'

const {Content, Footer} = Layout

function App() {
    return (
        <Layout className="layout" style={{height: "100%"}}>
            <Content style={{height: "100%", paddingBottom: 50}}>
                <Row type="flex" justify="center" align="middle" style={{height: "100%"}}>
                    <Col span={5}>
                        <img src="" alt="this is a icon"/>
                        {/* Icon */}
                        <Card title="Find the most interesting things on the internet!" style={{width: "auto"}}>
                            Register
                            <br/>
                            Login
                        </Card>
                    </Col>
                </Row>
            </Content>
            <Footer style={{
                height: 50,
                marginTop: -50,
                padding: 0,
                boxSizing: "border-box",
                textAlign: 'center'
            }}>
                <hr style={{margin: "0 15%", width: 'auto'}}/>
                <p style={{margin: 0, padding: "8px 0"}}>Powered by Ant Design ♥</p>
            </Footer>
        </Layout>
    );
}

export default App;
