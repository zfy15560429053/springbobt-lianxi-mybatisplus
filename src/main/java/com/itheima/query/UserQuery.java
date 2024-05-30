package com.itheima.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "用户查询条件实体")
public class UserQuery {
    @ApiModelProperty("用户名关键字")
    private String name;
    @ApiModelProperty("用户状态：1-正常，2-冻结")
    private Integer status;
    @ApiModelProperty("余额最小值")
    private Integer minBalance;
    @ApiModelProperty("余额最大值")
    private Integer maxBalance;

    @Override
    public String toString() {
        return "UserQuery{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", minBalance=" + minBalance +
                ", maxBalance=" + maxBalance +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(Integer minBalance) {
        this.minBalance = minBalance;
    }

    public Integer getMaxBalance() {
        return maxBalance;
    }

    public void setMaxBalance(Integer maxBalance) {
        this.maxBalance = maxBalance;
    }
}
