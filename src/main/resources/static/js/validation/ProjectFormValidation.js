export default {
    name: [
        v => !!v || 'Name is required'
    ],
    description: [
        v => !!v || 'Description is required'
    ],
    responsible: [
        v => (!!v && v.length > 0) || 'Responsible users are required'
    ]
}