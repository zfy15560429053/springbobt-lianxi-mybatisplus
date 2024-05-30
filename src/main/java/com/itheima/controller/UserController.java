package com.itheima.controller;

import cn.hutool.core.bean.BeanUtil;
import com.itheima.dto.UserFormDTO;
import com.itheima.domain.User;
import com.itheima.query.UserQuery;
import com.itheima.service.UserService;
import com.itheima.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * iserver开发简单接口
     */
    @ApiOperation("新增用户接口")
    @PostMapping
    void saveUser(@RequestBody UserFormDTO userFormDTO) {
        //把dto拷贝到po
        User user = BeanUtil.copyProperties(userFormDTO, User.class);
        userService.save(user);
        //新增
    }


    @ApiOperation("删除用户接口")
    @DeleteMapping("/{id}")
    void deleteUser(@ApiParam("用户id") @PathVariable("id") Long id) {
        userService.removeById(id);

    }

  /*  @ApiOperation("根据id查询用户")
    @GetMapping("/{id}")
    UserVO queryUserById(@ApiParam("用户id") @PathVariable("id") Long id) {
        //查询的是po
        User user = userService.getById(id);
        //转vo
        return BeanUtil.copyProperties(user, UserVO.class);
    }*/

   /* @ApiOperation("根据id批量查询")
    @GetMapping
    List<UserVO> queryUsers(@ApiParam("用户id集合") @RequestParam("ids") List<Long> ids) {
        //查询的是Po
        List<User> users = userService.listByIds(ids);
        //转
        return BeanUtil.copyToList(users, UserVO.class);
    }*/


    /**
     * service开发复杂接口
     *
     * @param id
     * @param money
     */

    @ApiOperation("扣除用户余额接口")
    @PutMapping("/{id}/deduction/{money}")
    public void deductBalance(
            @ApiParam("用户id") @PathVariable("id") Long id
            , @ApiParam("扣减的金额") @PathVariable("money") Integer money
    ) {
        userService.deductBalance(id, money);
    }

    /**
     * Lambda复杂条件查询用户
     */
    @ApiOperation("根据复杂条件查询用户接口")
    @GetMapping("/list")
    public List<UserVO> getUsers(UserQuery query) {
        List<User> users = userService.queryUser(query.getName(), query.getStatus(), query.getMinBalance(), query.getMaxBalance());
        return BeanUtil.copyToList(users, UserVO.class);
    }

    /**
     * Lambda更新案例
     */
    @ApiOperation("根据id修改用户余额")
    @PutMapping("/kouchu")
    public void deductionBalance(@ApiParam("用户id") @RequestParam("id") Long id, @ApiParam("扣除金额") @RequestParam("money") Integer money) {
        userService.deductionBalance(id, money);
    }


    /**
     * 静态工具：针对多个service之间会相互调用的问题
     */
    @ApiOperation("根据id查询用户和对应的地址")
    @GetMapping("{id}")
    public UserVO queryUsersAndAddressById(@ApiParam("用户id") @PathVariable("id") Long id) {
        UserVO userVO = userService.queryUsersAndAddressById(id);
        return userVO;
    }

    @ApiOperation("根据id批量查询用户和对应的地址")
    @GetMapping
    public List<UserVO> queryUsersAndAddress(@ApiParam("id集合") @RequestParam("ids") List<Long> ids) {
        List<UserVO> userVOS = userService.queryUsersAndAddress(ids);

        return userVOS;
    }

}
