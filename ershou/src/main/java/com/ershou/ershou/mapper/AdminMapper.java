package com.ershou.ershou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.dto.AdminDto;
import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.query.AdminPageQuery;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper extends BaseMapper<Admin> {
    Page<AdminDto> getAdminList(Page<AdminDto> page, @Param("adminQuery") AdminPageQuery adminQuery);
}
