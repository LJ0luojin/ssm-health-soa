package com.lh.dao;

import com.lh.pojo.Permission;

public interface PermissionDao {
    Permission findByPermissionId(Integer id);
}
