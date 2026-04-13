package com.ershou.ershou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.dto.StoreAddressDto;
import com.ershou.ershou.domain.po.StoreAddress;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ershou.ershou.domain.query.StoreAddressPageQuery;

/**
 * <p>
 * 收货地址表 服务类
 * </p>
 *
 * @author author
 * @since 2024-11-06
 */
public interface StoreAddressService extends IService<StoreAddress> {

    Page<StoreAddressDto> getStoreAddressList(StoreAddressPageQuery storeAddressPageQuery);
}
