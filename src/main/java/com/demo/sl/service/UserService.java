package com.demo.sl.service;

import com.demo.sl.dao.IUserDao;
import com.demo.sl.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

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
        session.setAttribute("user",loginUser);
        //更新数据库 lastLoginTime
        User userTwo=new User();
        userTwo.setId(loginUser.getId());//更改的条件
        userTwo.setLastLoginTime(new Date());//将登录时间改为最新的
        userDao.updateUserTable(userTwo);//调用更改数据库操作的方法
        return "success";
    }
}
