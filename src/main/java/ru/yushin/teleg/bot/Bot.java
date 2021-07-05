package ru.yushin.teleg.bot;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.yushin.teleg.model.Message;
import ru.yushin.teleg.transfer.TransferExcel;
import ru.yushin.teleg.transfer.TransferMessagesService;


/**
 *
 * Бот слушает все чаты к которым присоединён. Эта опция включается в BotFather в настройках приватности бота.
 */
public class Bot extends TelegramLongPollingBot {

    static final String BOT_TOKEN = "1729676833:AAHqC72pHn6aQKNL4WRPO5f_TMpZrzK-JsQ";
    static final String BOT_NAME = "VigryzkaProd_bot";
    static final String ADMIN_CHAT_ID = "266119069";

    TransferMessagesService transferMessagesServiceTXT;
    TransferMessagesService transferMessagesServiceEXCEL;
    Message message;

    public String getBotUsername() {
        return BOT_NAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }

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

        doSomethingCommand(input, chatIdReceivedUser);
    }

    /**
     *
     * @param input текст сообщения
     * @param chatIdReceivedUser айди чата кому отправить
     */
    public void doSomethingCommand(String input, String chatIdReceivedUser){
        if(input.equals("/dump")){
            // даем выгрузить только определённым пользователям бота
            if(chatIdReceivedUser.equals(ADMIN_CHAT_ID)){
                System.out.println("Выгрузить файлы");
                Util.sendMessageInChat("Dumping...", ADMIN_CHAT_ID);

                Message dumpLogging = new Message(message.getUserName(), "!!! Выполнена команда /dump !!!");
                transferMessagesServiceTXT.transferInTextFile(dumpLogging);
            } else {
                System.err.println("Доступ к выгрузке Отсутствует!");
            }
        }
    }
}