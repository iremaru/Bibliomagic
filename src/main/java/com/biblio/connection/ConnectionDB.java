package com.biblio.connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConnectionDB {

    static final String dbName = "libros";
    static final String dbURL = "jdbc:mysql://localhost/" + dbName;
    static final String user = "root";
    static final String pass = "";

    public static Connection connection;

    public static Connection Connect()
    {
        //Connection connection = null;

        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, user, pass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return  connection;
    }

    public static void CloseConnection(Connection connection)
    {
        try {
            connection.close();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public static List<Object[]> GetTableData(String tableName)
    {
        String SQL = "SELECT * FROM " + tableName;
        List<Object[]> data = new ArrayList<>();
        try
        {
            PreparedStatement statement = connection.prepareStatement(SQL);
            ResultSet result = statement.executeQuery();
            final int columnCount = result.getMetaData().getColumnCount()+1;

            while (result.next())
            {
                Object[] values = new Object[columnCount];

                for (int i = 1; i < columnCount; i++) {
                    values[i -1] = result.getObject(i);
                }

                data.add(values);
            }

        } catch (SQLException ex) { ex.printStackTrace(); }

        return data;

    }

    public static void SendInstructionToBD(String sql)
    {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
