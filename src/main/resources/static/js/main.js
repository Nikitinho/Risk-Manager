import Vue from 'vue'
import Vuetify from 'vuetify'
import '@babel/polyfill'
import 'api/resource'
import router from 'router/router'
import App from 'pages/App.vue'
import store from 'store/store'
import { connect } from "./util/ws"
import 'vuetify/dist/vuetify.min.css'
import {i18n} from "./localization/i18n";

if (frontendData.profile) {
    connect()
}

Vue.use(Vuetify, {
    // theme inspiration https://pp.userapi.com/c854020/v854020741/26519/joJr-WKw1-o.jpg
    theme: {
        app: '#E3E7E8',
        drawer_title: '#19223E',
        drawer: '#222E49',
        toolbar: '#FFFFFF'
    }
})

Vue.config.productionTip = false

new Vue({
    el: "#app",
    i18n,
    store,
    router,
    render: a => a(App)
})