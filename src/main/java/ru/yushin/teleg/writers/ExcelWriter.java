package ru.yushin.teleg.writers;

import ru.yushin.teleg.model.ExcelModel;
import ru.yushin.teleg.model.Message;
import ru.yushin.teleg.bot.Util;

public class ExcelWriter extends ExcelEngine implements IWriter {
    Message message;
    ExcelModel excelModel;

    public void write(Message message) {
        this.message = message;
        excelModel = new ExcelModel(message);
        sendDataInExcelFile();
    }

    /**
     * Запись данных в эксель файл
     */
    private void sendDataInExcelFile(){
        String[] values = excelModel.getValuesFromMessage();

        // создадим строку перед заполнением
        createRowAndCells();

        insertDataInCellByName("ДАТА", Util.getCurrentTime());

        insertDataInCellByName("Монтажник", message.getUserName());

        insertDataInCellByName("Адрес", values[1].split("-")[1].trim());

        //парсим сообщение
        for(int i = 2; i < values.length; i++){
            String line = values[i];
            String valueToInsert = "";

            String cellName = line.split("-")[0].trim();
            try{

                valueToInsert = line.split("-")[1]
                        .trim()
                        .replaceAll("[^0-9\\\\+]", "");

            } catch (IndexOutOfBoundsException ex){
                valueToInsert = "0";
            }

            insertDataInCellByName(cellName, valueToInsert);
        }

        insertDataInCellByName("ВСЕГО ЗА ДЕНЬ УСТАНОВЛЕНО ПУ", getSumOfInstalledPY());
    }

}