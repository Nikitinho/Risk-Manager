import Vue from 'vue'

const cramm = Vue.resource('/cramm{/id}')

export default {
    add: cramm => risks.save({}, cramm)
}