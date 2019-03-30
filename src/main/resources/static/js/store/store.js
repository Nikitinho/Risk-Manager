import Vue from 'vue'
import Vuex from 'vuex'
import risksApi from 'api/risks'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        risks: frontendData.risks,
        profile: frontendData.profile,
        categories: frontendData.categories
    },
    getters: {
        sortedRisks: state => (state.risks || []).sort((a, b) => -(a.id - b.id)),
        riskCategories: state => state.categories
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
        }
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
        }
    }
})