package helpers;

/**
 * Created by pajongjong on 5/30/16.
 */

import models.objects.User;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.util.Date;

public class HibernateUtilities {
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    static
    {
        try {
            Configuration configuration = new Configuration().configure();
            serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch(Exception exception) {
            System.out.println("Problem creating session factory!");
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

//    TEST ENVIRONMENT
    public static void main(String[] args) {
        System.out.println("Hello World");
        Session session = HibernateUtilities.getSessionFactory().openSession();
        session.beginTransaction();

        User user = new User();
        user.setFname("Jamilla Jyssa Mae");
        user.setLname("Rendor");
        user.setUname("jamilla.rendor");
        user.setPassword("1234");
        user.setLastLogin(new Date());
        user.setIsActive(ProjectConstants.ACTIVE);
        session.save(user);

        session.getTransaction().commit();
        session.close();
        HibernateUtilities.getSessionFactory().close();
    }
}