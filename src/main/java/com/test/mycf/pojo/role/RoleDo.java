package com.test.mycf.pojo.role;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author ASUS
 * @create 2020/7/7 - 8:40
 */
@Data
public class RoleDo {

    private String account;

    private String role;

    private String code;

    private String createAccount;

    private String status;

    // 创建时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    // 修改时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

    public RoleDo() {
    }

    public RoleDo(String account, String createAccount, Date createTime) {
        this.account = account;
        this.createAccount = createAccount;
        this.createTime = createTime;
    }
}
