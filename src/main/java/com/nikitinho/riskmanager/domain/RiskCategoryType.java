package com.nikitinho.riskmanager.domain;

public enum RiskCategoryType {
    INTEGRATION("Риск интеграции"),
    FINANCIAL("Финансовые риски"),
    TEMPORARY("Временные риски"),
    PERSONNEL("Риски персонала"),
    COMMUNICATION("Коммуникационные риски"),
    VENDOR("Риски поставщиков"),
    LACK_OF_QUALITY("Риски несоответствия качеству");

    private String name;

    private RiskCategoryType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static RiskCategoryType fromString(String text) {
        for (RiskCategoryType categoryType : RiskCategoryType.values()) {
            if (categoryType.name.equalsIgnoreCase(text)) {
                return categoryType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
