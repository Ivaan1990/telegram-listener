package ru.yushin.teleg.writers;

import ru.yushin.teleg.eval.Evaluate;
import ru.yushin.teleg.model.Message;
import ru.yushin.teleg.bot.Util;

public class ExcelWriter extends ExcelEngine implements IWriter {
    Message message;

    public void write(Message message) {
        this.message = message;
        sendDataInExcelFile();
    }

    /**
     * Запись данных в эксель файл
     */
    private void sendDataInExcelFile(){
        Evaluate evaluator = new Evaluate(message.evaluateValue());

        // создадим строку перед заполнением
        createRowAndCells();

        insertDataInCellByName("ДАТА", Util.getCurrentTime());

        insertDataInCellByName("Монтажник", message.getUserName());

        insertDataInCellByName("Адрес", evaluator.getAddress());

        //парсим сообщение
        for(int i = 2; i < evaluator.getValues().length; i++){
            String line = evaluator.getValues()[i];
            String valueToInsert = "";

            String cellName = line.split("-")[0].trim();
            try{

                valueToInsert = line.split("-")[1]
                        .trim()
                        .replaceAll("[^0-9\\\\+]", "");
                if(valueToInsert.length() == 0) valueToInsert = "0";

            } catch (IndexOutOfBoundsException ex){
                if(line.contains("–")){
                    insertDataInCellByName("Установлено ПУ 3Ф 3Т", evaluator.eval3F3T(line));
                    continue;
                } else {
                    valueToInsert = "0";
                }
            }

            insertDataInCellByName(cellName, valueToInsert);
        }

        insertDataInCellByName("ВСЕГО ЗА ДЕНЬ УСТАНОВЛЕНО ПУ", getSumOfInstalledPY());
    }

}