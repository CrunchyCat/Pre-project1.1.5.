package jm.task.core.jdbc;


import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();                                        // Создание таблицы

        userService.saveUser("Petr", "Ivanov", (byte) 27);       // Добавление пользователей
        userService.saveUser("Mariya", "Petrova", (byte) 28);
        userService.saveUser("Nikolay", "Muhin", (byte) 17);
        userService.saveUser("Vasiliy", "Morozov", (byte) 30);

        userService.getAllUsers();                                              // Получение всех  пользователей и вывод

        userService.cleanUsersTable();                                          // Очистка таблицы

        userService.dropUsersTable();                                           // Удаление таблицы

        UserDaoHibernateImpl.getSessionFactory().close();                       // Закрытие глобальной сессии


    }
}
