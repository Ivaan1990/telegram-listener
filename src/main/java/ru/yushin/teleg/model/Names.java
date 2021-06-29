package ru.yushin.teleg.model;

import java.util.HashMap;
import java.util.Map;

public class Names {
    static Map<String, String> names = new HashMap<String, String>();

    static {
        names.put("Ivan Evgenievich", "Иван Евгеньевич");
        names.put("Георгий null", "Георгий Гусев");
        names.put("", "");
        names.put("", "");
        names.put("", "");
    }

    static String getRealName(String fakeName){
        for(String key : names.keySet()){
            if(fakeName.equalsIgnoreCase(key)){
                return names.get(key);
            }
        }
        return fakeName;
    }
}