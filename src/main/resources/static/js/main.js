
var riskApi = Vue.resource('/risk{/id}')

Vue.component('risk-row', {
    props: ['risk'],
    template: '<div><i>({{ risk.id }})</i> {{ risk.text }}</div>'
});


Vue.component('risks-list', {
    props: ['risks'],
    template: '<div>' +
    '<risk-row v-for="risk in risks" :key="risk.id" :risk="risk"/>' +
    '</div>',
    created: function () {
        riskApi.get().then(result =>
            result.json().then(data =>
                data.forEach(risk => this.risks.push(risk))
            )
        )
    }
});

var app = new Vue({
    el: '#app',
    template: '<risks-list :risks="risks" />',
    data: {
        risks: [
            // { id: '123', text: 'Wow' },
            // { id: '213', text: 'More' },
            // { id: '312', text: 'Another' }
        ]
    }
});