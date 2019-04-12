package com.nikitinho.riskmanager.domain;

public enum BoardItemType {
    MESSAGE("Текстовое сообщение"),
    POLL("Опрос"),
    IMAGE("Изображение"),
    ATTACHMENT("Вложение");

    private String name;

    private BoardItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static BoardItemType fromString(String text) {
        for (BoardItemType boardItemType : BoardItemType.values()) {
            if (boardItemType.name.equalsIgnoreCase(text)) {
                return boardItemType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
