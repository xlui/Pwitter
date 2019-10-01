import React from "react";

export default function (props) {
    const tweet = props.tweet
    return (
        <div>
            id: {tweet.id}
            <br/>
            content: {tweet.content}
            <br/>
            username: {tweet.user.username}
            <hr/>
        </div>
    )
}