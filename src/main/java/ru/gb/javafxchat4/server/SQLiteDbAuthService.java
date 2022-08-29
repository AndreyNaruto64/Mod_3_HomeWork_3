package ru.gb.javafxchat4.server;

import java.io.IOException;
import java.sql.*;

public class SQLiteDbAuthService implements AuthService {

    public static String DB_PATH = "src/main/resources/ru/gb/javafxchat4/server/db/database.db";

    private Connection connection;

    public SQLiteDbAuthService(){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+DB_PATH);
        }catch (SQLException e){
            throw new RuntimeException("Не удалось подключиться к базе данных: " + e.getMessage(), e);
        }
    }
    @Override
    public String getNickByLoginAndPassword(String login, String password) {
        try {
            PreparedStatement stmt = connection.prepareStatement("select username from auth where login = ? and password = ?");
            stmt.setString(1, login);
            stmt.setString(2, password);

            ResultSet resultSet = stmt.executeQuery();
            return resultSet.getString(1);
        }catch (SQLException e){
            System.out.println("не удалось получить username: "+ e.getMessage());
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        if(connection != null ){
            try {
                connection.close();
            }catch (SQLException e ){
                throw new IOException(e);
            }
        }

    }
}
