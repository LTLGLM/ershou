package com.ershou.ershou.service.impl;

import com.ershou.ershou.domain.dto.StoreCateDto;
import com.ershou.ershou.domain.po.StoreCate;
import com.ershou.ershou.mapper.StoreCateMapper;
import com.ershou.ershou.service.StoreCateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author author
 * @since 2024-11-10
 */
@Service
public class StoreCateServiceImpl extends ServiceImpl<StoreCateMapper, StoreCate> implements StoreCateService {

    @Autowired
    private StoreCateMapper storeCateMapperl;

    @Override
    public StoreCateDto getStoreCateByid(Integer storeCateId) {
        return storeCateMapperl.getStoreCateByid(storeCateId);
    }
}
