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
    asset: [
        v => !!v || 'Asset is required'
    ],
    assetRate: [
        v => !!v || 'Asset rate is required',
        v => (v >= 0 &&  v <= 10) || 'Value must be between 0 and 10'
    ],
    threat: [
        v => !!v || 'Threat is required'
    ],
    vulnerability: [
        v => !!v || 'Vulnerability is required'
    ],
    vulnerabilityRate: [
        v => !!v || 'Vulnerability rate is required',
        v => (v >= 0 &&  v <= 10) || 'Value must be between 0 and 10'
    ]
}