package ru.yushin.teleg.model;

public class Message {

    String userName;
    String time;
    String value;
    int row; // тут хранится номер строки в который будет производится запись

    public Message(String userName, String time, String value) {
        this.userName = userName;
        this.time = time;
        this.value = value;
    }

    /**
     * Конструктор для сервисных сообщений
     * @param userName
     * @param value сервисное сообщение
     */
    public Message(String userName, String value){
        this.userName = userName;
        this.value = value;
    }

    /**
     * Конструктор для excel
     * @param userName
     * @param value сервисное сообщение
     */
    public Message(String userName, String value, int row){
        this.userName = userName;
        this.value = value;
        this.row = row;
    }

    public String[] evaluateValue(){
        return value.split("\n");
    }

    public String getUserName() {
        return Names.getRealName(userName);
    }

    public String getTime() {
        return time;
    }

    public String getValue() {
        return value;
    }

    public int getRow() {
        return row;
    }
}
