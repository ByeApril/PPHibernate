package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "rootroot";
    public static Connection connection;
    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (connection != null) {
                System.out.println("Соединение установлено");
            } else {
                System.out.println("Соединение не установлено");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер JDBC не найден");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Ошибка подключения");
            e.printStackTrace();
        }
        return connection;
    }
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
