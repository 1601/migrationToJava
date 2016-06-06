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
        List<User> users = listUsers();
//        for(User u : users){
//            System.out.println(u.getFname());
//        }
        JsonNode json = Json.toJson(users);
        System.out.println(json.toString());
//        System.out.println(login("manrick.capotolan", "12334"));
    }
}
