package ru.yushin.teleg.writers;
import ru.yushin.teleg.model.Message;
import java.io.FileWriter;
import java.io.IOException;

public class TxtWriter implements IWriter{
    private static final String LOG_FILE_NAME = "log.txt";

    public void write(Message message){
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(LOG_FILE_NAME, false);
            fileWriter.write(message.getValue());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
