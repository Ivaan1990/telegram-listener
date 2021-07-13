package ru.yushin.teleg.db;

import java.sql.Connection;
import java.sql.Statement;

public abstract class AbstractDataBase implements IDataBase {
    Statement statement;
    Connection connection;

    @Override
    public Connection getCurrentConnection() {
        return connection;
    }

    @Override
    public Statement getCurrentStatement() {
        return statement;
    }

    abstract String setConnectionPath(String serverName);
}

