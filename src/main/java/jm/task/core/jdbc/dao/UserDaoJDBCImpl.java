package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection();                         // Поле для получения connection
    private static String SQL_QUERY;                                   // Строка запроса для prepareStatement(SQL_QUERY)

    public UserDaoJDBCImpl() {

    }

    @Override

    public void createUsersTable() {
        SQL_QUERY = "CREATE TABLE IF NOT EXISTS Users (id BIGINT NOT NULL AUTO_INCREMENT, " +
                "firstName VARCHAR(30) NOT NULL," +
                "lastName VARCHAR(30) NOT NULL," +
                "age TINYINT(3)  NOT NULL," +
                "PRIMARY KEY (id))";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    @Override
    public void dropUsersTable() {
        SQL_QUERY = "DROP TABLE IF EXISTS Users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        SQL_QUERY = "INSERT INTO Users (firstName, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY)) {
            preparedStatement.setString(1, name);                      // Заполнение SQL_QUERY аргументами
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            System.out.println("User с именем " + name  + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void removeUserById(long id) {
        SQL_QUERY = "DELETE FROM Users WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY)){
            preparedStatement.setLong(1, id);                          // Заполнение SQL_QUERY аргументами
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> allUsers = new ArrayList<>();
        SQL_QUERY = "SELECT * FROM Users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY)){
            ResultSet  resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                allUsers.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(allUsers);
        return allUsers;
    }

    public void cleanUsersTable() {
        SQL_QUERY = "TRUNCATE TABLE Users";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_QUERY)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
