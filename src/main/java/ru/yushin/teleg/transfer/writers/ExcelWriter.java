package ru.yushin.teleg.transfer.writers;

import ru.yushin.teleg.eval.Evaluate;
import ru.yushin.teleg.transfer.model.Message;
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
            double valueToInsertDouble = 0;

            String cellName = line.split("-")[0].trim();
            try{

                valueToInsert = line.split("-")[1]
                        .trim()
                        .replaceAll(Evaluate.REG_EXP_PATTERN, "");
                if(valueToInsert.length() == 0) valueToInsert = "0";

                valueToInsertDouble = Double.parseDouble(valueToInsert);
            } catch (IndexOutOfBoundsException ex){
            //-- todo ужасный костыль, избавится

                if(line.contains("–")){

                    String[] arr = line.split("–");
                    if(arr.length > 1){
                        cellName = arr[0].trim();
                        valueToInsert = arr[1].replaceAll(Evaluate.REG_EXP_PATTERN, "");
                        valueToInsertDouble = Double.parseDouble(valueToInsert);

                        insertDataInCellByName(cellName, valueToInsertDouble);
                        //insertDataInCellByName(cellName, valueToInsert);
                        continue;
                    } else {
                        valueToInsert = "0";
                        valueToInsertDouble = Double.parseDouble(valueToInsert);
                        cellName = arr[0].trim();
                    }

                } else {
                    valueToInsert = "0";
                    valueToInsertDouble = Double.parseDouble(valueToInsert);
                }
            }//--

            insertDataInCellByName(cellName, valueToInsertDouble);
            //insertDataInCellByName(cellName, valueToInsert);
        }

        insertDataInCellByName("ВСЕГО ЗА ДЕНЬ УСТАНОВЛЕНО ПУ", getSumOfInstalledPY());
    }

}