package com.demo.sl.controller;

import com.demo.sl.common.Constants;
import com.demo.sl.service.role.RoleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 权限控制层 用于进行权限管理
 */
@Controller
public class AuthorityManage extends BaseController{
    //获取日志
    private Logger log=Logger.getLogger(AuthorityManage.class);
    @Resource
    RoleService roleService;

    //打开权限管理页面
    @RequestMapping(path = "/backend/authoritymanage.html",method = {RequestMethod.POST,RequestMethod.GET})
    public ModelAndView authorityManage(HttpServletRequest req, Model model){
        //得到会话变量
        HttpSession session = req.getSession();
        //Constants.SESSION_BASE_MODEL 存的值 就是一个map类型
        Map<String,Object> baseModel=(Map<String, Object>) session.getAttribute(Constants.SESSION_BASE_MODEL);
        List roleList=null;
        if (baseModel==null){
            return new ModelAndView("redirect:/");
        }else {//得到角色列表信息
            try {
                //角色为启用状态
                roleList= roleService.getRoleListOfStartService();
            }catch (Exception e){
                roleList=null;
            }
        }
        model.addAllAttributes(baseModel);
        model.addAttribute(roleList);
        return new ModelAndView("/backend/authoritymanage");
    }
}
