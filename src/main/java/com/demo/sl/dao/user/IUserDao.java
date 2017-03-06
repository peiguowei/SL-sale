package com.demo.sl.dao.user;

import com.demo.sl.entity.User;

import java.util.List;

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
    boolean updateUserTable(User user);

    /**
     * 用户表au_user表的总条数
     * @param user 查询条件
     * @return
     */
    int userCount(User user);

    /**
     * 得到user表的内容
     * @param user 1.有条件2.可无条件
     * @return
     */
    List<User> getUserList(User user);
}
