import Vue from 'vue'

const rcomments = Vue.resource('/rcomment{/id}')

export default {
    add: rcomment => rcomments.save({}, rcomment),
    update: rcomment => rcomments.update({id: rcomment.id}, rcomment),
    remove: id => rcomments.remove({id}),
    get: id => rcomments.get({id})
}