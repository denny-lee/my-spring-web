package com.lee.service.impl;

import com.lee.dao.GirlDao;
import com.lee.entity.GirlFriend;
import com.lee.entity.GirlFriend_;
import com.lee.service.GirlService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;


@Service
@Transactional
public class GirlServiceImpl implements GirlService {
    @Autowired
    private GirlDao girlDao;
    @Override
    public GirlFriend serve(GirlFriend gf) {

        try {
            girlDao.save(gf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gf;
    }

    @Override
    public List<GirlFriend> listByParams() {
        Session session = girlDao.getSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<GirlFriend> criteria = builder.createQuery( GirlFriend.class );
        Root<GirlFriend> root = criteria.from( GirlFriend.class );

        ParameterExpression<String> nickNameParameter = builder.parameter( String.class );
        criteria.where( builder.equal( root.get( GirlFriend_.name ), nickNameParameter ) );

        TypedQuery<GirlFriend> query = session.createQuery( criteria );
        query.setParameter( nickNameParameter, "loly" );
        List<GirlFriend> persons = query.getResultList();
        return persons;
    }
}
