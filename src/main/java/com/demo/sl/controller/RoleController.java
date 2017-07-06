package com.demo.sl.controller;


import com.demo.sl.common.Constants;
import com.demo.sl.entity.Role;
import com.demo.sl.entity.User;
import com.demo.sl.service.role.RoleService;
import com.demo.sl.service.user.UserService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *角色管理
 */
@Controller
public class RoleController extends BaseController{
    private Logger logger=Logger.getLogger(RoleController.class);//日志
    @Resource
    private RoleService roleService;//角色业务层
    @Resource
    private UserService userService;//用户业务层
//    得到角色列表
    @RequestMapping(path = "/backend/rolelist.html")
    public ModelAndView roleList(HttpServletRequest req,Model model){
//        得到会话变量
        HttpSession session = req.getSession();
//        得到菜单
        Map<String,Object>baseModel= (Map<String,Object>)session.getAttribute(Constants.SESSION_BASE_MODEL);
//        判断是否为空
        if (baseModel==null){
            return new ModelAndView("redirect:/");//为空返回登录界面
        }else {
            List<Role> roleList=null;
            try {
//                调用获取角色列表的业务
                roleList=roleService.getRoleListService();
            }catch (Exception e){
                e.printStackTrace();
                logger.info("----------------------获取角色列表失败--------------------------------");
            }
            model.addAllAttributes(baseModel);
            model.addAttribute(roleList);
            return new ModelAndView("/backend/rolelist");
        }

    }
//    添加角色
    @RequestMapping(path = "/backend/addRole.html",method = RequestMethod.POST)
    @ResponseBody
    public String addRole(HttpServletRequest req,@RequestParam String role){
        if (role==null||role.equals("")){
            return "nodata";//无需处理
        }else {
//            解析json
            JSONObject jsonObject = JSONObject.fromObject(role);//将json字符串转换成json对象
            Role roleValue= (Role)jsonObject.toBean(jsonObject, Role.class);//将json对象转换成java对象
            roleValue.setIsStart(1);
            roleValue.setCreateDate(new Date());
//            得到会话变量
            HttpSession session = req.getSession();
            roleValue.setCreatedBy(((User)session.getAttribute(Constants.SESSION_USER)).getLoginCode());
            try {
//                判断角色名与角色代码是否重复 调用判断业务
                if (roleService.getRoleByServices(roleValue)!=null){
                    return "rename";
                }else {
//                    调用添加业务
                    roleService.addRoleService(roleValue);
                }
            }catch (Exception e){
                e.printStackTrace();
                logger.info("=====================添加角色失败======================");
                return "failed";
            }
        }
        return "success";
    }
//    删除角色
    @RequestMapping(path = "/backend/delRole.html",method =RequestMethod.POST)
    @ResponseBody
    public String delRole(HttpServletRequest req,@RequestParam String role){
        if (role==null || role.equals(" ")){
            return "nodata";
        }else {
            JSONObject roleObject = JSONObject.fromObject(role);//得到json对象
            Role roles=(Role)JSONObject.toBean(roleObject,Role.class);//得到java对象
            try {
//                删除的原则 该角色下没有用户
                User user = new User();
                user.setRoleId(roles.getId());
//                调用查询业务
                List<User> userList = userService.getUserDataBySearchService(user);
                if (userList==null||userList.size()==0){
//                    角色下没有用户 允许删除
                    roleService.deleteRoleService(roles);
                }else {
                    String flag="";
                    for (int i=0;i<userList.size();i++){
                        flag+=userList.get(i).getLoginCode();
                        flag+=",";
                    }
                    return flag;
                }
            }catch (Exception e){
                e.printStackTrace();
                logger.info("-----------------------删除角色失败-----------------------");
                return "failed";
            }
            return "success";
        }
    }
//    修改角色与启用
    @RequestMapping(path = "/backend/modifyRole.html",method = RequestMethod.POST)
    @ResponseBody
    public String modifyRole(HttpServletRequest req,@RequestParam String role){
        if (role==null || role.equals("")){
            return "nodata";
        }else {
            JSONObject roleObject = JSONObject.fromObject(role);//得到json对象
            Role roles=(Role) JSONObject.toBean(roleObject,Role.class);//得到java对象
            roles.setCreateDate(new Date());
//            得到会话变量
            HttpSession session = req.getSession();
            roles.setCreatedBy(((User)session.getAttribute(Constants.SESSION_USER)).getLoginCode());
            try {
//                调用修改业务
                roleService.pz_modifyRoleService(roles);
            }catch (Exception e) {
                e.printStackTrace();
                logger.info("-----------------------修改或者启用失败----------------------");
                return "failed";
            }
                return "success";
        }
    }

}
