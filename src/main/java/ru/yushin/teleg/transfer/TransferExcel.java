package ru.yushin.teleg.transfer;


import ru.yushin.teleg.model.Message;
import ru.yushin.teleg.writers.ExcelWriter;
import ru.yushin.teleg.writers.IWriter;

/**
 * Класс отвечает за передачу данных в файл формата excel
 */
public class TransferExcel implements ITransfer{

    IWriter excelWriter;

    public void transfer(Message message){
        excelWriter = new ExcelWriter();
        excelWriter.write(message);
    }
}