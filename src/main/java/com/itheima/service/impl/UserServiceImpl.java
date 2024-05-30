package com.itheima.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.itheima.domain.Address;
import com.itheima.domain.UsersStatus;
import com.itheima.mapper.UserMapper;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.vo.AddressVO;
import com.itheima.vo.UserVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 扣减金额
     *
     * @param id
     * @param money
     */
    @Override
    public void deductBalance(Long id, Integer money) {
        User user = getById(id);
        //判断用户状态
        if (user == null || user.getStatus() == UsersStatus.FREEZE) {
            throw new RuntimeException("用户异常");
        }
        //检验用户余额
        if (user.getBalance() < money) {
            throw new RuntimeException("用户余额不足");
        }
        //扣除余额
        baseMapper.deductBalance(id, money);

    }

    /**
     * Lambda复杂条件查询用户
     *
     * @param name
     * @param status
     * @param minBalance
     * @param maxBalance
     */
    @Override
    public List<User> queryUser(String name, Integer status, Integer minBalance, Integer maxBalance) {
        List<User> users = lambdaQuery()
                .like(name != null, User::getUsername, name)
                .eq(status != null, User::getStatus, status)
                .gt(minBalance != null, User::getBalance, minBalance)
                .lt(maxBalance != null, User::getBalance, maxBalance)
                .list();
        return users;

    }

    /**
     * Lambda更新案例
     *
     * @param id
     * @param money
     */
    @Override
    @Transactional
    public void deductionBalance(Long id, Integer money) {
        //1.用户校验
        User user = getById(id);
        //判断用户状态
        if (user == null || user.getStatus() == UsersStatus.FREEZE) {
            throw new RuntimeException("用户异常");
        }
        //2.用户余额校验
        if (user.getBalance() < money) {
            throw new RuntimeException("用户余额不足");
        }
        //3.扣除余额为0，则修改status为冻结
        int remainBalance = user.getBalance() - money;  //扣除余额
        lambdaUpdate()
                .set(User::getBalance, remainBalance)
                .set(remainBalance == 0, User::getStatus, 0)
                .eq(User::getId, id)
                .eq(User::getBalance, user.getBalance()) //乐观锁 解决并发安全性
                .update();
    }

    /**
     * 根据id查询出用户及地址
     *
     * @param id
     */
    @Override
    public UserVO queryUsersAndAddressById(Long id) {
        //1.查询用户
        User user = getById(id);
        //判断用户状态
        if (user == null || user.getStatus() == UsersStatus.FREEZE) {
            throw new RuntimeException("用户异常");
        }
        //2.查询地址（静态方法）
        List<Address> address = Db.lambdaQuery(Address.class)
                .eq(Address::getUserId, id)
                .list();
        //3.封装
        //3.1封装uservo
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        //3.2封装地址
        if (CollUtil.isNotEmpty(address)) {
            List<AddressVO> addressVOS = BeanUtil.copyToList(address, AddressVO.class);
            userVO.setAddress(addressVOS);
        }
        return userVO;
    }

    /**
     * 根据id批量查询用户及地址
     *
     * @param ids
     * @return
     */
    @Override
    public List<UserVO> queryUsersAndAddress(List<Long> ids) {
        //1.查用户
        List<User> users = listByIds(ids);
        if (CollUtil.isEmpty(users)) {
            throw new RuntimeException("用户不存在");
        }
        //2查地址
        List<Integer> userIds = users.stream().map(User::getId).collect(Collectors.toList());
        List<Address> addresses = Db.lambdaQuery(Address.class)
                .in(Address::getUserId, userIds)
                .list();
        //3.封装
        List<AddressVO> addressVos = BeanUtil.copyToList(addresses, AddressVO.class);
        Map<Long, List<AddressVO>> addressMap = new HashMap<>(0);
        if (CollUtil.isNotEmpty(addressVos)) {
            addressMap = addressVos.stream().collect(Collectors.groupingBy(AddressVO::getId));
        }

        ArrayList<UserVO> lsit = new ArrayList<>(users.size());
        for (User user : users) {
            UserVO vo = BeanUtil.copyProperties(user, UserVO.class);
            lsit.add(vo);
            vo.setAddress(addressMap.get(user.getId()));
        }


        return lsit;
    }


}
