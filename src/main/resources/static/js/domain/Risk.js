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
            this.category = risk.category
            this.causes = risk.causes
            this.consequences = risk.consequences
            this.responsible = risk.responsible
            this.status = risk.status
            this.stage = Risk.convertStage(risk.stage)
            this.probability = risk.probability
            this.impact = risk.impact
            this.riskRate = risk.riskRate
            this.riskLevel = Risk.convertRiskLevel(risk.riskLevel)
            this.strategy = risk.strategy
            this.actionStartDate = risk.actionStartDate ? risk.actionStartDate.substr(0, 10) : null
            this.actionEndDate = risk.actionEndDate ? risk.actionEndDate.substr(0, 10) : null
            this.strategyInfo = risk.strategyInfo
            this.actualActionEndDate = risk.actualActionEndDate ? risk.actualActionEndDate.substr(0, 10) : null
            this.comments = risk.comments || []
        } else {
            this.text = ''
            this.creationDate = ''
            this.description = ''
            this.category = null
            this.causes = []
            this.consequences = ''
            this.responsible = []
            this.status = null
            this.stage = null
            this.probability = ''
            this.impact = ''
            this.riskRate = ''
            this.riskLevel = null
            this.strategy = null
            this.actionStartDate = null
            this.actionEndDate = null
            this.strategyInfo = ''
            this.actualActionEndDate = null
            this.comments = []
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

    static convertStage(stage) {
        switch (stage) {
            case 'IDENTIFICATION':
                return 'Идентификация'
            case 'EVALUATION':
                return 'Оценка'
            case 'PLANNING':
                return 'Планирование'
            case 'MONITORING':
                return 'Мониторинг'
            default:
                return null
        }
    }

    static convertStageToStep(stage) {
        switch (stage) {
            case 'IDENTIFICATION':
                return 1
            case 'EVALUATION':
                return 2
            case 'PLANNING':
                return 3
            case 'MONITORING':
                return 4
            default:
                return 0
        }
    }

    static convertRiskRateToColor(level) {
        if (level === 0.0) {
            return 'grey'
        } else if (level > 0.0 && level < 0.34) {
            return 'green'
        } else if (level >= 0.34 && level < 0.68) {
            return 'yellow'
        } else if (level >= 0.68 && level <= 1.0) {
            return 'red'
        }
    }

    static convertRiskRateToLevel(rate) {
        if (rate >= 0.0 && rate < 0.34) {
            return 'Низкий'
        } else if (rate >= 0.34 && rate < 0.68) {
            return 'Средний'
        } else if (rate >= 0.68 && rate <= 1.0) {
            return 'Высокий'
        }
    }
}