import React from "react";

export default function (props) {
    const tweet = props.tweet
    console.log(tweet)
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
