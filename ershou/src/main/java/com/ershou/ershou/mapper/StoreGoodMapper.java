package com.ershou.ershou.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.dto.StoreGoodDto;
import com.ershou.ershou.domain.po.StoreGood;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ershou.ershou.domain.query.StoreGoodPageQuery;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-11-09
 */
public interface StoreGoodMapper extends BaseMapper<StoreGood> {

    Page<StoreGoodDto> getStoreGoodList(Page<StoreGoodDto> storeGoodDtoPage,@Param("storeGoodQuery") StoreGoodPageQuery storeGoodPageQuery);
}
