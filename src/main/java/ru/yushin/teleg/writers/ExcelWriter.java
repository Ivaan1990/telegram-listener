package ru.yushin.teleg.writers;

import ru.yushin.teleg.model.ExcelModel;
import ru.yushin.teleg.model.Message;

public class ExcelWriter extends ExcelEngine implements IWriter {
    Message message;
    ExcelModel excelModel;

    public void write(Message message) {
        this.message = message;
        excelModel = new ExcelModel(message);

        String[] values = excelModel.getValuesFromMessage();

        // создадим строку перед заполнением
        createRowAndCells();

        // напишем в excel настоящее имя монтажника вместо его ника в телеграме
        insertDataInCellByName("Монтажник", message.getUserName());

        // основные данные из тела сообщения
        insertDataInCellByName("ДАТА", message.getTime());
        insertDataInCellByName("ВСЕГО ЗА ДЕНЬ УСТАНОВЛЕНО ПУ", "0");

        for(String line : values){
            String cellName = line.split("-")[0].trim();

            String valueToInsert = "";
            try{
                valueToInsert = line.split("-")[1].trim();
            } catch (IndexOutOfBoundsException ex){
                valueToInsert = "0";
            }

            insertDataInCellByName(cellName, valueToInsert);
        }
    }

}