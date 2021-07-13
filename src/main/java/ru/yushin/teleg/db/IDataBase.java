package ru.yushin.teleg.db;

import java.sql.Connection;
import java.sql.Statement;

public interface IDataBase {
    /**
     * Подключение к базе данных
     */
    void initConnection();

    /**
     *
     * @return текущее подключение к бд
     */
    Connection getCurrentConnection();

    /**
     *
     * @return текущее сосятоние БД
     */
    Statement getCurrentStatement();
}
