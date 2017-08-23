package com.lee.dao;

import dev.local.ReflectUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao<T> {
    @Autowired
    private SessionFactory sessionFactory;

    private Class<?> entityClass;

    public BaseDao() {
        this.entityClass = ReflectUtils.getClassGenricType(getClass());
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public void save(T t) {
        Session session = getSession();
//        Transaction tx = session.beginTransaction();
        try {
            session.save(t);
            session.flush();
        } catch (Exception e) {
            e.printStackTrace();
//            tx.rollback();
        }
//        tx.commit();
//        session.close();
    }
}
