package com.itheima.service.impl;

import com.itheima.domain.Address;
import com.itheima.mapper.AddressMapper;
import com.itheima.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zfy
 * @since 2024-05-27
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
