package com.ershou.ershou.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.dto.StoreUserCollectDto;
import com.ershou.ershou.domain.dto.StoreUserFootmarkDto;
import com.ershou.ershou.domain.po.StoreUserFootmark;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ershou.ershou.domain.query.StoreUserCollectPageQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-11-07
 */
public interface StoreUserFootmarkMapper extends BaseMapper<StoreUserFootmark> {

    Page<StoreUserFootmarkDto> getStoreUserFootmarkList(Page<StoreUserFootmarkDto> storeUserCollectDtoPage,@Param("storeUserCollectQuery") StoreUserCollectPageQuery storeUserCollectPageQuery);
}
