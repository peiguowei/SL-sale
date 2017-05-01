package com.demo.sl.service.user;

import com.demo.sl.common.Constants;
import com.demo.sl.dao.user.IUserDao;
import com.demo.sl.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 用户业务操作层
 */
@Service
public class UserService {
    @Resource
    private IUserDao userDao;

    /**
     * 用户登录验证
     * @param user 待验证的登录信息
     * @param req 保存存在的用户信息
     * @return 回调的字符串 在前台进行判断
     */
    public String userLoginService(User user, HttpServletRequest req){
        User loginUser = userDao.userLogin(user.getLoginCode());
        if (loginUser==null){
            return "nologincode";//登录账户不存在
        }
        if (!user.getPassword().equals(loginUser.getPassword())){
            return "pwderror";//登录密码错误
        }
        //得到会话变量 存储用户的信息
        HttpSession session = req.getSession();
        loginUser.setPassword("");
        session.setAttribute(Constants.SESSION_USER,loginUser);
        //更新数据库 lastLoginTime
        User userTwo=new User();
        userTwo.setId(loginUser.getId());//更改的条件
        userTwo.setLastLoginTime(new Date());//将登录时间改为最新的
        userDao.updateUserTable(userTwo);//调用更改数据库操作的方法
        return "success";
    }

    /**
     * 获得登录用户的所有信息
     * @param user 现在登录的用户的各别信息
     * @return 非null 用户的所有信息
     */
    public User loginUserMessage(User user){
        return userDao.userLogin(user.getLoginCode());
    }

    /**
     * 更改当前登录用户的密码
     * @param user 包含登录的条件
     * @return true成功 false 失败
     */
    public boolean updatePwd(User user){
        return userDao.updateUserTable(user);
    }

    /**
     * 得到user表有几条数据
     * @param user 可以为空 为空代表无条件查询 否则有条件的查询
     * @return
     */
    public int getUserCountService(User user){
        return userDao.userCount(user);
    }

    /**
     * 查询每页显示的内容
     * @param user 查询条件
     * @return 非null 内容
     */
    public List<User> getUserListService(User user){
        return userDao.getUserList(user);
    }

    /**
     * 判断输入的用户名是否已存在
     * @param user 查询条件
     * @return 存在 返回0  非0不存在
     */
    public int getLoginCodeIsExitService(User user){
        return userDao.getLoginCodeIsExit(user);
    }

    /**
     * 添加用户数据业务
     * @param user 待添加的数据
     */
    public void getAddUserService(User user){
        userDao.addUser(user);
    }

    /**
     * 删除用户上传的图片业务
     * @param user 删除的条件
     * @return true 删除成功
     */
    public boolean delpicService(User user){
        return userDao.delpic(user);
    }

    /**
     * 得到用户的信息
     * @param user 条件（id）
     * @return 返回user对象
     */
    public User getUserService(User user){
        return userDao.getUser(user.getId());
    }
}
