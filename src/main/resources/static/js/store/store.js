import Vue from 'vue'
import Vuex from 'vuex'
import risksApi from 'api/risks'
import projectApi from 'api/projects'
import rcommentsApi from 'api/rcomments'
import boardsApi from 'api/boards'
import boarditemsApi from 'api/boarditems'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        projects: frontendData.projects,
        profile: frontendData.profile,
        categories: frontendData.categories,
        statuses: frontendData.statuses,
        strategies: frontendData.strategies,
        users: frontendData.users,
        boards: frontendData.boards
    },
    getters: {
        sortedProjects: state => (state.projects || []).sort((a, b) => -(a.id - b.id)),
        riskCategories: state => state.categories,
        actionStrategies: state => state.strategies,
        getBoards: state => state.boards,
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
        addRiskMutation(state, risk) {
            let index = this.state.projects.findIndex(project => project.id === risk.project.id)
            let item = this.state.projects.find(project => project.id === risk.project.id)

            if (index > -1) {
                if (!item.risks) { item.risks = [] }
                item.risks.splice(item.risks.length, 0, risk)
                state.projects.splice(index, 1, item)
            }
        },
        updateRiskMutation(state, risk) {
            let index = this.state.projects.findIndex(project => project.id === risk.project.id)
            let item = this.state.projects.find(project => project.id === risk.project.id)

            let riskIndex = item ? item.risks.findIndex(x => x.id = risk.id) : undefined
            if (riskIndex > -1) {
                item.risks.splice(riskIndex, 1, risk)
                state.projects.splice(index, 1, item)
            }
        },
        removeRiskMutation(state, risk) {
            let index = this.state.projects.findIndex(project => project.id === risk.project.id)
            let item = this.state.projects.find(project => project.id === risk.project.id)

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
        },
        async addRCommentAction({commit, state}, rcomment) {
            await rcommentsApi.add(rcomment)
        },
        async updateRCommentAction({commit}, rcomment) {
            await rcommentsApi.update(rcomment)
        },
        async removeRCommentAction({commit}, rcomment) {
            await rcommentsApi.remove(rcomment.id)
        },
        async addBoardAction({commit, state}, board) {
            await boardsApi.add(board)
        },
        async updateBoardAction({commit}, board) {
            await boardsApi.update(board)
        },
        async removeBoardAction({commit}, board) {
            await boardsApi.remove(board.id)
        },
        async addBoardItemAction({commit, state}, item) {
            await boarditemsApi.add(item)
        },
        async updateBoardItemAction({commit}, item) {
            await boarditemsApi.update(item)
        },
        async removeBoardItemAction({commit}, item) {
            await boarditemsApi.remove(item.id)
        }
    }
})