package com.smhrd.yeaha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.smhrd.yeaha.mapper.YeahaUserMapper;
import com.smhrd.yeaha.model.YeahaUser;

@Service
public class DBYeahaUserService {

    private final YeahaUserMapper yeahaUserMapper;

    @Autowired
    public DBYeahaUserService(YeahaUserMapper yeahaUserMapper) {
        this.yeahaUserMapper = yeahaUserMapper;
    }

    @Transactional
    public void saveUser(YeahaUser user) {
        yeahaUserMapper.insertUser(user);
    }
}
