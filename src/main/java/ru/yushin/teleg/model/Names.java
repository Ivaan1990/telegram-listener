package ru.yushin.teleg.model;

import java.util.HashMap;
import java.util.Map;

public class Names {
    static Map<String, String> names = new HashMap<String, String>();

    static {
        names.put("Ivan Evgenievich", "ADMIN");
        names.put("Георгий null", "Георгий Гусев");
        names.put("Ефимов И. Г. Вомифе", "");
        names.put("Вадим", "");
        names.put("АНДРЕЙ", "");
        names.put("Иван Тюрин", "Иван Тюрин");
        names.put("Денис Клевцов", "Денис Клевцов");
        names.put("Артур", "");
        names.put("Камакин Николай", "Николай Камакин");
        names.put("Demidov Andrey", "Андрей Демидов");
        names.put("Евгений Евгений", "");
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