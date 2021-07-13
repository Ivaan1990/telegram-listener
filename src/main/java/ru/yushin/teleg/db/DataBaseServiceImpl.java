package ru.yushin.teleg.db;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DataBaseServiceImpl implements DataBaseService  {
    IDataBase dataBase;

    public DataBaseServiceImpl(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    public Map execSqlScript(String script) {
        dataBase.initConnection();

        ResultSet resultSet  = null;
        Map result = null;

        try {
            resultSet = dataBase.getCurrentStatement().executeQuery(script);
            result = new HashMap<Integer, HashMap>();
            int ii = 0;
            while (resultSet.next()) {
                HashMap<String, String> row = new HashMap<>();
                for (int column = 1; column <= resultSet.getMetaData().getColumnCount(); column++) {
                    try {
                        String columnName = resultSet.getMetaData().getColumnName(column);
                        Object cellValue = resultSet.getObject(column);
                        String cellValueString = "" + cellValue;
                        row.put(columnName, cellValueString);
                    } catch (Exception e) {
                        System.err.printf("При выполнении запроса [%s] произошла непредвиденная ошибка", script);
                    }
                }
                result.put(ii, row);
                ii++;
            }
            resultSet.close();

        } catch (SQLException ignored){

        }
        finally {
            try {
                resultSet.close();
            } catch (SQLException ignored) {}
            try {
                dataBase.getCurrentStatement().close();
            } catch (SQLException ignored) {}
            try {
                dataBase.getCurrentConnection().close();
            } catch (SQLException ignored) {}
        }
        return result;
    }

    @Override
    public boolean updateSqlScript(String script) {
        dataBase.initConnection();
        try {
            dataBase.getCurrentStatement().executeUpdate(script);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            try {
                dataBase.getCurrentStatement().close();
            } catch (SQLException ignored) {}
            try {
                dataBase.getCurrentConnection().close();
            } catch (SQLException ignored) {}
        }
        return false;
    }

    @Override
    public void executeStoredProcedure(String procedureName) {
        dataBase.initConnection();

        try(CallableStatement cstmt = dataBase.getCurrentConnection().prepareCall(String.format("{call %s(?)}", procedureName));) {

            cstmt.registerOutParameter(1, java.sql.Types.INTEGER);

            cstmt.execute();
            System.out.println("RETURN STATUS: " + cstmt.getInt(1));
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getDataBaseInstance(IDataBase dataBase) {
        return dataBase.getCurrentConnection();
    }

    @Override
    public Statement getDataBaseStatement(IDataBase dataBase) {
        return dataBase.getCurrentStatement();
    }

}
