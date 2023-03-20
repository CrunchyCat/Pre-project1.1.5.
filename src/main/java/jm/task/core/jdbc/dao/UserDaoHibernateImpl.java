package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;



public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory SESSION_FACTORY = Util.createSession();         // Создание глобальной сесси
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        try {
            Session session = SESSION_FACTORY.getCurrentSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS Users (id BIGINT NOT NULL AUTO_INCREMENT, " +
                    "firstName VARCHAR(30) NOT NULL," +
                    "lastName VARCHAR(30) NOT NULL," +
                    "age TINYINT(3)  NOT NULL," +
                    "PRIMARY KEY (id))").addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        try {
            Session session = SESSION_FACTORY.getCurrentSession();
            transaction = session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS Users");
            query.executeUpdate();
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try {
            Session session = SESSION_FACTORY.getCurrentSession();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println(ANSI_YELLOW + "User с именем " + name + " добавлен в базу данных" + ANSI_RESET);
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        try {
            Session session = SESSION_FACTORY.getCurrentSession();
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        Transaction transaction = null;
        try {
            Session session = SESSION_FACTORY.getCurrentSession();
            transaction = session.beginTransaction();
            users = session.createQuery("FROM User").list();
            session.getTransaction().commit();
            System.out.println(ANSI_YELLOW + users + ANSI_RESET);
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        try {
            Session session = SESSION_FACTORY.getCurrentSession();
            transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public static void closeSessionFactory() {
        SESSION_FACTORY.close();
    }
}
