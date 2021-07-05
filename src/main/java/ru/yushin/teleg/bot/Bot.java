package ru.yushin.teleg.bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.yushin.teleg.model.Message;
import ru.yushin.teleg.model.Names;
import ru.yushin.teleg.transfer.TransferExcel;
import ru.yushin.teleg.transfer.TransferMessagesService;

import java.io.File;
import java.io.IOException;


/**
 *
 * Бот слушает все чаты к которым присоединён. Эта опция включается в BotFather в настройках приватности бота.
 */
public class Bot extends TelegramLongPollingBot {

    static final String BOT_TOKEN = "1729676833:AAHqC72pHn6aQKNL4WRPO5f_TMpZrzK-JsQ";
    static final String BOT_NAME = "VigryzkaProd_bot";
    static final String ADMIN_CHAT_ID = "-592971739";

    TransferMessagesService transferMessagesServiceEXCEL;
    Message message;

    public void onUpdateReceived(Update update) {

        // получить id чата отправителя сообщения
        String chatIdReceivedUser = update.getMessage().getChat().getId().toString();

        // получим текст сообщения
        String input = Util.getMessage(update);

        // получим first + last name пользователя
        String userName = Util.getFirstAndLastNameReceiverMessage(update);

        // отправим в excel файл если это сообщение от монтажника
        if(input.contains("Установлено ПУ 1Т") || input.contains("Установлено ПУ 2Т")){
            message = new Message(userName, input);
            transferMessagesServiceEXCEL = new TransferMessagesService(message, new TransferExcel());
            transferMessagesServiceEXCEL.transferExcel();
        }

        commandToSendExcel(input, chatIdReceivedUser, userName);
    }

    /**
     *
     * @param input ждем определённый текст чтобы дать выгрузить
     * @param chatIdReceivedUser айди чата кому отправить
     */
    public void commandToSendExcel(String input, String chatIdReceivedUser, String userName){
        if(input.equalsIgnoreCase("выгрузить")){
                //todo посмотреть в api телеги как через http передавать файлы

                if(chatIdReceivedUser.equalsIgnoreCase("-592971739")){
                    Util.sendMessageInChat(String.format("Пользователю [%s] выгружается отчет.", userName), chatIdReceivedUser);
                    try {
                        Util.sendDocumentToUser(
                                chatIdReceivedUser,
                                new File("actual.xlsx")
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
        }
    }

    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }
}