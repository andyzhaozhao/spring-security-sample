package cn.com.taiji.repository;

import cn.com.taiji.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by iandtop on 2018/12/11.
 */
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
}
