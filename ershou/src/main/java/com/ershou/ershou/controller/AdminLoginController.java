package com.ershou.ershou.controller;

import com.ershou.ershou.domain.request.LoginBody;
import com.ershou.ershou.domain.dto.AdminInfoDto;
import com.ershou.ershou.domain.dto.LoginDto;
import com.ershou.ershou.service.AdminLoginService;
import com.ershou.ershou.service.AdminService;
import com.ershou.ershou.service.LoginInfoService;
import com.ershou.ershou.utils.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录
 */
@RestController()
@RequestMapping("/admin")
@Api(tags = "管理端登录服务")
public class AdminLoginController {

    @Autowired
    private AdminLoginService adminLoginServlet;

    @Autowired
    private LoginInfoService loginInfoService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    @ApiOperation(value="PC登录")
    public AjaxResult login(@RequestBody LoginBody loginBody, HttpServletRequest request) {
        LoginDto loginDto = adminLoginServlet.login(loginBody,request);
        return AjaxResult.success("登录成功", loginDto);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "PC登出")
    public AjaxResult logout(HttpServletRequest request) {
        adminLoginServlet.logout(request);
        return AjaxResult.success("退出登录");
    }

    @GetMapping("/info")
    @ApiOperation("获取用户详情")
    @ApiImplicitParam(name = "Token",required = true,dataType = "String",paramType = "header")
    public AjaxResult getAdminInfo(){
        AdminInfoDto adminInfo = adminLoginServlet.getAdminInfo();
        return AjaxResult.success(adminInfo);
    }
}
