package com.nikitinho.riskmanager.domain;

public enum RiskStageType {
    IDENTIFICATION("Идентификация"),
    EVALUATION("Оценка"),
    PLANNING("Планирование"),
    MONITORING("Мониторинг");

    private String name;

    private RiskStageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static RiskStageType fromString(String text) {
        for (RiskStageType stageType : RiskStageType.values()) {
            if (stageType.name.equalsIgnoreCase(text)) {
                return stageType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
