package ru.yushin.teleg.writers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.yushin.teleg.model.ExcelModel;
import ru.yushin.teleg.model.Message;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExcelWriter implements IWriter {

    static final String FILE_NAME = "table.xlsx";
    static int COUNT_ROW = 1;

    Message message; // сообщение из телеграм чата
    ExcelModel excelModel;

    static Map<String, Integer> columsNames;

    static {
        columsNames = new HashMap<String, Integer>();
        columsNames.put("Дата", 0);
        columsNames.put("Адрес", 1);
        columsNames.put("Монтажник", 2);
        columsNames.put("Установлено ПУ 1Т", 3);
        columsNames.put("Установлено ПУ 2Т", 4);
        columsNames.put("Установлено ПУ 3Т", 5);
        columsNames.put("ВСЕГО ЗА ДЕНЬ УСТАНОВЛЕНО ПУ", 6);
        columsNames.put("Отказы", 7);
        columsNames.put("Брак ПУ", 8);
        columsNames.put("Брак ПЛ", 9);
        columsNames.put("Остаток ПУ 1Т", 10);
        columsNames.put("Остаток ПУ 2Т", 11);
        columsNames.put("Остаток ПУ 3Т", 12);
        columsNames.put("Остаток ПЛ", 13);
    }

    public void write(Message message) {
        this.message = message;
        excelModel = new ExcelModel(message);

        String[] values = excelModel.getValuesFromMessage();

        createRowAndCells();

        // данные берущиеся не из тело сообщения
        insterDataInCellByName("Дата", message.getTime());
        insterDataInCellByName("Монтажник", message.getUserName());
        insterDataInCellByName("ВСЕГО ЗА ДЕНЬ УСТАНОВЛЕНО ПУ", getTotalInstalled_ПУ(values));

        // основные данные из тела сообщения
        for(String line : values){
            String cellName = line.split("-")[0].trim();
            String valueToInsert = line.split("-")[1].trim();

            insterDataInCellByName(cellName, valueToInsert);
        }
    }

    /**
     *
     * @param values тело сообщения из телеги, берём 1, 2 и 3 строчки сообщения, там инфа по установленным ПУ
     * @return
     */
    private String getTotalInstalled_ПУ(String[] values){
        return String.valueOf(
                Integer.parseInt(values[1].split("-")[1].trim()) +
                Integer.parseInt(values[2].split("-")[1].trim()) +
                Integer.parseInt(values[3].split("-")[1].trim()));
    }

    /**
     * Создание строки и ячеек для заполнения
     * @throws IOException
     */
    private void createRowAndCells() {

        try {
            FileInputStream file = new FileInputStream(new File(FILE_NAME));
            FileInputStream inputStream = new FileInputStream(FILE_NAME);
            XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
            Sheet sheet = workBook.getSheetAt(0);

            while (true){
                if(sheet.getRow(COUNT_ROW) != null) {
                    COUNT_ROW++;
                } else {
                    sheet.createRow(COUNT_ROW);
                    for (int i = 0; i < 14; i++) {
                        sheet.getRow(COUNT_ROW).createCell(i);
                    }
                    break;
                }
            }

            file.close();

            FileOutputStream outFile = new FileOutputStream(new File(FILE_NAME));
            workBook.write(outFile);
            outFile.close();
        } catch (IOException ex){}
    }

    /**
     *
     * @param name имя столбца в excel файле
     * @param data текст для записи в ячейку
     * @throws IOException
     */
    private void insterDataInCellByName(String name, String data) {
        try{
            FileInputStream inputStream = new FileInputStream(FILE_NAME);
            XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
            Sheet sheet = workBook.getSheetAt(0);
            Iterator<Row> it = sheet.iterator();

            Cell cell = null;
            cell = sheet.getRow(COUNT_ROW).getCell(columsNames.get(name));
            cell.setCellValue(data);

            inputStream.close();

            FileOutputStream outFile = new FileOutputStream(new File("table.xlsx"));
            workBook.write(outFile);
            outFile.close();
        } catch (IOException ex){
            // отправим в текстовый файл сообщение о том, что при записи в excel возникли проблемы :(
            new TxtWriter().write(
                    new Message("ADMIN",
                            String.format("[не удалось загрузить данные [%s][%s] в excel]", name, data))
            );
        }
    }

}