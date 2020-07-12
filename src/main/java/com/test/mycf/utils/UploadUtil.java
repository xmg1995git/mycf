package com.test.mycf.utils;

import com.test.mycf.common.UploadCommon;
import com.test.mycf.exception.MyException;
import com.test.mycf.exception.ResultEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author ASUS
 * @create 2020/7/10 - 23:58
 */
@Component
public class UploadUtil {

    public static final byte B_100 = (byte)100;
    public static final String REGEX = "\\.";
    public static final String DIAN = ".";
    public static final byte B_1 = (byte)1;

    public String upload(MultipartFile file, String account) throws MyException{
        if (file.isEmpty()) {
            throw new MyException(ResultEnum.UPLOAD_FILE_NULL);
        }
        // 获取原文件名
        String fileName = file.getOriginalFilename();
        String[] split = fileName.split(REGEX);
        fileName = new StringBuilder()
                .append(account)
                .append(System.currentTimeMillis()% B_100)
                .append(DIAN)
                .append(split[split.length - B_1]).toString();
        File filePath = new File(UploadCommon.PATH+fileName);
        try {
            file.transferTo(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回图片路径
        return fileName;
    }








}
