package ru.yushin.teleg.db;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * PostgersSQL todo подключить
 */
public class PostgresSql extends AbstractDataBase{

    String connectionPath = "jdbc:sqlserver";
    String serverName = "localhost:8080";

    @Override
    public void initConnection() {
        try {
            Class.forName("");
            connection = DriverManager.getConnection(connectionPath);
            statement = connection.createStatement();
        } catch (SQLException ex){
            Logger.getLogger(MsSqlDb.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    String setConnectionPath(String servarName) {
        return "null";
    }
}
