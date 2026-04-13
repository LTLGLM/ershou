package com.ershou.ershou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.dto.StoreUserCollectDto;
import com.ershou.ershou.domain.dto.StoreUserFootmarkDto;
import com.ershou.ershou.domain.po.StoreUserFootmark;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ershou.ershou.domain.query.StoreUserCollectPageQuery;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-11-07
 */
public interface StoreUserFootmarkService extends IService<StoreUserFootmark> {

    Page<StoreUserFootmarkDto> getStoreUserFootmarkList(StoreUserCollectPageQuery storeUserCollectPageQuery);
}
