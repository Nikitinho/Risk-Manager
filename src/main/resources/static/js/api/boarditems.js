import Vue from 'vue'

const boarditems = Vue.resource('/boarditem{/id}')

export default {
    add: boarditem => boarditems.save({}, boarditem),
    update: boarditem => boarditems.update({id: boarditem.id}, boarditem),
    remove: id => boarditems.remove({id}),
    get: id => boarditems.get({id})
}