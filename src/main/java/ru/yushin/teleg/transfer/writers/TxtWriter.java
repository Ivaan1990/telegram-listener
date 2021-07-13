package ru.yushin.teleg.transfer.writers;
import ru.yushin.teleg.transfer.model.Message;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class TxtWriter implements IWriter{
    private static final String LOG_FILE_NAME = "log.txt";
    private static String TEXT_TO_WRITE = "время[%s]file_id=%s";

    public void write(Message message){
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(LOG_FILE_NAME, true);
            fileWriter.write(String.format(TEXT_TO_WRITE, new Date().toString(), message.getValue()));
            fileWriter.write("\n");
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
