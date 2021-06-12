package ru.yushin.teleg.model;

public class ExcelModel {
    Message message;

    public ExcelModel(Message message) {
        this.message = message;
    }

    public String[] getValuesFromMessage() {
        return message.evaluateValue();
    }
}
