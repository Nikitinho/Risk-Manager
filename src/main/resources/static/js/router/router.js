import Vue from 'vue'
import VueRouter from 'vue-router'
import RisksList from 'pages/RisksList.vue'
import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profie.vue'
import RiskCreation from 'pages/RiskCreation.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', name: 'RisksList', component: RisksList },
    { path: '/auth', name: 'GoogleAuth', component: Auth },
    { path: '/profile', name: 'Profile', component: Profile },
    { path: '/newRisk', component: RiskCreation },
    { path: '/newRisk/:riskId/:readonly', name: 'RiskCreation', component: RiskCreation, props: true },
    { path: '*', component: RisksList }
]

export default new VueRouter({
    mode: 'history',
    routes
})