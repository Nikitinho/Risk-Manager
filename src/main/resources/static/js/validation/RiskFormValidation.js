export default {
    title: [
        v => !!v || 'Title is required'
    ],
    description: [
        v => !!v || 'Description is required'
    ],
    category: [
        v => !!v || 'Category is required'
    ],
    causes: [
        v => !!v || 'Causes are required'
    ],
    consequences: [
        v => !!v || 'Consequences are required'
    ],
    responsible: [
        v => !!v || 'Responsible users are required'
    ],
    status: [
        v => !!v || 'Status is required'
    ]
}