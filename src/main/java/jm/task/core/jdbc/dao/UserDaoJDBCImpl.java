package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT auto_increment PRIMARY KEY," +
                "name VARCHAR(50) NOT NULL," +
                "lastname VARCHAR(50) NOT NULL," +
                "age INT)";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы");
        }

    }

    public void dropUsersTable() {
        String dropTableSQL = "DROP TABLE IF EXISTS users";

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(dropTableSQL);
            System.out.println("Таблица удалена");
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении таблицы");
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        String insertUserSQL = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertUserSQL)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("Пользователь успешно добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Ошибка при сохранении пользователя");
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        String deleteUserSQL = "DELETE FROM users WHERE id = ?";
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteUserSQL)) {
            statement.setLong(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Пользователь удален из БД");
            } else {
                System.out.println("Пользователь не найден");
            }
        } catch (SQLException e) {
            System.out.println("Ошибка удаления пользователя");
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectAllUsersSQL = "SELECT * FROM users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectAllUsersSQL)) {
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte("age");
                User user = new User(id, name, lastName, age);
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при получении списка пользователей");
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        String truncateTableSQL = "TRUNCATE TABLE users";
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(truncateTableSQL);
            System.out.println("Таблица пользователей очищена");
        } catch (SQLException e) {
            System.out.println("Ошибка очистки таблицы пользователей");
        }

    }
}
