import React from 'react';
import '../assets/App.css';
import Welcome from './Welcome'
import SignUp from './SignUp'
import Login from './Login'
import {Layout} from 'antd'
import {BrowserRouter, Route, Switch} from 'react-router-dom'

const {Content, Footer} = Layout

export default function () {
    return (
        <Layout>
            <Content>
                <BrowserRouter>
                    <Switch>
                        <Route exact path="/" component={Welcome}/>
                        <Route exact path="/signup" component={SignUp}/>
                        <Route exact path="/login" component={Login}/>
                    </Switch>
                </BrowserRouter>
            </Content>
            <Footer>
                <hr/>
                <p>Powered by Ant Design ♥</p>
            </Footer>
        </Layout>
    );
}
