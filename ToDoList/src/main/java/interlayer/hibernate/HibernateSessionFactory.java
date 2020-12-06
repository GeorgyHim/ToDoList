package interlayer.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Класс для конфигурации Hibernate и предоставления {@link SessionFactory}
 */
public class HibernateSessionFactory {
    /** Фабрика сессий Hibernate */
    private static SessionFactory sessionFactory;

    /** Конфигурация Hibernate */
    private static Configuration configuration;

    private HibernateSessionFactory() {
        configuration = new Configuration();
        addAnnotatedClasses();
        sessionFactory = createSessionFactory();
    }

    /** Метод добавления аннотированных классов моделей */
    private void addAnnotatedClasses() {
        // TODO: Добавить классы моделей
        //configuration.addAnnotatedClass();
    }

    /** Метод создания {@link SessionFactory}*/
    private static SessionFactory createSessionFactory() {
        if (configuration == null) {
            configuration = new Configuration();
        }
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
        return sessionFactory;
    }
}
