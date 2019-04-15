export default {
    name: [
        v => !!v || 'Название проекта - обязательное поле'
    ],
    description: [
        v => !!v || 'Описание проекта - обязательное поле'
    ],
    type:[
        v => !!v || 'Вид проекта - обязательное поле'
    ],
    managers: [
    ],
    analytics: [
    ],
    developers: [
    ],
    testers: [
    ],
    others: [
    ]
}