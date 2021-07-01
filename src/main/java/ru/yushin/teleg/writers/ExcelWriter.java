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

        // напишем в excel настоящее имя монтажника вместо его ника в телеграме
        insertDataInCellByName("Монтажник", message.getUserName());

        // обработка ошибки ДАТА
        int j = zaglushka(message.getUserName());

        //парсим сообщение
        for(int i = j; i < values.length; i++){
            String line = values[i];
            String valueToInsert = "";

            String cellName = line.split("-")[0].trim();
            try{
                valueToInsert = line.split("-")[1].trim();
            } catch (IndexOutOfBoundsException ex){
                valueToInsert = "0";
            }

            insertDataInCellByName(cellName, valueToInsert);
        }
    }

    /**
     *
     * @param userName принимает имя отправителя сообщения
     * @return возвращает число с какой по счету строки начинать отсчет
     * сделано против сообщений с неверным формитированием строки с датой, пример: ДАТА 01 0721 Кулаков
     */
    private int zaglushka(String userName){
        if(userName.equalsIgnoreCase("Андрей Кулаков") || userName.equalsIgnoreCase("Алексей Лазарев")){
            insertDataInCellByName("ДАТА", Util.getCurrentTime());
            return 1;
        }
        return 0;
    }

}