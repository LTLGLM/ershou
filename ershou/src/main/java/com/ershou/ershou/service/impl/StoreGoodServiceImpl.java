package com.ershou.ershou.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.dto.StoreGoodDto;
import com.ershou.ershou.domain.po.StoreGood;
import com.ershou.ershou.domain.query.StoreGoodPageQuery;
import com.ershou.ershou.mapper.StoreGoodMapper;
import com.ershou.ershou.service.StoreGoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-11-09
 */
@Service
public class StoreGoodServiceImpl extends ServiceImpl<StoreGoodMapper, StoreGood> implements StoreGoodService {

    @Autowired
    private StoreGoodMapper storeGoodMapper;

    @Override
    public Page<StoreGoodDto> getStoreGoodList(StoreGoodPageQuery storeGoodPageQuery) {
        Page<StoreGoodDto> storeGoodDtoPage = new Page<>(storeGoodPageQuery.getPageNum(), storeGoodPageQuery.getPageSize());
        return storeGoodMapper.getStoreGoodList(storeGoodDtoPage,storeGoodPageQuery);
    }
}
