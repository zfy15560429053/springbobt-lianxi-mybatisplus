package com.itheima.domain;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public
enum UsersStatus {

    NORMAL(1,"正常"),
    FREEZE(2,"冻结");

    @EnumValue
    private final int value;
    @JsonValue

    private  final  String desc;

    UsersStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
