package com.lee.dao;

import com.lee.entity.GirlFriend;
import org.hibernate.Session;

public interface GirlDao {
    void save(GirlFriend girlFriend) throws Exception;
    Session getSession();
}
