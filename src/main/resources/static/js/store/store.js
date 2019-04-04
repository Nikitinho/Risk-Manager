import Vue from 'vue'
import Vuex from 'vuex'
import risksApi from 'api/risks'
import crammApi from 'api/cramm'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        risks: frontendData.risks,
        profile: frontendData.profile,
        categories: frontendData.categories,
        statuses: frontendData.statuses,
        users: frontendData.users
    },
    getters: {
        sortedRisks: state => (state.risks || []).sort((a, b) => -(a.id - b.id)),
        riskCategories: state => state.categories,
        riskStatuses: state => state.statuses,
        activeUsers: state => state.users,
        getUserByEmail: state => name => {
            return state.users.find(user => user.email === name);
        },
        getRiskById: state => id => {
            return state.risks.find(risk => risk.id === id)
        },
        getProfile: state => state.profile
    },
    mutations: {
        addRiskMutation(state, risk) {
            state.risks = [
                ...state.risks,
                risk
            ]
        },
        updateRiskMutation(state, risk) {
            const updateIndex = state.risks.findIndex(item => item.id === risk.id)
            state.risks = [
                ...state.risks.slice(0, updateIndex),
                risk,
                ...state.risks.slice(updateIndex + 1)
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
        addCRAMMMutation(state, cramm) {
            const updateIndex = state.risks.findIndex(item => item.id === cramm.risk.id)
            const risk = state.risks[updateIndex]

            state.risks = [
                ...state.risks.slice(0, updateIndex),
                {
                    ...risk,
                    cramms: [
                        ...risk.cramms,
                        cramm
                    ]
                },
                ...state.risks.slice(updateIndex + 1)
            ]

        },
    },
    actions: {
        async addRiskAction({commit, state}, risk) {
            const result = await risksApi.add(risk)
            const data = await result.json()
            const index = state.risks.findIndex(item => item.id === data.id)

            if (index > -1) {
                commit('updateRiskMutation', data)
            } else {
                commit('addRiskMutation', data)
            }
        },
        async updateRiskAction({commit}, risk) {
            const result = await risksApi.update(risk)
            const data = await result.json()
            commit('updateRiskMutation', data)
        },
        async removeRiskAction({commit}, risk) {
            const result = await risksApi.remove(risk.id)
            if (result.ok) {
                commit('removeRiskMutation', risk)
            }
        },
        async addCRAMMAction({commit, state}, cramm) {
            const result = await crammApi.add(cramm)
            const data = await result.json()
            commit('addCRAMMMutation', cramm)
        }
    }
})