package com.ershou.ershou.controller;

import com.ershou.ershou.exception.GeneralBusinessException;
import com.ershou.ershou.utils.AjaxResult;
import com.ershou.ershou.utils.uuid.UUID;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/upload")
@Api(tags = "上传文件服务")
public class UploadController {

    @Value("${SHTP.image_upload_path}")
    private String UploadDir;

    @PostMapping()
    @ApiOperation("上传文件接口")
    public AjaxResult uploadFile(MultipartFile file) throws IOException {
        // 判断文件是否为空
        if (file == null || file.isEmpty()) {
            return AjaxResult.error("上传文件不能为空");
        }

        try {

            File uploadDir = new File(UploadDir);
            // 如果存储地址不存在，创建存储地址
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            // 获取文件名，没有文件名用UUID替代
            String originalFilename = file.getOriginalFilename();
            if (originalFilename == null) {
                originalFilename = UUID.randomUUID().toString();
            }

            // 拼接好需要存储的地址
            File fileNameDir = new File(uploadDir, originalFilename);
            // 将文件写入地址
            file.transferTo(fileNameDir);
            // 返回文件的存储地址
            return AjaxResult.success("上传成功！", "/images/" + originalFilename);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.error("文件上传失败！");
        }

    }
}
