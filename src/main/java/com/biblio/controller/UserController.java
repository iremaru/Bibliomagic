package com.biblio.controller;

import com.biblio.connection.ConnectionDB;
import java.sql.Connection;

public class UserController {

    public boolean CheckCredentials(String user, String password)
    {
        Connection connection = ConnectionDB.Connect();
        if(CheckUserExist(connection) && CheckUsserPassword(connection, user, password)) {
            ConnectionDB.CloseConnection(connection);
            return true;
        }
        ConnectionDB.CloseConnection(connection);
        return false;
    }

    public boolean CheckUserExist(Connection connection)
    {

        return true;
    }

    public boolean CheckUsserPassword(Connection connection, String user, String name)
    {
        return true;
    }
}
