package cn.com.taiji.service;

import cn.com.taiji.domain.UserInfo;

/**
 * Created by iandtop on 2018/12/11.
 */
public interface UserInfoService {
    UserInfo findByUsername(String username);
}
