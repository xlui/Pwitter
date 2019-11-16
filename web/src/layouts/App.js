import React from 'react';
import '../assets/App.css';
import Welcome from './Welcome'
import SignUp from './SignUp'
import Login from './Login'
import Home from './Home'
import {Layout} from 'antd'
import {BrowserRouter, Route, Switch} from 'react-router-dom'
import {home, login, signup, welcome} from "./Const";

const {Content, Footer} = Layout

export default function () {
    return (
        <Layout>
            <Content>
                <BrowserRouter>
                    <Switch>
                        <Route exact path={welcome} component={Welcome}/>
                        <Route exact path={signup} component={SignUp}/>
                        <Route exact path={login} component={Login}/>
                        <Route exact path={home} component={Home}/>
                    </Switch>
                </BrowserRouter>
            </Content>
            <Footer>
                <hr/>
                <p>
                    Powered by Ant Design ♥ <a
                    href="https://github.com/xlui"
                    target="_blank"
                    rel="noopener noreferrer">xlui</a>
                </p>
            </Footer>
        </Layout>
    );
}
