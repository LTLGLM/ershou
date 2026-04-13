package com.ershou.ershou.service.impl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ershou.ershou.domain.dto.AdminDto;
import com.ershou.ershou.domain.po.Admin;
import com.ershou.ershou.domain.query.AdminPageQuery;
import com.ershou.ershou.mapper.AdminMapper;
import com.ershou.ershou.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Page<AdminDto> getAdminList(AdminPageQuery adminPageQuery) {
        Page<AdminDto> page = new Page<>(adminPageQuery.getPageNum(), adminPageQuery.getPageSize());
        Page<AdminDto> adminList = adminMapper.getAdminList(page,adminPageQuery);
        return adminList;
    }
}
