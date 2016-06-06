package models.db;

import com.fasterxml.jackson.databind.JsonNode;
import helpers.HibernateUtilities;
import helpers.ProjectConstants;
import models.objects.User;
import org.hibernate.Query;
import org.hibernate.Session;
import play.libs.Json;

import java.util.List;


public class DBUser {

    public static int insert(User user){
        Session session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.save(user);
            session.getTransaction().commit();
            return ProjectConstants.SUCCESS;
        } catch (Exception e){
            e.printStackTrace();
            return ProjectConstants.FAILED;
        } finally {
            session.close();
        }
    }

    public static List<User> listUsers(){
        Session session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();

        List<User> users = session.createCriteria(User.class).list();
        session.close();

        return users;
    }

    public static boolean login(String uname, String password){
        Session session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();

        String hql = "SELECT id, uname, password FROM User WHERE uname = :uname AND password = :password";
        Query query = session.createQuery(hql);
        query.setParameter("uname", uname);
        query.setParameter("password", password);
        List result = query.list();
        session.close();
        return result.size() != 0;
    }


    //EDIT USER (USER OBJECT) RET BOOLEAN
    public static int editUser(User user) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.update(user);
            session.getTransaction().commit();
            return ProjectConstants.SUCCESS;
        } catch (Exception e){
            e.printStackTrace();
            return ProjectConstants.FAILED;
        } finally {
            session.close();
        }
    }

    //GET_USER (INT ID) RET USER OBJECT
    public User getUser(int id) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        User user= (User) session.load(User.class, new Integer(id));
        session.close();
        return user;
    }

    //DEACTIVATE (INT ID) RET BOOLEAN
    public static int deactivateUser(int id) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            User user= (User) session.load(User.class, new Integer(id));
            user.setIsActive(ProjectConstants.INACTIVE);
            editUser(user);
            session.getTransaction().commit();
            return ProjectConstants.SUCCESS;
        } catch (Exception e){
            e.printStackTrace();
            return ProjectConstants.FAILED;
        } finally {
            session.close();
        }
    }


    //DELETE (INT ID) RET BOOLEAN
    public static int deleteUser (int id) {
        Session session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            User user = (User) session.load(User.class, new Integer(id));
            if(null != user){
                session.delete(user);
            }
            session.getTransaction().commit();
            return ProjectConstants.SUCCESS;
        } catch (Exception e){
            e.printStackTrace();
            return ProjectConstants.FAILED;
        } finally {
            session.close();
        }
    }



//    public static boolean getUsers(){
////        Session session = HibernateUtilities.getSessionFactory().openSession();
////        session.beginTransaction();
////
////        String hql = "SELECT id,  FROM User";
////        Query query = session.createQuery(hql);
////        List result = query.list();
////        session.close();
////        return result.size() != 0;
//    }

    public static void main(String[] args) {
        User user = new User();
        user.setFname("jam");
        user.setLname("render");
        user.setUname("jr");
        user.setPassword("1234");
        user.setIsActive(1);

        System.out.println("Insert: " + insert(user));


        List<User> users = listUsers();
//        for(User u : users){
//            System.out.println(u.getFname());
//        }
        JsonNode json = Json.toJson(users);
        System.out.println(json.toString());
//        System.out.println(login("manrick.capotolan", "12334"));


        System.out.println("Login: " + login("black", "1234"));
    }
}
