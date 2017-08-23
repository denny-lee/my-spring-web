package com.lee.service.impl;

import com.lee.dao.GirlDao;
import com.lee.entity.GirlFriend;
import com.lee.service.GirlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GirlServiceImpl implements GirlService {
    @Autowired
    private GirlDao girlDao;
    @Override
    public GirlFriend serve(GirlFriend gf) {

        girlDao.save(gf);
        return gf;
    }
}
