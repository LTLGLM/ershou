package com.ershou.ershou.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ershou.ershou.domain.PageResult;
import com.ershou.ershou.domain.po.LoginInfo;
import com.ershou.ershou.domain.query.LoginInfoPageQuery;
import com.ershou.ershou.service.LoginInfoService;
import com.ershou.ershou.utils.AjaxResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/log")
@Api(tags = "日志接口")
public class LoginInfoController {

    @Autowired
    private LoginInfoService loginInfoService;

    @PreAuthorize("hasAuthority('systemmanager:log:list')")
    @GetMapping("/list")
    public AjaxResult getLoginInfoList(LoginInfoPageQuery loginInfoPageQuery){
        Page<LoginInfo> loginInfoPage = new Page<>(loginInfoPageQuery.getPageNum(), loginInfoPageQuery.getPageSize());
        LambdaQueryWrapper<LoginInfo> loginInfoLambdaQueryWrapper = new LambdaQueryWrapper<LoginInfo>().eq(LoginInfo::getDelFlag, 0);
        if(loginInfoPageQuery.getLoginLocation() != null && !loginInfoPageQuery.getLoginLocation().isEmpty()){
            loginInfoLambdaQueryWrapper.like(LoginInfo::getLoginLocation, loginInfoPageQuery.getLoginLocation());
        }
        if(loginInfoPageQuery.getUsername() != null && !loginInfoPageQuery.getUsername().isEmpty()){
            loginInfoLambdaQueryWrapper.like(LoginInfo::getUsername, loginInfoPageQuery.getUsername());
        }
        if(loginInfoPageQuery.getStatus() != null){
            loginInfoLambdaQueryWrapper.eq(LoginInfo::getStatus, loginInfoPageQuery.getStatus());
        }
        if(loginInfoPageQuery.getStarttime() != null && !loginInfoPageQuery.getStarttime().isEmpty()){
            loginInfoLambdaQueryWrapper.ge(LoginInfo::getLoginTime, loginInfoPageQuery.getStarttime());
        }
        if(loginInfoPageQuery.getEndtime() != null && !loginInfoPageQuery.getEndtime().isEmpty()){
            loginInfoLambdaQueryWrapper.le(LoginInfo::getLoginTime, loginInfoPageQuery.getEndtime());
        }
        Page<LoginInfo> page = loginInfoService.page(loginInfoPage, loginInfoLambdaQueryWrapper);
        PageResult<LoginInfo> loginInfoPageResult = new PageResult<>(page);

        return AjaxResult.success(loginInfoPageResult);
    }

    @DeleteMapping()
    @PreAuthorize("hasAuthority('systemmanager:log:deletes')")
    public AjaxResult deleteLoginInfoByIds(@RequestBody List<Integer> ids){
        LambdaUpdateWrapper<LoginInfo> loginInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<LoginInfo>().in(LoginInfo::getLogId, ids).set(LoginInfo::getDelFlag, 1);
        boolean removeLoginInfosResult = loginInfoService.update(loginInfoLambdaUpdateWrapper);

        if(!removeLoginInfosResult){
            return AjaxResult.error("删除登陆信息失败");
        }

        return AjaxResult.success("删除登陆信息成功");
    }

    @DeleteMapping("/all")
    public AjaxResult deleteAllLoginInfos(){
        LambdaUpdateWrapper<LoginInfo> loginInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<LoginInfo>().set(LoginInfo::getDelFlag, 1);
        boolean removeAllLoginInfosResult = loginInfoService.update(loginInfoLambdaUpdateWrapper);

        if(!removeAllLoginInfosResult){
            return AjaxResult.error("删除所有登陆信息失败");
        }

        return AjaxResult.success("删除所有登陆信息成功");
    }
}
