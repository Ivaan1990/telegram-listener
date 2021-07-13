package ru.yushin.teleg.db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MsSqlDb extends AbstractDataBase {

    String connectionPath = "jdbc:sqlserver://%s;user=%s;password=%s;";
    String serverName;

    public MsSqlDb() {
        connectionPath = setConnectionPath("VLS-KKA-TESTSQL");
    }

    public MsSqlDb(String serverName) {
        this.serverName = serverName;
        connectionPath = setConnectionPath(serverName);
    }

    @Override
    public void initConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
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
    String setConnectionPath(String serverName) {
        return String.format(connectionPath, serverName, "sql_av_kka", "al***12345");
    }
}
