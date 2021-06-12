package ru.yushin.teleg.teleg;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import static ru.yushin.teleg.teleg.Bot.BOT_TOKEN;


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
        return update.getMessage().getText();
    }

    /**
     *
     * @param text строка которую хотим отправить в какой либо чат
     * @param chatId айдишник чата куда хотим отправить text
     */
   static public void sendMessageInChat(String text, String chatId) {
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
    static String getCurrentTime(){
        return new SimpleDateFormat("yyyy.MM.dd").format(new Date());
    }

    /**
     *
     * @param urlString адрес куда хотим отправить запрос
     * @throws IOException
     */
    public static void httpRequest(String urlString) throws IOException {
        urlString = "https://api.beget.com/api/mysql/getList?login=h9858890&passwd=scmFeTl5&output_format=json";
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
        /**
         * Как отправить запрос Telegram bot API?
         * https://coderoad.ru/31197659/%D0%9A%D0%B0%D0%BA-%D0%BE%D1%82%D0%BF%D1%80%D0%B0%D0%B2%D0%B8%D1%82%D1%8C-%D0%B7%D0%B0%D0%BF%D1%80%D0%BE%D1%81-Telegram-bot-API
         */
    }


}