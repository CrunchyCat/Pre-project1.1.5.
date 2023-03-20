package jm.task.core.jdbc.util;



import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;
import java.util.Properties;


public class Util {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/users1.1.4";
    private static final String USER_NAME = "root";
    private static final String  PASSWORD = "12345";
    private static SessionFactory sessionFactory;



    private Util() {
        
    }

    private static Configuration hibernateConfig() {                             //конфигурация для глобальной сессии

        Configuration configuration = new Configuration();

        final Properties properties = new Properties();

        properties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        properties.put("hibernate.connection.url", URL);
        properties.put("hibernate.connection.username", USER_NAME);
        properties.put("hibernate.connection.password", PASSWORD);
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        properties.put("hibernate.current_session_context_class", "thread");
        properties.put("hibernate.show_sql", true);
        properties.put("hbm2ddl.auto", "create-drop");

        configuration.addProperties(properties).addAnnotatedClass(User.class);

        return configuration;
    }

    public static SessionFactory createSession() {

        return hibernateConfig().buildSessionFactory();
    }



    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            connection.setAutoCommit(false);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }



}
