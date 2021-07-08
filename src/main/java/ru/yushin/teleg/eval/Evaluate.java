package ru.yushin.teleg.eval;


public class Evaluate {
    String[] values;

    public Evaluate(String[] values) {
        this.values = values;
    }

    public String getAddress(){
        String address = values[1].trim();

        if(address.split("-").length > 2){
            return evalAddress(address);
        }

        try {
            return address.split("-")[1].trim();
        } catch (IndexOutOfBoundsException ex){
            //todo заресплейсить АДРЕС Адрес
            return address;
        }

    }

    /**
     * //-- для случая, если в адресе присутствует '-'
     * @param address
     * @return
     */
    private String evalAddress(String address){
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
}