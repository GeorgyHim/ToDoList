package interlayer.dao;

import interlayer.hibernate.HibernateSessionFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 * Абстрактный объект доступа к данным
 *
 * @param <T>   -   тип объектов, с которыми работает DAO
 */
abstract class DAO<T> {

    /** Объект {@link SessionFactory} для создания сессий */
    private SessionFactory sessionFactory;

    /** Явный тип (класс) объекта, которому соответствует параметр T */
    private Class<T> typeOfObject;

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
     * Метод получения единственного объекта класса T по имени и значению поля
     *
     * @param fieldName -   Название поля
     * @param value     -   Значение поля
     * @return          -   Найденный объект или null
     */
    public T getByField(String fieldName, Object value) {
        try(Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(typeOfObject);
            return (T) criteria.add(Restrictions.eq(fieldName, value)).uniqueResult();
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

    /**
     * Метод обновления объекта БД
     *
     * @param object    -   объект
     */
    public void update(T object) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(object);
            transaction.commit();
        }
    }
}
