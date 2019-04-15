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
// import FlagIcon from 'vue-flag-icon'

if (frontendData.profile) {
    connect()
}

Vue.use(Vuetify)
// Vue.config.productionTip = false
// Vue.use(FlagIcon)

new Vue({
    el: "#app",
    i18n,
    store,
    router,
    render: a => a(App)
})