package com.demo.sl.dao.authority;

import com.demo.sl.entity.Authority;

/**
 * 权限接口 有关功能的增删改查
 */
public interface IAuthorityDao {
    /**
     * 有条件的得到权限（角色-功能）
     * @param authority 条件
     * @return true获取成功
     */
    Authority getAuthority(Authority authority);

    /**
     * 根据条件删除权限表
     * @param authority 条件
     */
    void delAuthority(Authority authority);

    /**
     * 新增
     * @param authority 新增的内容
     */
    void addAuthority(Authority authority);
}
