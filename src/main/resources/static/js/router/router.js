import Vue from 'vue'
import VueRouter from 'vue-router'
import RisksList from 'pages/RisksList.vue'
import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profie.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: RisksList },
    { path: '/auth', component: Auth },
    { path: '/profile', component: Profile },
    { path: '*', component: RisksList }
]

export default new VueRouter({
    mode: 'history',
    routes
})