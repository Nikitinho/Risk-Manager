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
            this.probability = risk.probability
            this.impact = risk.impact
            this.riskRate = risk.riskRate
            this.riskLevel = Risk.convertRiskLevel(risk.riskLevel)
            this.consequences = risk.consequences
            this.comments = risk.comments
        } else {
            this.text = ''
            this.creationDate = ''
            this.description = ''
            this.category = null
            this.causes = []
            this.consequences = ''
            this.responsible = []
            this.status = null
            this.probability = ''
            this.impact = ''
            this.riskRate = ''
            this.riskLevel = null
            this.comments = []
        }
    }

    // RiskStatusType
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

    // RiskCategoryType
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

    static convertRiskLevel(level) {
        switch (level) {
            case 'LOW':
                return 'Низкий'
            case 'MEDIUM':
                return 'Средний'
            case 'HIGH':
                return 'Высокий'
            default:
                return null
        }
    }

    static convertRiskRateToColor(level) {
        if (level >= 0 && level < 34) {
            return 'green'
        } else if (level >= 34 && level < 68) {
            return 'yellow'
        } else if (level >= 68 && level <= 100) {
            return 'red'
        }
    }

    static convertRiskRateToLevel(rate) {
        if (rate >= 0 && rate < 34) {
            return 'Низкий'
        } else if (rate >= 34 && rate < 68) {
            return 'Средний'
        } else if (rate >= 68 && rate <= 100) {
            return 'Высокий'
        }
    }
}