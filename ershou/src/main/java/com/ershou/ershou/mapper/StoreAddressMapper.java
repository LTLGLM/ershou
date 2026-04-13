package com.ershou.ershou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.dto.StoreAddressDto;
import com.ershou.ershou.domain.po.StoreAddress;
import com.ershou.ershou.domain.query.StoreAddressPageQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 收货地址表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-11-06
 */
public interface StoreAddressMapper extends BaseMapper<StoreAddress> {

    Page<StoreAddressDto> getStoreAddressList(Page<StoreAddressDto> page,@Param("storeAddressQuery") StoreAddressPageQuery storeAddressPageQuery);
}
