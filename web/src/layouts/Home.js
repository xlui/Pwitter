import React, {useState} from "react";
import {getTweets} from "../api/api";
import {message, Row} from "antd";
import Tweet from "./Tweet";

export default function () {
    const [tweets, setTweets] = useState([])

    const loadTweets = () => {
        getTweets({
            from: "2019-09-01",
            to: "2020-09-01"
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

    loadTweets()
    const tweetsRender = tweets.map((tweet) => <Tweet key={tweet.id} tweet={tweet}/>)
    return (
        <Row type="flex" justify="center">
            <div>{tweetsRender}</div>
        </Row>
    )
}
