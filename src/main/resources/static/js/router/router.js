import Vue from 'vue'
import VueRouter from 'vue-router'
import RisksList from 'pages/RisksList.vue'
import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profie.vue'
import RiskCreation from 'pages/RiskCreation.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', component: RisksList },
    { path: '/auth', component: Auth },
    { path: '/profile', component: Profile },
    // { path: '/newRisk', component: RiskCreation },
    { path: '/newRisk/:riskId', name: 'RiskCreation', component: RiskCreation, props: true },
    { path: '*', component: RisksList }
]

export default new VueRouter({
    mode: 'history',
    routes
})