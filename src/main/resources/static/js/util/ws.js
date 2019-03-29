import SockJS from 'sockjs-client'
import { Stomp } from '@stomp/stompjs'

let stompClient = null
const handlers = []


export function connect() {
    const socket = new SockJS('/gs-guide-websocket')
    stompClient = Stomp.over(socket)
    // Turn off debug mod
    stompClient.debug = () => {}
    stompClient.connect({}, frame => {
        stompClient.subscribe('/topic/activity', risk => {
            handlers.forEach(handler => handler(JSON.parse(risk.body)))
        })
    })
}

export function addHandler(handler) {
    handlers.push(handler)
}

export function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect()
    }
    console.log("Disconnected")
}

export function sendRisk(risk) {
    stompClient.send("/app/changeRisk", {}, JSON.stringify(risk))
}