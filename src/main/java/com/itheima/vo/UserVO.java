package com.itheima.vo;

import com.itheima.domain.Address;
import com.itheima.domain.UsersStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "用户VO实体")
public class UserVO {

    /**
     * 此类为查询时用的vo
     */
    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("详细信息")
    private String info;

    @ApiModelProperty("使用状态（1正常 2冻结）")
    private UsersStatus status;

    @ApiModelProperty("账户余额")
    private Integer balance;

    @ApiModelProperty("用户收获地址")
    private List<AddressVO> address;

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", info='" + info + '\'' +
                ", status=" + status +
                ", balance=" + balance +
                ", address=" + address +
                '}';
    }

    public List<AddressVO> getAddress() {
        return address;
    }

    public void setAddress(List<AddressVO> address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public UsersStatus getStatus() {
        return status;
    }

    public void setStatus(UsersStatus status) {
        this.status = status;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }
}
