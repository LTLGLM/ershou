package com.ershou.ershou.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.dto.StoreAddressDto;
import com.ershou.ershou.domain.po.StoreAddress;
import com.ershou.ershou.domain.query.StoreAddressPageQuery;
import com.ershou.ershou.mapper.StoreAddressMapper;
import com.ershou.ershou.service.StoreAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收货地址表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-11-06
 */
@Service
public class StoreAddressServiceImpl extends ServiceImpl<StoreAddressMapper, StoreAddress> implements StoreAddressService {
    @Autowired
    private StoreAddressMapper storeAddressMapper;
    @Override
    public Page<StoreAddressDto> getStoreAddressList(StoreAddressPageQuery storeAddressPageQuery) {
        Page<StoreAddressDto> storeAddressPage = new Page<>(storeAddressPageQuery.getPageNum(), storeAddressPageQuery.getPageSize());
        Page<StoreAddressDto> storeAddressList = storeAddressMapper.getStoreAddressList(storeAddressPage, storeAddressPageQuery);
        return storeAddressList;
    }
}
