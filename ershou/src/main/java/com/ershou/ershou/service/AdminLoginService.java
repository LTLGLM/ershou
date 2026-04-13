package com.ershou.ershou.service;


import com.ershou.ershou.domain.request.LoginBody;
import com.ershou.ershou.domain.dto.AdminInfoDto;
import com.ershou.ershou.domain.dto.LoginDto;

import javax.servlet.http.HttpServletRequest;

public interface AdminLoginService {
    LoginDto login(LoginBody loginBody, HttpServletRequest request);

    void logout(HttpServletRequest request);

    AdminInfoDto getAdminInfo();
}
