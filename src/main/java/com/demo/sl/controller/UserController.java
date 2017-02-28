package com.demo.sl.controller;

import com.demo.sl.entity.User;
import com.demo.sl.service.UserService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户控制层
 */
@Controller
public class UserController extends BaseController{
    private Logger logger=Logger.getLogger(UserController.class);
    @Resource
    private UserService userService;
    //修改密码
    @RequestMapping(path = "/backend/modifyPwd.html",method = RequestMethod.POST)
    @ResponseBody
    public String modifyPwd(String userJson, HttpServletRequest req){
        //判断前台是否传递过来数据
        if (userJson==null||userJson.equals("")){//数据为空
            return "nodata";
        }
        JSONObject jsonObject = JSONObject.fromObject(userJson);//将json字符串转换成json对象
        User user = (User)jsonObject.toBean(jsonObject, User.class);//将json对象转换成java对象
        //得到session对象
        HttpSession session = req.getSession();
        //得到的当前登录用户的信息
        User loginUser = (User)session.getAttribute("user");
        try{
            //得到当前用户在数据库中的所有信息
            User userMessage = userService.loginUserMessage(loginUser);
            if (userMessage!=null){//确定获取到用户信息
                //判断输入的原密码是否跟数据库中的密码一致
                if (!userMessage.getPassword().equals(user.getPassword())){
                    return "oldpwdwrong";
                }
                //将数据库数据更新
                User newUser=new User();
                newUser.setPassword(user.getPassword2());//设置新的密码
                newUser.setId(loginUser.getId());//更改的条件 当前用户的id
                //调用更改操作业务
                boolean b = userService.updatePwd(newUser);
                if (b==false){//更改失败
                    return "failed";
                }
            }

        }catch (Exception e){//捕获异常
            logger.info("modifyPwd修改密码================更改数据库操作错误======================");
            return "failed";
        }
        return "success";
    }
}
