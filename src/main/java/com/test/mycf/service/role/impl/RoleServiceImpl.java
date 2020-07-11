package com.test.mycf.service.role.impl;

import com.test.mycf.mapper.role.IRoleMapper;
import com.test.mycf.pojo.role.RoleDo;
import com.test.mycf.service.role.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ASUS
 * @create 2020/7/10 - 23:28
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Resource
    IRoleMapper roleMapper;

    @Override
    public Integer saveDefaultUserRole(RoleDo role) {
        return roleMapper.saveDefaultUserRole(role);
    }
}
