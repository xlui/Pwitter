import React, {useEffect, useState} from "react";
import {getTweets} from "../api/api";
import {Layout, message, Row} from "antd";
import dayjs from "dayjs";
import Tweet from "./Tweet";
import {date_format} from "./Const";
import "../assets/Home.less"

const {Header, Sider, Content} = Layout

export default function () {
    const [tweets, setTweets] = useState([])

    const loadTweets = () => {
        getTweets({
            from: dayjs().subtract(14, 'day').format(date_format),
            to: dayjs().add(300, 'day').format(date_format)
        }).then(res => {
            if (res.data.code === 0) {
                setTweets(res.data.data)
            } else {
                message.error(res.data.error)
            }
        }).catch(error => {
            console.log(`Meet error while fetching timeline: ${error.response}`)
            message.error(`Error! Server response: ${JSON.stringify(error)}`)
        })
    };

    useEffect(() => {
        loadTweets()
    }, [])

    return (
        <div className="Home-Main">
            <Layout>
                    <Header className="Home-Header">Header</Header>
                <Layout>
                    <Sider theme="light" style={{
                        maxWidth: 300,
                        width: '300px',
                        backgroundColor: 'red'
                    }}>Left Sider</Sider>
                    <Content>
                        <Row type="flex" justify="center">
                            <div>
                                {
                                    tweets.map((tweet) => <Tweet key={tweet.id} tweet={tweet}/>)
                                }
                            </div>
                        </Row>
                    </Content>
                    <Sider theme="light" className="Home-Sider">Right Sider</Sider>
                </Layout>
            </Layout>
        </div>
    )
}
