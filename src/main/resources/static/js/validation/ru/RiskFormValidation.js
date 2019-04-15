export default {
    title: [
        v => !!v || 'Название риска - обязательное поле'
    ],
    description: [
        v => !!v || 'Описание риска - обязательное поле'
    ],
    category: [
        v => !!v || 'Категория риска - обязательное поле'
    ],
    causes: [
        v => !!v || 'Причины возникновения - обязательное поле'
    ],
    consequences: [
        v => !!v || 'Описание последствий - обязательное поле'
    ],
    responsible: [
        v => !!v || 'Ответственные пользователи - обязательное поле'
    ],
    status: [
        v => !!v || 'Статус - обязательное поле'
    ],
    probability: [
        v => !!v || 'Вероятность - обязательное поле',
        v => (v >= 0 &&  v <= 1) || 'Значение должно быть между 0 и 1'
    ],
    impact: [
        v => !!v || 'Влияние - обязательное поле',
        v => (v >= 0 &&  v <= 1) || 'Значение должно быть между 0 и 1'
    ],
    strategy: [
        v => !!v || 'Стратегия реагирования - обязательное поле'
    ],
    actionStartDate: [
        v => !!v || 'Дата начала - обязательное поле'
    ],
    actionEndDate: [
        v => !!v || 'Дата окончания - обязательное поле'
    ],
    strategyInfo: [
    ],
    actualActionEndDate: [
        v => !!v || 'Реальная дата окончания - обязательное поле'
    ]
}