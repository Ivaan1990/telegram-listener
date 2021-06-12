package ru.yushin.teleg.transfer;


import ru.yushin.teleg.model.Message;
import ru.yushin.teleg.writers.IWriter;
import ru.yushin.teleg.writers.TxtWriter;

/**
 * Класс отвечает за загрузку текста в файл формата .txt
 * Логи пишутся в messagesLogs.txt
 */
public class TransferTxt implements ITransfer{

    IWriter txtWriter;

    public void transfer(Message message) {
        txtWriter = new TxtWriter();
        txtWriter.write(message);
    }
}