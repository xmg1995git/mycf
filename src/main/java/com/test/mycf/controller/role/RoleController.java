package com.test.mycf.controller.role;

import com.test.mycf.service.role.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ASUS
 * @create 2020/7/10 - 23:18
 */
@RestController
public class RoleController {
    private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    @Resource
    private IRoleService roleService;





}
