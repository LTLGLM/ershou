package com.ershou.ershou.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.dto.StoreUserCollectDto;
import com.ershou.ershou.domain.dto.StoreUserFootmarkDto;
import com.ershou.ershou.domain.po.StoreUserFootmark;
import com.ershou.ershou.domain.query.StoreUserCollectPageQuery;
import com.ershou.ershou.mapper.StoreUserFootmarkMapper;
import com.ershou.ershou.service.StoreUserFootmarkService;
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
public class StoreUserFootmarkServiceImpl extends ServiceImpl<StoreUserFootmarkMapper, StoreUserFootmark> implements StoreUserFootmarkService {

    @Autowired
    private StoreUserFootmarkMapper storeUserFootmarkMapper;

    @Override
    public Page<StoreUserFootmarkDto> getStoreUserFootmarkList(StoreUserCollectPageQuery storeUserCollectPageQuery) {
        Page<StoreUserFootmarkDto> storeUserCollectDtoPage = new Page<>(storeUserCollectPageQuery.getPageNum(), storeUserCollectPageQuery.getPageSize());
        return storeUserFootmarkMapper.getStoreUserFootmarkList(storeUserCollectDtoPage,storeUserCollectPageQuery);
    }
}
