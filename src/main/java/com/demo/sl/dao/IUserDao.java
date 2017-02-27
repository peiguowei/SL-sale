package com.demo.sl.dao;

import com.demo.sl.entity.User;

/**
 *用户接口 待实现的用户增删查改
 */
public interface IUserDao {
    /**
     * 用户登录验证
     * @param loginCode 待验证的登录账户
     * @return null用户不存在 非null 登录用户的所有信息
     */
    User userLogin(String loginCode);

    /**
     * 更改用户的数据表
     * @param user 待更改的数据跟更改条件
     */
    void updateUserTable(User user);
}
