package com.test.mycf.utils;

import com.test.mycf.common.UploadCommon;
import com.test.mycf.exception.MyException;
import com.test.mycf.exception.ResultEnum;
import com.test.mycf.pojo.ResponseInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;

/**
 * @author ASUS
 * @create 2020/7/10 - 23:58
 */
@Component
public class UploadUtil {
    public String upload(MultipartFile file, String account) throws MyException{
        if (file.isEmpty()) {
            throw new MyException(ResultEnum.UPLOAD_FILE_NULL);
        }
        // 获取原文件名
        String fileName = file.getOriginalFilename();
        String[] split = fileName.split("\\.");
        fileName = new StringBuilder()
                .append(account)
                .append(System.currentTimeMillis()%100)
                .append(".")
                .append(split[split.length - 1]).toString();
        File filePath = new File(UploadCommon.PATH+fileName);
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回图片路径
        return filePath.toString();
    }








}
