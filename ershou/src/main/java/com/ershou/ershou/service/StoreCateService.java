package com.ershou.ershou.service;

import com.ershou.ershou.domain.dto.StoreCateDto;
import com.ershou.ershou.domain.po.StoreCate;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author author
 * @since 2024-11-10
 */
public interface StoreCateService extends IService<StoreCate> {

    StoreCateDto getStoreCateByid(Integer storeCateId);
}
