package com.ershou.ershou.controller;

import com.ershou.ershou.domain.wechat.WeChatModel;
import com.ershou.ershou.service.StoreUserService;
import com.ershou.ershou.utils.AjaxResult;
import com.ershou.ershou.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class StoreUserLoginController {
    
    @Autowired
    private StoreUserService storeUserService;
    
    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody WeChatModel weChatModel, HttpServletResponse response) {
        // 检查登录
        Map<String, Object> resultMap = storeUserService.checkLogin(weChatModel.getCode());
        // resultMap大于1为通过，业务层判断正确后返回用户信息和token，所以应该size为2才正确。
        if (resultMap.size() > 1) {
            log.info("创建的token为=>{}", resultMap.get("token"));
            // 将token添加入响应头以及返回用户信息
            response.setHeader("token", (String) resultMap.get("token"));
            return AjaxResult.success(resultMap.get("user").toString());
        } else {
            // 当返回map的size为1时，即为报错信息
            return AjaxResult.error(resultMap.get("errmsg").toString());
        }
    }
}
