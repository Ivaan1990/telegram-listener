package ru.yushin.teleg.bot;

import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ru.yushin.teleg.bot.Bot.*;


/**
 * Всякие вспомогательные методы для Bot.class
 */
public class Util {

    /**
     *
     * @param update
     * @return текст последнего сообщения полученного ботом
     */
    static String getMessage(Update update){
        String message = update.getMessage().getText();
        return message == null ? "" : message;
    }

    /**
     *
     * @param text строка которую хотим отправить в какой либо чат
     * @param chatId айдишник чата куда хотим отправить text
     */
    public static void sendMessageInChat(String text, String chatId) {
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
        urlString = String.format(urlString, BOT_TOKEN, chatId, text);
        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            StringBuilder builder = new StringBuilder();
            InputStream inputStream = null;

            inputStream = new BufferedInputStream(connection.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine = "";
            while ((inputLine = bufferedReader.readLine()) != null) {
                builder.append(inputLine);
            }
            String response = builder.toString();
            System.out.println(response);
            System.out.println("\n");
        } catch (IOException ex){}
    }

    /**
     *
     * @param update
     * @return firstName и lastName пользователя отправившего сообщения в чат
     */
    static String getFirstAndLastNameReceiverMessage(Update update){
        return String.format("%s %s", update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getLastName());
    }

    /**
     *
     * @return текущее время
     */
    public static String getCurrentTime(){
        return new SimpleDateFormat("dd.MM.yyyy").format(new Date());
    }

    /**
     *
     * @param chatIdReceivedUser айди чата куда отправим actual.xlsx
     */
    public static void sendDocumentToUser(String chatIdReceivedUser) throws IOException {
        // получить file_id из log.txt
        String fileId = getFileIdFromLogFile();

        // получим этот файл с сервера телеги, и отправим его Александру
        getFileFromTelegramApiByFileId(chatIdReceivedUser, fileId);
    }

    /**
     *
     * @return file_id отчёта, который пока отправляется руками
     * @throws IOException
     */
    private static String getFileIdFromLogFile() throws IOException{
        String fileId;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("log.txt"));
        try {
            StringBuilder builder = new StringBuilder();
            String line = bufferedReader.readLine();

            while (line != null) {
                builder.append(line);
                line = bufferedReader.readLine();
            }
            fileId = builder.toString();
        } finally {
            bufferedReader.close();
        }

        String[] arr = fileId.split("время");
        return arr[arr.length-1].split("=")[1];
    }

    /**
     *
     * @param chatIdReceivedUser
     * @param fileId документа на сервере телеги, отправим его заказчику
     */
    private static void getFileFromTelegramApiByFileId(String chatIdReceivedUser, String fileId) {
        String urlString = String.format("https://api.telegram.org/bot%s/sendDocument?chat_id=%s&document=%s", BOT_TOKEN, chatIdReceivedUser, fileId);

        try {
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();
            StringBuilder builder = new StringBuilder();
            InputStream inputStream = null;

            inputStream = new BufferedInputStream(connection.getInputStream());

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine = "";
            while ((inputLine = bufferedReader.readLine()) != null) {
                builder.append(inputLine);
            }
            String response = builder.toString();
            System.out.println(response);
            System.out.println("\n");
        } catch (IOException ex){}
    }

}