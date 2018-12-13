package cn.com.taiji.service.impl;

import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.repository.UserInfoRepository;
import cn.com.taiji.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by iandtop on 2018/12/11.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoRepository.findByUsername(username);
    }
}
