package com.ershou.ershou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ershou.ershou.domain.po.LoginInfo;
import com.ershou.ershou.domain.request.LoginBody;
import com.ershou.ershou.mapper.LoginInfoMapper;
import com.ershou.ershou.service.LoginInfoService;
import com.ershou.ershou.utils.GetLoginInfo;
import com.ershou.ershou.utils.SecurityAdminUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class LoginInfoServiceImpl extends ServiceImpl<LoginInfoMapper, LoginInfo> implements LoginInfoService {

    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Override
    public void insertLoginInfo(LoginBody loginBody, HttpServletRequest request, Integer status, String msg) {
        LoginInfo loginInfo = new LoginInfo();
        if (loginBody != null) {
            loginInfo.setUsername(loginBody.getUsername());
        } else {
            loginInfo.setUsername(SecurityAdminUtils.getAdmin().getUsername());
        }
        loginInfo.setLoginLocation(GetLoginInfo.getIp(request));
        loginInfo.setBrowser(GetLoginInfo.getBrowser(request));
        loginInfo.setOs(GetLoginInfo.getOs((request)));
        loginInfo.setStatus(status);
        loginInfo.setMsg(msg);
        loginInfo.setLoginTime(new Date());
        int insertLoginInfoResult = loginInfoMapper.insert(loginInfo);
    }
}
