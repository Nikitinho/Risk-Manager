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
    ],
    probability: [
        v => !!v || 'Probability is required',
        v => (v >= 0 &&  v <= 10) || 'Value must be between 0 and 10'
    ],
    impact: [
        v => !!v || 'Impact rate is required',
        v => (v >= 0 &&  v <= 10) || 'Value must be between 0 and 10'
    ],
    strategy: [
        v => !!v || 'Strategy is required'
    ]
}