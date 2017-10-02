package com.lee.service;

import com.lee.entity.GirlFriend;

import java.util.List;

public interface GirlService {
    GirlFriend serve(GirlFriend girlFriend);

    List<GirlFriend> listByParams();
}
