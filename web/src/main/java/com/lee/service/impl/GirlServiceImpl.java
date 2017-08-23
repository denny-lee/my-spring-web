package com.lee.service.impl;

import com.lee.dao.BaseDao;
import com.lee.entity.GirlFriend;
import com.lee.service.GirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class GirlServiceImpl implements GirlService{
    @Resource(name = "baseDaoImpl")
    private BaseDao<GirlFriend> baseDao;
    @Override
    public GirlFriend serve(GirlFriend gf) {

        baseDao.save(gf);
        return gf;
    }
}
