package ru.yushin.teleg.db;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public interface DataBaseService {
    /**
     *
     * @param script sql запрос
     * @return результат выборки
     */
    Map execSqlScript(String script);

    /**
     *
     * @param script sql запрос на апдейт таблицы
     * @return
     */
    boolean updateSqlScript(String script);

    /**
     *
     * @param procedureName имя процедуры которую хотим запустить
     */
    void executeStoredProcedure(String procedureName);

    /**
     *
     * @param dataBase
     * @return текущий Connection
     */
    Connection getDataBaseInstance(IDataBase dataBase);

    /**
     *
     * @param dataBase
     * @return текущий Statement
     */
    Statement getDataBaseStatement(IDataBase dataBase);

    /**
     *
     * @param quaryResult результат sql запроса
     * @param columnName имя столбца
     * @return значение столбца
     */
    default String getSingleValueByColumnName(HashMap<String, String> quaryResult, String columnName){
        for(Map.Entry<String, String> item : quaryResult.entrySet()){
            if(String.valueOf(item.getKey()).equals(columnName)){
                return String.valueOf(item.getValue());
            }
        }
        return "";
    }
}
