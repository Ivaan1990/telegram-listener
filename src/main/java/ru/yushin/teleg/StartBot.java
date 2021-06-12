package ru.yushin.teleg;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.yushin.teleg.teleg.Bot;


public class StartBot {

    /**
     * https://www.youtube.com/watch?v=xv-FYOizUSY&t=221s ЛУЧШИЙ ВИДОС ПО TELEGRAM BOT API
     * @param args
     */

    public static void main(String[] args) {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            Bot bot = new Bot();
            BotSession botSession = botsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}

