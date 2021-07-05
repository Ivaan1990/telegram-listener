package ru.yushin.teleg.writers;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.yushin.teleg.model.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class ExcelEngine {

    protected static final String FILE_NAME = "actual.xlsx";
    protected static int COUNT_ROW = 1;
    protected static Map<String, Integer> columsNames;

    static {
        columsNames = new HashMap<String, Integer>();
        columsNames.put("ДАТА", 0);
        columsNames.put("Адрес", 1);
        columsNames.put("Монтажник", 2);
        columsNames.put("Установлено ПУ 1Т", 3);
        columsNames.put("Установлено ПУ 2Т", 4);
        columsNames.put("Установлено ПУ 3Т", 5);
        columsNames.put("Установлено ПУ 3Ф 1Т", 6);
        columsNames.put("Установлено ПУ 3Ф 2Т", 7);
        columsNames.put("Установлено ПУ 3Ф 3Т", 8);
        columsNames.put("ВСЕГО ЗА ДЕНЬ УСТАНОВЛЕНО ПУ", 9);
        columsNames.put("Отказы", 10);
        columsNames.put("Брак ПУ", 11);
        columsNames.put("Брак ПЛ", 12);
        columsNames.put("Остаток ПУ 1Т", 13);
        columsNames.put("Остаток ПУ 2Т", 14);
        columsNames.put("Остаток ПУ 3Т", 15);
        columsNames.put("Остаток ПУ 3Ф 1Т", 16);
        columsNames.put("Остаток ПУ 3Ф 2Т", 17);
        columsNames.put("Остаток ПУ 3Ф 3Т", 18);
        columsNames.put("Остаток ПЛ", 19);
    }

    /**
     * Создание строки и ячеек для заполнения
     * @throws IOException
     */
    protected void createRowAndCells() {

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
                    for (int i = 0; i < 19; i++) {
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
    protected void insertDataInCellByName(String name, String data) {
        try{
            FileInputStream inputStream = new FileInputStream(FILE_NAME);
            XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
            Sheet sheet = workBook.getSheetAt(0);

            Cell cell;
            cell = sheet.getRow(COUNT_ROW).getCell(columsNames.get(name));
            cell.setCellValue(data);

            inputStream.close();

            FileOutputStream outFile = new FileOutputStream(new File(FILE_NAME));
            workBook.write(outFile);
            outFile.close();
        } catch (IOException ex){
            // отправим в текстовый файл сообщение о том, что при записи в excel возникли проблемы :(
            new TxtWriter().write(
                    new Message("ERROR SCHEDULER",
                            String.format("[не удалось загрузить данные [%s][%s] в excel]", name, data))
            );
        }
    }

    /**
     * пока работает только с числовыми значениями.
     * @param name номер столбца
     * @return содержимое ячейки
     */
    protected String getDataFromCellByName(String name){
        String value = "";

        try{
            FileInputStream inputStream = new FileInputStream(FILE_NAME);
            XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
            Sheet sheet = workBook.getSheetAt(0);

            Cell cell;
            cell = sheet.getRow(COUNT_ROW).getCell(columsNames.get(name));
            value = cell.getRichStringCellValue().toString().replaceAll("[^0-9\\\\+]", "");

            inputStream.close();

            FileOutputStream outFile = new FileOutputStream(new File(FILE_NAME));
            workBook.write(outFile);
            outFile.close();

        } catch (IOException ex){}

        return value;
    }

    public String getSumOfInstalledPY(){
        return String.valueOf(Integer.parseInt(getDataFromCellByName("Установлено ПУ 1Т"))
                + Integer.parseInt(getDataFromCellByName("Установлено ПУ 2Т"))
                + Integer.parseInt(getDataFromCellByName("Установлено ПУ 3Т"))
                + Integer.parseInt(getDataFromCellByName("Установлено ПУ 3Ф 1Т"))
                + Integer.parseInt(getDataFromCellByName("Установлено ПУ 3Ф 2Т"))
                + Integer.parseInt(getDataFromCellByName("Установлено ПУ 3Ф 3Т")));
    }
}
