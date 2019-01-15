
var riskApi = Vue.resource('/risk{/id}')

function getIndex(list, id) {
    for (var i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}

Vue.component('risk-form', {
    props: ['risks', 'riskAttr'],
    data: function () {
        return {
            text: '',
            id: ''
        }
    },
    watch: {
        riskAttr: function (newVal, oldVal) {
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    template: '<div>' +
    '<input type="text" placeholder="Write something" v-model="text"/>' +
    '<input type="button" value="Save" @click="save"/>' +
    '</div>',
    methods: {
        save: function () {
            var risk = { text: this.text };

            if (this.id) {
                riskApi.update({id: this.id}, risk).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.risks, data.id);
                        this.risks.splice(index, 1, data)
                        this.text = ''
                        this.id = ''
                    })
                )
            } else {
                riskApi.save({}, risk).then(result =>
                    result.json().then(data => {
                        this.risks.push(data);
                        this.text = ''
                    })
                )
            }
        }
    }
});

Vue.component('risk-row', {
    props: ['risk', 'editMethod', 'risks'],
    template: '<div>' +
    '<i>({{ risk.id }})</i> {{ risk.text }}' +
    '<span style="position: absolute; right: 0">' +
        '<input type="button" value="Edit" @click="edit" />' +
    '<input type="button" value="X" @click="del" />' +
    '</span>' +
    '</div>',
    methods: {
        edit: function () {
            this.editMethod(this.risk)
        },
        del: function () {
            riskApi.remove({id: this.risk.id}).then(result => {
                if (result.ok) {
                    this.risks.splice(this.risks.indexOf(this.risk), 1)
                }
            })
        }
    }
});


Vue.component('risks-list', {
    props: ['risks'],
    data: function () {
        return {
            risk: null
        }
    },
    template: '<div>' +
    '<risk-form :risks="risks" :riskAttr="risk"/>' +
    '<risk-row v-for="risk in risks" :key="risk.id" :risk="risk" ' +
    ':editMethod="editMethod" :risks="risks"/>' +
    '</div>',
    methods: {
        editMethod: function (risk) {
            this.risk = risk
        }
    }
});

var app = new Vue({
    el: '#app',
    template:
    '<div>' +
      '<div v-if="!profile">Необходимо авторизоваться через <a href="/login">Google</a></div>' +
      '<div v-else>' +
        '<div>{{profile.name}}&nbsp;<a href="/logout">Выйти</a></div>' +
        '<risks-list :risks="risks" />' +
      '</div>' +
    '</div>',
    data: {
        risks: frontendData.risks,
        profile: frontendData.profile
    },
    created: function() {
        // riskApi.get().then(result =>
        //     result.json().then(data =>
        //         data.forEach(risk => this.risks.push(risk))
        //     )
        // )
    }
});