package com.ershou.ershou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.dto.StoreGoodDto;
import com.ershou.ershou.domain.po.StoreGood;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ershou.ershou.domain.query.StoreGoodPageQuery;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author author
 * @since 2024-11-09
 */
public interface StoreGoodService extends IService<StoreGood> {

    Page<StoreGoodDto> getStoreGoodList(StoreGoodPageQuery storeGoodPageQuery);
}
