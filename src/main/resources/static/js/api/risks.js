import Vue from 'vue'

const risks = Vue.resource('/risk{/id}')

export default {
    add: risk => risks.save({}, risk),
    update: risk => risks.update({id: risk.id}, risk),
    remove: id => risks.remove({id})
}