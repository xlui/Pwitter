import axios from 'axios'

const host = `${process.env.NODE_ENV === 'production' ? 'https://pwitter.xlui.app' : 'http://localhost:8080'}`
const contentType = 'application/json;charset=utf-8'

export const postSignUp = params => {
    let uri = `${host}/register`
    return axios.post(uri, JSON.stringify(params), {
        headers: {
            'Content-Type': contentType
        }
    })
}