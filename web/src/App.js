import React from 'react';
import './App.css';
import {Breadcrumb, Layout, Menu} from 'antd'

const {Header, Content, Footer} = Layout

function App() {
    return (
        <Layout className="layout">
            <Header>
                <div className="logo"/>
                <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['2']} style={{lineHeight: '64px'}}>
                    <Menu.Item key="1">Nav 1</Menu.Item>
                    <Menu.Item key="2">Nav 2</Menu.Item>
                    <Menu.Item key="3">Nav 3</Menu.Item>
                </Menu>
            </Header>
            <Content style={{padding: '0 50px'}}>
                <Breadcrumb style={{margin: '16px 0'}}>
                    <Breadcrumb.Item>Home</Breadcrumb.Item>
                    <Breadcrumb.Item>List</Breadcrumb.Item>
                    <Breadcrumb.Item>App</Breadcrumb.Item>
                </Breadcrumb>
                <div style={{background: '#fff', padding: 24, minHeight: 780}}>Content</div>
            </Content>
            <Footer style={{textAlign: 'center'}}>Powered by Ant Design ♥</Footer>
        </Layout>
    );
}

export default App;
