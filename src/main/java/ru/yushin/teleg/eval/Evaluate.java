package ru.yushin.teleg.eval;

/**
 * Класс представляющий обработчик ошибок в сообщениях от пользователей.
 */
public class Evaluate {
    String[] values;

    public Evaluate(String[] values) {
        this.values = values;
    }

    public String getAddress(){
        String address = values[1].trim();

        if(address.split("-").length > 2){
            return appendAddress(address);
        }

        try {
            return address.split("-")[1].trim();
        } catch (IndexOutOfBoundsException ex){
            return replaceAddress(address).trim();
        }

    }

    /**
     * Костыль от какого-то странного символа, который пишет Андрей Корнеев
     * @param line строка содержащая Установлено ПУ 3Ф 3Т –,
     * @return
     */
    public String eval3F3T(String line){
        String parseLine = line.replaceAll("Установлено ПУ 3Ф 3Т –", "");
        return parseLine.replaceAll("[^0-9\\\\+]", "").length() != 0 ? parseLine : "0";
    }

    /**
     * //-- для случая, если в адресе присутствует '-'
     * @param address
     * @return
     */
    private String appendAddress(String address){
        String[] arr = address.split("-");
        StringBuilder builder = new StringBuilder();
        for(String line : arr) {
            builder.append(line);
        }
        return builder.toString().trim();
    }

    public String[] getValues() {
        return values;
    }

    /**
     * //-- для случая, если в адресе присутствует 'АДРЕС/Адрес'
     * @param address
     * @return
     */
    private String replaceAddress(String address){
        String replaceAddress = "";
        if(address.contains("АДРЕС")) {
            replaceAddress = address.replaceAll("АДРЕС", "");
            return replaceAddress;
        } else if(address.contains("Адрес")){
            replaceAddress = address.replaceAll("Адрес", "");
            return replaceAddress;
        }
        return address;
    }


}