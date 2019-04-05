import Vue from 'vue'

const projects = Vue.resource('/project{/id}')

export default {
    add: project => projects.save({}, project),
    update: project => projects.update({id: project.id}, project),
    remove: project => projects.remove({id})
}