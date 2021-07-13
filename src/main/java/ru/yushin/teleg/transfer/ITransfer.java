package ru.yushin.teleg.transfer;


import ru.yushin.teleg.transfer.model.Message;

public interface ITransfer {
    /**
     * интерфейс для передачи данных, например в txt, docx, excel, html
     * @param message сообщение
     */
    void transfer(Message message);
}
