import Vue from 'vue'
import VueRouter from 'vue-router'
import RisksList from 'pages/RisksList.vue'
import Auth from 'pages/Auth.vue'
import Profile from 'pages/Profie.vue'
import RiskCreation from 'pages/RiskCreation.vue'
import RiskView from 'pages/RiskView.vue'
import ProjectsList from 'pages/ProjectsList.vue'
import ProjectCreation from 'pages/ProjectCreation.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', name: 'RisksList', component: RisksList },
    { path: '/projects', name: 'ProjectsList', component: ProjectsList},
    { path: '/auth', name: 'GoogleAuth', component: Auth },
    { path: '/profile', name: 'Profile', component: Profile },
    { path: '/newRisk', name: 'RiskCreation', component: RiskCreation },
    { path: '/editRisk/:riskId', name: 'RiskEdition', component: RiskCreation, props: true },
    { path: '/viewRisk/:riskId', name: 'RiskView', component: RiskView, props: true },
    { path: '/newProject', name: 'ProjectCreation', component: ProjectCreation },
    { path: '*', component: RisksList }
]

export default new VueRouter({
    mode: 'history',
    routes
})