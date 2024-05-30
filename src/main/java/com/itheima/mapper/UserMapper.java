package com.itheima.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    void updateBalanceById(@Param("ew") LambdaQueryWrapper<User> lambda, @Param("amount") int amount);

    @Update("update tb_user set balance = balance - #{money}  where id = #{id} ")
    void deductBalance(@Param("id") Long id, @Param("money") Integer money);
}
