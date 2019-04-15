export default {
    name: [
        v => !!v || 'Name is required'
    ],
    description: [
        v => !!v || 'Description is required'
    ],
    type:[
        v => !!v || 'Type is required'
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