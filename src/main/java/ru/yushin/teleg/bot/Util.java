package ru.yushin.teleg.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        return update.getMessage().getText();
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
     * TODO
     * @param chatId айдишник чата
     * @param file файл который хотим отправить
     */
    public static void sendDocumentToUser(String chatId, File file) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(file);

        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(chatId);
        sendDocument.setDocument(new InputFile(fileInputStream, String.format("report [%s]", Util.getCurrentTime())));

        fileInputStream.close();
    }
}