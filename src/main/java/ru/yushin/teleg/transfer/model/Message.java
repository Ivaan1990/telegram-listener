package ru.yushin.teleg.transfer.model;

public class Message {

    String userName;
    String time;
    String value;

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
     * Конструктор для сохранения file_id отправленного файла в log.txt
     * @param value
     */
    public Message(String value) {
        this.value = value;
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

}