package ru.yushin.teleg.writers;



import ru.yushin.teleg.model.Message;

import java.io.FileWriter;
import java.io.IOException;

public class TxtWriter implements IWriter{
    private static final String SENDER = "Отправитель:[%s] ";
    private static final String SEND_TIME = "время:[%s] ";
    private static final String MESSAGE_BODY = "сообщение: %s";
    private static final String LOG_FILE_NAME = "log.txt";

    public void write(Message message){
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(LOG_FILE_NAME, true);
            fileWriter.write(String.format(SENDER, message.getUserName()));
            fileWriter.write(String.format(SEND_TIME, message.getTime()));
            fileWriter.write(String.format(MESSAGE_BODY, message.getValue()));
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
