package com.lee.dao.impl;

import com.lee.dao.BaseDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void save(T t) {
        Session session = sessionFactory.openSession();
//        Transaction tx = session.beginTransaction();
        try {
            session.save(t);
            session.flush();
        } catch (Exception e) {
            e.printStackTrace();
//            tx.rollback();
        }
//        tx.commit();
        session.close();
    }
}
