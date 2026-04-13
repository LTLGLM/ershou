package com.ershou.ershou.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ershou.ershou.domain.dto.AdminDto;
import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.query.AdminPageQuery;

public interface AdminService extends IService<Admin> {
    Page<AdminDto> getAdminList(AdminPageQuery adminPageQuery);
}
