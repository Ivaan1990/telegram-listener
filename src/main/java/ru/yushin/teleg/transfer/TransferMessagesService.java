package ru.yushin.teleg.transfer;


import ru.yushin.teleg.model.Message;

/**
 * Сервис для передачи сообщений в сторонние ресурсы txt, html, excel...etc
 */
public class TransferMessagesService {
    Message message;
    ITransfer blankTransfer;

    public TransferMessagesService(Message message, ITransfer transfer) {
        this.message = message;
        blankTransfer = transfer;
    }

    public TransferMessagesService(ITransfer transfer){
        blankTransfer = transfer;
    }

    /**
     * Отправим конкретный текст сообщения в текстовый файл.
     */
    public void transferInTextFile(Message message){
        blankTransfer.transfer(message);
    }

    /**
     * Отправим текст сообщения в текстовый файл.
     */
    public void transferInTextFile(){
        blankTransfer.transfer(message);
    }

    /**
     * Отправим текст сообщения в excel файл
     */
    public void transferExcel(){
        blankTransfer.transfer(message);
    }
}
