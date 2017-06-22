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

    /**
     * 判断用户名是否重复
     * @param user 查询条件
     * @return 0 only 1 重复
     */
    int getLoginCodeIsExit(User user);

    /**
     * 添加用户到数据表
     * @param user 待添加的数据
     */
    void addUser(User user);

    /**
     * 删除上传的图片
     * @param user 删除的条件
     * @return true 删除成功  false 删除失败
     */
    boolean delpic(User user);

    /**
     * 根据id得到user表的信息
     * @param id 条件
     * @return
     */
    User getUser(Integer id);

    /**
     * 删除user表中的信息
     * @param user 删除的条件
     * @return true 删除成功 false 删除失败
     */
    Boolean delUser(User user);
}
