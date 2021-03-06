package ru.yushin.teleg.bot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.yushin.teleg.transfer.model.Message;
import ru.yushin.teleg.transfer.TransferExcel;
import ru.yushin.teleg.transfer.TransferMessagesService;
import ru.yushin.teleg.transfer.TransferTxt;
import java.io.IOException;

public class Bot extends TelegramLongPollingBot {

    static final String BOT_TOKEN = "1729676833:AAHqC72pHn6aQKNL4WRPO5f_TMpZrzK-JsQ";
    static final String BOT_NAME = "VigryzkaProd_bot";
    static final String REPORT_CHAT = "-592971739";
    static final String ADMIN_CHAT = "-526991630";

    TransferMessagesService transferMessagesServiceEXCEL;
    TransferMessagesService transferMessagesServiceDataBase;
    Message message;

    public void onUpdateReceived(Update update) {
        // если отправлен документ, сохраним его file_id в log.txt
        checkForDocumentUpload(update);

        // получить id чата отправителя сообщения
        String chatIdReceivedUser = update.getMessage().getChat().getId().toString();

        // получим текст сообщения
        String input = Util.getMessage(update);

        // получим first + last name пользователя
        String userName = Util.getFirstAndLastNameReceiverMessage(update);

        // отправим в excel файл если это сообщение от монтажника
        if(input.contains("Установлено ПУ 1Т") || input.contains("Установлено ПУ 2Т")){
            message = new Message(userName, input);
            System.out.println(input);
            transferMessagesServiceEXCEL = new TransferMessagesService(message, new TransferExcel());
            transferMessagesServiceEXCEL.transferExcel();

            //transferMessagesServiceDataBase = new TransferMessagesService(message, new TransferDb());
            //transferMessagesServiceDataBase.transferDataBase();
        }
        commandToSendExcel(input, chatIdReceivedUser, userName);
    }

    /**
     * Проверим, что отправленный файл является отчетом.
     * @param update
     */
    private void checkForDocumentUpload(Update update){
        TransferMessagesService transferMessagesServiceLOG;
        Document document = update.getMessage().getDocument();
        if(document != null){
            if(document.getFileName().equalsIgnoreCase("actual.xlsx")){
                String fileId = document.getFileId();
                transferMessagesServiceLOG = new TransferMessagesService(
                        new Message(fileId),
                        new TransferTxt()
                );

                transferMessagesServiceLOG.transferInTextFile();
            }
        }
    }

    /**
     *
     * @param input команда для выгрузки
     * @param chatIdReceivedUser айди чата где сидит бот
     */
    private void commandToSendExcel(String input, String chatIdReceivedUser, String userName){
        if(input.equalsIgnoreCase("выгрузить")){

                if(chatIdReceivedUser.contains(REPORT_CHAT) || chatIdReceivedUser.contains(ADMIN_CHAT)){
                    Util.sendMessageInChat(String.format("Пользователю [%s] выгружается отчет.", userName), chatIdReceivedUser);

                    try {
                        Util.sendDocumentToUser(chatIdReceivedUser);
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