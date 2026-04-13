package com.ershou.ershou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ershou.ershou.domain.po.LoginInfo;
import com.ershou.ershou.domain.request.LoginBody;

import javax.servlet.http.HttpServletRequest;

public interface LoginInfoService extends IService<LoginInfo> {
    void insertLoginInfo(LoginBody loginBody,HttpServletRequest request,Integer status,String msg);
}
