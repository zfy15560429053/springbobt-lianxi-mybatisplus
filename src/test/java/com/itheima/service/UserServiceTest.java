package com.itheima.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void select() {
        int pageNO = 1, pageSize = 2;
        Page<User> page = Page.of(pageNO, pageSize);
        page.addOrder(new OrderItem("balance", false));

        Page<User> p = userService.page(page);
        System.out.println(p.getTotal()); //总条数
        System.out.println(p.getPages()); //总页数
        List<User> records = p.getRecords();
        records.forEach(System.out::println);

    }

}