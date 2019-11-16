import React, {useEffect, useState} from "react";
import {getTweets} from "../api/api";
import {message, Row} from "antd";
import Tweet from "./Tweet";
import dayjs from "dayjs";
import {date_format} from "./Const";

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
        <Row type="flex" justify="center">
            <div>
                {
                    tweets.map((tweet) => <Tweet key={tweet.id} tweet={tweet}/>)
                }
            </div>
        </Row>
    )
}
