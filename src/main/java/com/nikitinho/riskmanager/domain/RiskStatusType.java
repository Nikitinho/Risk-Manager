package com.nikitinho.riskmanager.domain;

public enum RiskStatusType {
    CREATED("Открыт"),
    IN_THE_WORK("В работе"),
    CLOSED("Закрыт");

    private String description;

    RiskStatusType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static RiskStatusType fromString(String text) {
        for (RiskStatusType statusType : RiskStatusType.values()) {
            if (statusType.description.equalsIgnoreCase(text)) {
                return statusType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return description;
    }
}
