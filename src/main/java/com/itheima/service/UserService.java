package com.itheima.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.domain.User;
import com.itheima.vo.UserVO;

import java.util.List;

public interface UserService extends IService<User> {
    void deductBalance(Long id, Integer money);

    List<User> queryUser(String name, Integer status, Integer minBalance, Integer maxBalance);

    void deductionBalance(Long id, Integer money);

    UserVO queryUsersAndAddressById(Long id);

    List<UserVO> queryUsersAndAddress(List<Long> ids);
}
