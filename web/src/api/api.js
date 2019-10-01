import axios from 'axios'

const host = `${process.env.NODE_ENV === 'production' ? 'https://pwitter.xlui.app' : 'http://localhost:8080'}`
const contentType = 'application/json;charset=utf-8'

/**
 * Register to pwitter
 *
 * @param params request params
 * @returns {Promise<AxiosResponse<T>>} Axios Promise
 */
export const postSignUp = params => {
    let url = `${host}/register`
    return axios.post(url, JSON.stringify(params), {
        headers: {
            'Content-Type': contentType
        }
    })
}

export const postLogin = params => {
    let url = `${host}/login`
    return axios.post(url, JSON.stringify(params), {
        headers: {
            'Content-Type': contentType
        }
    })
}
