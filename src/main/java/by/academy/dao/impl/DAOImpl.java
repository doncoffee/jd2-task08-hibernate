package by.academy.dao.impl;

import by.academy.dao.DAO;
import by.academy.util.HibernateUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static by.academy.util.Constants.FROM;

public abstract class DAOImpl<T> implements DAO<T> {
    private final Class<T> clazz;

    public DAOImpl(Class<T> clazz) {
        this.clazz = clazz;
    }


    @Override
    public T create(T object) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();

        entityManager.persist(object);

        entityManager.getTransaction().commit();
        entityManager.close();
        return object;
    }

    @Override
    public List<T> read() {
        List<T> list;
        EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();

        Query query = entityManager.createQuery(FROM + clazz.getSimpleName());
        list = query.getResultList();

        entityManager.getTransaction().commit();
        entityManager.close();
        return list;
    }

    @Override
    public T update(T object) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();

        entityManager.merge(object);

        entityManager.getTransaction().commit();
        entityManager.close();
        return object;
    }

    @Override
    public void delete(int id) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();

        entityManager.remove(entityManager.find(clazz, id));

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public T selectById(int id) {
        EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();

        T object = entityManager.find(clazz, id);

        entityManager.getTransaction().commit();
        entityManager.close();
        return object;
    }
}
