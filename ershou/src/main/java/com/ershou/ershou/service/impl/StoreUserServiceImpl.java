package com.ershou.ershou.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ershou.ershou.domain.po.StoreUser;
import com.ershou.ershou.domain.wechat.WeChatSessionModel;
import com.ershou.ershou.mapper.StoreUserMapper;
import com.ershou.ershou.service.StoreUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ershou.ershou.utils.JwtUtils;
import com.ershou.ershou.utils.uuid.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author author
 * @since 2024-11-06
 */
@Service
@Slf4j
public class StoreUserServiceImpl extends ServiceImpl<StoreUserMapper, StoreUser> implements StoreUserService {
    @Value("${wx.appid}")
    private String appid;

    @Value("${wx.secret}")
    private String appsecret;

    @Autowired
    private RestTemplate restTemplate;

    // 用于存储用户信息和token
    Map<String,Object> map = new HashMap<>();

    @Autowired
    private StoreUserMapper storeUserMapper;

    @Autowired
    private JwtUtils jwtUtils;


    @Override
    public Map<String, Object> checkLogin(String code) {
        // 根据传入code，调用微信服务器，获取唯一openid
        // 微信服务器接口地址
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+ "&secret="+appsecret
                +"&js_code="+ code +"&grant_type=authorization_code";
        String errmsg = "";
        String errcode = "";
        String session_key = "";
        String openid = "";
        WeChatSessionModel weChatSessionModel;
        // 发送请求
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        // 判断请求是否成功
        if(responseEntity != null && responseEntity.getStatusCode() == HttpStatus.OK) {
            // 获取主要内容
            String sessionData = responseEntity.getBody();
            // 替换Gson部分代码
            // 原Gson代码：
            // Gson gson = new Gson();
            // weChatSessionModel = gson.fromJson(sessionData, WeChatSessionModel.class);
            
            // 改为Fastjson2：
            weChatSessionModel = JSON.parseObject(sessionData, WeChatSessionModel.class);
            log.info("返回的数据==>{}",weChatSessionModel);
            //获取用户的唯一标识openid
            openid = weChatSessionModel.getOpenid();
            //获取错误码
            errcode = weChatSessionModel.getErrcode();
            //获取错误信息
            errmsg = weChatSessionModel.getErrmsg();
        }else{
            log.info("出现错误，错误信息：{}",errmsg );
            map.put("errmsg",errmsg);
            return map;
        }
        // 判断是否成功获取到openid
        if ("".equals(openid) || openid == null){
            log.info("错误获取openid,错误信息:{}",errmsg);
            map.put("errmsg",errmsg);
            return map;
        }else {
            // 判断用户是否存在，查询数据库
            LambdaQueryWrapper<StoreUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(StoreUser::getWeixinOpenid, openid);
            StoreUser userInfo = storeUserMapper.selectOne(queryWrapper);
            // 不存在，加入数据表
            StoreUser storeUser = null;
            if (userInfo == null) {
                // 填充初始信息
                storeUser = new StoreUser();
                storeUser.setWeixinOpenid(openid);
                storeUser.setCreateTime(new Date());
                // 加入数据表
                storeUserMapper.insert(storeUser);
                // 加入map返回
                map.put("user", storeUser);
                // 调用自定义类封装的方法，创建token
                String token = jwtUtils.createUserToken(storeUser);
                map.put("token", token);
                return map;
            } else {
                // 存在，将用户信息加入map返回
                map.put("user", userInfo);
                String token = jwtUtils.createUserToken(storeUser);
                map.put("token", token);
                return map;
            }
        }
    }
}
