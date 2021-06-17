package ru.yushin.teleg.model;

import java.util.HashMap;
import java.util.Map;

public class Names {
    static Map<String, String> names = new HashMap<String, String>();

    static {
        names.put("Ivan Evgenievich", "Иван Евгеньевич");
        names.put("", "");
        names.put("", "");
        names.put("", "");
        names.put("", "");
    }

    static String getRealName(String fakeName){
        return fakeName == null ? fakeName : names.get(fakeName);
    }
}