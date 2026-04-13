package com.ershou.ershou.controller;

import com.ershou.ershou.constant.Constants;
import com.ershou.ershou.constant.RedisConstants;
import com.ershou.ershou.utils.AjaxResult;
import com.ershou.ershou.utils.RedisCache;
import com.ershou.ershou.utils.uuid.IdUtils;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@Api(tags="验证码服务")
public class CaptchaController {
    private static final Logger log = LoggerFactory.getLogger(CaptchaController.class);
    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private RedisCache redisCache;

    @GetMapping("/common/kaptcha")
    @ApiOperation(value="获取验证码图片")
    public AjaxResult getCaptchaCode(){
        AjaxResult ajax = AjaxResult.success();

        // 生成随机的加法题目
        Random random = new Random();
        int num1 = random.nextInt(10); // 生成0到9的随机数
        int num2 = random.nextInt(10); // 生成0到9的随机数

        // 保存加法结果用于验证
        int result = num1 + num2;

        // 生成加法验证码的字符串
        String code = String.format("%d + %d =", num1, num2);

        // 生成验证码的图像
        BufferedImage image = defaultKaptcha.createImage(code);

        // 生成简化uuid
        String uuid = IdUtils.simpleUUID();
        String verifyKey = RedisConstants.CAPTCHA_CODE_KEY + uuid;

        // 将验证码存储到redis中
        redisCache.setCacheObject(verifyKey,result, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();

        try {
            ImageIO.write(image,"jpg",os);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        ajax.put("uuid",uuid);
        ajax.put("image", Base64.getEncoder().encodeToString(os.toByteArray()));
        return ajax;
    }
}
