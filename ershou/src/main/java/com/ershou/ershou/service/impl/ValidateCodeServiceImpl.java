package com.ershou.ershou.service.impl;

import com.ershou.ershou.constant.RedisConstants;
import com.ershou.ershou.exception.GeneralBusinessException;
import com.ershou.ershou.service.ValidateCodeService;
import com.ershou.ershou.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateCodeServiceImpl implements ValidateCodeService {
    @Autowired
    private RedisCache redisCache;
    @Override
    public Boolean check(String uuid, Integer code) {
        if(!redisCache.hasKey(RedisConstants.CAPTCHA_CODE_KEY + uuid)){
            throw new GeneralBusinessException("验证码失效");
        }
        Object redisValue = redisCache.getCacheObject(RedisConstants.CAPTCHA_CODE_KEY + uuid);

        if(redisValue == null){
            return false;
        }
        return redisValue.equals(code);
    }
}
