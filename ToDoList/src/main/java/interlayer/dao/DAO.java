package interlayer.dao;

import interlayer.hibernate.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class DAO<T> {

    /** Объект {@link SessionFactory} для создания сессий */
    private SessionFactory sessionFactory;

    /** Явный тип (класс) объекта, которому соответствует параметр T */
    private final Class<T> typeOfObject;

    public DAO(Class<T> typeOfObject) {
        this.typeOfObject = typeOfObject;
        sessionFactory = HibernateSessionFactory.getSessionFactory();
    }

    /**
     * Метод получения объекта по идентификатору
     *
     * @param id    -   Идентификатор
     * @return      -   Найденный объект либо null
     */
    public T getById(long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(typeOfObject, id);
        }
    }

    /**
     * Метод добавления объекта в базу данных
     *
     * @param object    -   объект
     * @return          -   идентификатор в БД
     */
    public long add(T object) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            long id =(long) session.save(object);
            transaction.commit();
            return id;
        }
    }
}
