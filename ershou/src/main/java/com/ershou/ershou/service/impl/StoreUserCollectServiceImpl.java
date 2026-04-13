package com.ershou.ershou.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.dto.StoreUserCollectDto;
import com.ershou.ershou.domain.po.StoreUserCollect;
import com.ershou.ershou.domain.query.StoreUserCollectPageQuery;
import com.ershou.ershou.mapper.StoreUserCollectMapper;
import com.ershou.ershou.service.StoreUserCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-11-07
 */
@Service
public class StoreUserCollectServiceImpl extends ServiceImpl<StoreUserCollectMapper, StoreUserCollect> implements StoreUserCollectService {
    @Autowired
    private StoreUserCollectMapper storeUserCollectMapper;

    @Override
    public Page<StoreUserCollectDto> getStoreUserCollectList(StoreUserCollectPageQuery storeUserCollectPageQuery) {
        Page<StoreUserCollectDto> storeUserCollectDtoPage = new Page<>(storeUserCollectPageQuery.getPageNum(), storeUserCollectPageQuery.getPageSize());
        return storeUserCollectMapper.getStoreUserCollectList(storeUserCollectDtoPage, storeUserCollectPageQuery);
    }
}
