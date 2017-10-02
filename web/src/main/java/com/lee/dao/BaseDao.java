package com.lee.dao;

import dev.local.ReflectUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;

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

    public void save(T t) throws Exception {
        Session session = getSession();
//        Transaction tx = session.beginTransaction();
//        try {
            session.save(t);
//            session.flush();
            throw new NullPointerException("aaa");
//        } catch (Exception e) {
//            e.printStackTrace();
//            tx.rollback();
//        }
//        tx.commit();
//        session.close();
    }

}
