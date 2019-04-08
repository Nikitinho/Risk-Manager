import Vue from 'vue'
import Vuex from 'vuex'
import risksApi from 'api/risks'
import projectApi from 'api/projects'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        projects: frontendData.projects,
        profile: frontendData.profile,
        categories: frontendData.categories,
        statuses: frontendData.statuses,
        users: frontendData.users
    },
    getters: {
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
        getProjectByRiskId: state => id => {
            return state.projects.find(project => project.risks.some(risk => Number(risk.id) === Number(id)))
        },
        getProjectById: state => id => {
            return state.projects.find(project => project.id === id)
        },
        getProfile: state => state.profile
    },
    mutations: {
        updateRiskMutation(state, risk) {
            // const updateIndex = state.projects.findIndex(item => Number(item.id) === Number(risk.project.id))
            // const project = state.projects[updateIndex]
            // const updateRiskIndex = project.risks.findIndex(item => Number(item.id) === Number(risk.id))
            //
            // state.projects = [
            //     ...state.projects.slice(0, updateIndex),
            //     {
            //         ...project,
            //         risks: [
            //             ...project.risks.slice(0, updateRiskIndex),
            //             risk,
            //             ...project.risks.slice(updateRiskIndex + 1)
            //         ]
            //     },
            //     ...state.projects.slice(updateIndex + 1)
            // ]
            let item = this.state.projects.find(project => project.risks.some(r => Number(r.id) === Number(risk.id)))
            let index = this.state.projects.findIndex(project => project.risks.some(r => Number(r.id) === Number(risk.id)))
            let riskIndex = item ? item.risks.findIndex(x => x.id = risk.id) : undefined
            if (riskIndex > -1) {
                item.risks.splice(riskIndex, 1, risk)
                state.projects.splice(index, 1, item)
            }
        },
        addRiskMutation(state, risk) {
            // console.log('addRiskMutation')
            // const updateIndex = state.projects.findIndex(item => item.id === risk.project.id)
            // const project = state.projects[updateIndex]
            // state.projects = [
            //     ...state.projects.slice(0, updateIndex),
            //     {
            //         ...project,
            //         risks: [
            //             ...project.risks,
            //             risk
            //         ]
            //     },
            //     ...state.projects.slice(updateIndex + 1)
            // ]


            let index = this.state.projects.findIndex(project => project.id === risk.project.id)
            let item = this.state.projects.find(project => project.id === risk.project.id)

            if (index > -1) {
                item.risks.splice(item.risks.length, 0, risk)
                state.projects.splice(index, 1, item)
            }
        },
        removeRiskMutation(state, risk) {
            // const deletionIndex = state.projects.findIndex(item => Number(item.id) === Number(risk.project.id))
            // const project = state.projects[deletionIndex]
            // const deletionRiskIndex = project.risks.findIndex(item => Number(item.id) === Number(risk.id))
            //
            // if (deletionIndex > -1) {
            //     state.projects = [
            //         ...state.projects.slice(0, deletionIndex),
            //         {
            //             ...project,
            //             risks: [
            //                 ...project.risks.slice(0, deletionRiskIndex),
            //                 ...project.risks.slice(deletionRiskIndex + 1)
            //             ]
            //         },
            //         ...state.projects.slice(deletionIndex + 1)
            //     ]
            // }
            let item = this.state.projects.find(project => project.risks.some(r => Number(r.id) === Number(risk.id)))
            let index = this.state.projects.findIndex(project => project.risks.some(r => Number(r.id) === Number(risk.id)))
            let riskIndex = item ? item.risks.findIndex(x => x.id = risk.id) : undefined
            if (riskIndex > -1) {
                item.risks.splice(riskIndex, 1)
                state.projects.splice(index, 1, item)
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
            await risksApi.add(risk)
        },
        async addRiskRefresh({commit, state}, risk) {
            const result = await risksApi.get(risk.id)
            const data = await result.json()
            commit('addRiskMutation', data)
        },
        async updateRiskAction({commit}, risk) {
             await risksApi.update(risk)
        },
        async updateRiskRefresh({commit, state}, risk) {
            const result = await risksApi.get(risk.id)
            const data = await result.json()
            commit('updateRiskMutation', data)
        },
        async removeRiskAction({commit}, risk) {
            await risksApi.remove(risk.id)
        },
        async removeRiskRefresh({commit, state}, risk) {
            commit('removeRiskMutation', risk)
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