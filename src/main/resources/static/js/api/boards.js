import Vue from 'vue'

const boards = Vue.resource('/board{/id}')

export default {
    add: board => boards.save({}, board),
    update: board => boards.update({id: board.id}, board),
    remove: id => boards.remove({id}),
    get: id => boards.get({id})
}