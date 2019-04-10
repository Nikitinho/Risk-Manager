package com.nikitinho.riskmanager.domain;

public enum RiskActionStrategyType {
    INVESTIGATION("Исследование"),
    ACCEPTANCE("Принятие"),
    AVOIDANCE("Избежание"),
    TRANSMISSION("Передача"),
    PREVENTION("Предотвращение"),
    MINIMIZATION("Минимизация");

    private String name;

    private RiskActionStrategyType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static RiskActionStrategyType fromString(String text) {
        for (RiskActionStrategyType strategyType : RiskActionStrategyType.values()) {
            if (strategyType.name.equalsIgnoreCase(text)) {
                return strategyType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}