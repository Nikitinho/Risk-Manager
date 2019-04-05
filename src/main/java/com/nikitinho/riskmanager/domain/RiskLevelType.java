package com.nikitinho.riskmanager.domain;

public enum RiskLevelType {
    LOW("Низкий"),
    MEDIUM("Средний"),
    HIGH("Высокий");

    private String name;

    private RiskLevelType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static RiskLevelType fromString(String text) {
        for (RiskLevelType levelType : RiskLevelType.values()) {
            if (levelType.name.equalsIgnoreCase(text)) {
                return levelType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
