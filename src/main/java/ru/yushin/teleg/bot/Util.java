package ru.yushin.teleg.bot;

import org.telegram.telegrambots.meta.api.objects.Update;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ru.yushin.teleg.bot.Bot.ADMIN_CHAT;
import static ru.yushin.teleg.bot.Bot.BOT_TOKEN;


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
       /**
        * Как отправить запрос Telegram bot API?
        * https://coderoad.ru/31197659/%D0%9A%D0%B0%D0%BA-%D0%BE%D1%82%D0%BF%D1%80%D0%B0%D0%B2%D0%B8%D1%82%D1%8C-%D0%B7%D0%B0%D0%BF%D1%80%D0%BE%D1%81-Telegram-bot-API
        */
        String urlString = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";
        urlString = String.format(urlString, BOT_TOKEN, chatId, text);
        try {
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            StringBuilder sb = new StringBuilder();
            InputStream is = null;

            is = new BufferedInputStream(conn.getInputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            String response = sb.toString();
            System.out.println(response);
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
     * TODO отправим excel файл
     */
    public static void sendDocumentToUser() throws IOException {
        // получить file_id из log.txt
        String fileId = getFileIdFromLogFile();

        // получим этот файл с сервера телеги, и отправим его Александру
        getFileFromTelegramApiByFileId(fileId);
    }

    /**
     *
     * @return file_id отчёта, который пока отправляется руками
     * @throws IOException
     */
    private static String getFileIdFromLogFile() throws IOException{
        String fileId;
        BufferedReader br = new BufferedReader(new FileReader("log.txt"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            fileId = sb.toString();
        } finally {
            br.close();
        }
        return fileId.split("=")[1];
    }

    /**
     *
     * @param fileId документа на сервере телеги, отправим его заказчику
     */
    private static void getFileFromTelegramApiByFileId(String fileId) {

        String urlString = String.format("https://api.telegram.org/bot%s/sendDocument?chat_id=%s&document=%s", BOT_TOKEN, ADMIN_CHAT, fileId);
        try{
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            StringBuilder sb = new StringBuilder();
            InputStream is = null;

            is = new BufferedInputStream(conn.getInputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String inputLine = "";
            while ((inputLine = br.readLine()) != null) {
                sb.append(inputLine);
            }
            String response = sb.toString();
            System.out.println(response);
        } catch (IOException ex){}
    }

}