package com.ershou.ershou.mapper;

import com.ershou.ershou.domain.dto.StoreCateDto;
import com.ershou.ershou.domain.po.StoreCate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author author
 * @since 2024-11-10
 */
public interface StoreCateMapper extends BaseMapper<StoreCate> {
    StoreCateDto getStoreCateByid(Integer storeCateId);
}
