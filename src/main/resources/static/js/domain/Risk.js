// Risk
// All fields coincide with .java class!
// All enums coincide with .java enums!
export default class Risk {

    constructor (risk) {
        if (risk) {
            this.id = risk.id
            this.text = risk.text
            this.creationDate = risk.creationDate
            this.description = risk.description
            this.category = Risk.convertCategory(risk.category)
            this.causes = risk.causes
            this.consequences = risk.consequences
            this.responsible = risk.responsible
            this.status = Risk.convertStatus(risk.status)
            this.cramms = risk.cramms
        }
    }

    static convertStatus(status) {
        switch (status) {
            case 'CREATED':
                return 'Открыт'
            case 'IN_THE_WORK':
                return 'В работе'
            case 'CLOSED':
                return 'Закрыт'
            default:
                return null
        }
    }

    static convertCategory(category) {
        switch (category) {
            case 'INTEGRATION':
                return 'Риск интеграции'
            case 'FINANCIAL':
                return 'Финансовые риски'
            case 'TEMPORARY':
                return 'Временные риски'
            case 'PERSONNEL':
                return 'Риски персонала'
            case 'COMMUNICATION':
                return 'Коммуникационные риски'
            case 'VENDOR':
                return 'Риски поставщиков'
            case 'LACK_OF_QUALITY':
                return 'Риски несоответствия качеству'
            default:
                return null
        }
    }
}