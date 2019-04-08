import Vue from 'vue'
import Vuex from 'vuex'
import risksApi from 'api/risks'
import projectApi from 'api/projects'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        projects: frontendData.projects,
        risks: frontendData.risks,
        profile: frontendData.profile,
        categories: frontendData.categories,
        statuses: frontendData.statuses,
        users: frontendData.users
    },
    getters: {
        sortedRisks: state => (state.risks || []).sort((a, b) => -(a.id - b.id)),
        sortedProjects: state => (state.projects || []).sort((a, b) => -(a.id - b.id)),
        riskCategories: state => state.categories,
        riskStatuses: state => state.statuses,
        activeUsers: state => state.users,
        getUserByEmail: state => name => {
            return state.users.find(user => user.email === name);
        },
        getUserById: state => id => {
            return state.users.find(user => user.id === id);
        },
        getRiskById: state => id => {
            return state.risks.find(risk => risk.id === id)
        },
        getProjectById: state => id => {
            return state.projects.find(project => project.id === id)
        },
        getProfile: state => state.profile
    },
    mutations: {
        updateRiskMutation(state, risk) {
            const updateIndex = state.projects.findIndex(item => Number(item.id) === Number(risk.project.id))
            const project = state.projects[updateIndex]
            const updateRiskIndex = project.risks.findIndex(item => Number(item.id) === Number(risk.id))

            state.projects = [
                ...state.projects.slice(0, updateIndex),
                {
                    ...project,
                    risks: [
                        ...project.risks.slice(0, updateRiskIndex),
                        risk,
                        ...project.risks.slice(updateRiskIndex + 1)
                    ]
                },
                ...state.projects.slice(updateIndex + 1)
            ]
        },
        addRiskMutation(state, risk) {
            const updateIndex = state.projects.findIndex(item => item.id === risk.project.id)
            const project = state.projects[updateIndex]
            state.projects = [
                ...state.projects.slice(0, updateIndex),
                {
                    ...project,
                    risks: [
                        ...project.risks,
                        risk
                    ]
                },
                ...state.projects.slice(updateIndex + 1)
            ]

        },
        removeRiskMutation(state, risk) {
            const deletionIndex = state.risks.findIndex(item => item.id === risk.id)

            if (deletionIndex > -1) {
                state.risks = [
                    ...state.risks.slice(0, deletionIndex),
                    ...state.risks.slice(deletionIndex + 1)
                ]
            }
        },
        addProjectMutation(state, project) {
            state.projects = [
                ...state.projects,
                project
            ]
        },
        updateProjectMutation(state, project) {
            const updateIndex = state.projects.findIndex(item => item.id === project.id)
            state.projects = [
                ...state.projects.slice(0, updateIndex),
                project,
                ...state.projects.slice(updateIndex + 1)
            ]
        },
        removeProjectMutation(state, project) {
            const deletionIndex = state.projects.findIndex(item => item.id === project.id)

            if (deletionIndex > -1) {
                state.projects = [
                    ...state.projects.slice(0, deletionIndex),
                    ...state.projects.slice(deletionIndex + 1)
                ]
            }
        },
    },
    actions: {
        async addRiskAction({commit, state}, risk) {
            const result = await risksApi.add(risk)
            const data = await result.json()
            commit('addRiskMutation', risk)
        },
        async updateRiskAction({commit}, risk) {
            const result = await risksApi.update(risk)
            const data = await result.json()
            commit('updateRiskMutation', risk)
        },
        async removeRiskAction({commit}, risk) {
            const result = await risksApi.remove(risk.id)
            if (result.ok) {
                commit('removeRiskMutation', risk)
            }
        },
        async addProjectAction({commit, state}, project) {
            const result = await projectApi.add(project)
            const data = await result.json()
            const index = state.projects.findIndex(item => item.id === data.id)

            if (index > -1) {
                commit('updateProjectMutation', data)
            } else {
                commit('addProjectMutation', data)
            }
        },
        async updateProjectAction({commit}, project) {
            const result = await projectApi.update(project)
            const data = await result.json()
            commit('updateProjectMutation', data)
        },
        async removeProjectAction({commit}, project) {
            const result = await projectApi.remove(project.id)
            if (result.ok) {
                commit('removeProjectMutation', project)
            }
        }
    }
})