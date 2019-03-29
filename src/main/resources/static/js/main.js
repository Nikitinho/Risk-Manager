import Vue from 'vue'
import App from 'pages/App.vue'
import VueResource from 'vue-resource'
import { connect } from "./util/ws"
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'

if (frontendData.profile) {
    connect()
}

Vue.use(Vuetify)
Vue.use(VueResource)

new Vue({
    el: "#app",
    render: a => a(App)
})