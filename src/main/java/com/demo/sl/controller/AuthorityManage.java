package com.demo.sl.controller;

import com.demo.sl.common.Constants;
import com.demo.sl.entity.Function;
import com.demo.sl.entity.RoleFunctions;
import com.demo.sl.service.function.FunctionService;
import com.demo.sl.service.role.RoleService;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
    @Resource
    FunctionService functionService;

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
    //得到菜单列表
    @RequestMapping(path = "/backend/functions.html",produces = "text/html;charset='utf-8'")
    @ResponseBody
    public Object functionMenu(){
        String resultString="nodata";
        Function function = new Function();
        try {
            //得到主菜单列表
            function.setId(0);
            List<Function> menuList = functionService.getFunctionMenuListService(function);
            List<RoleFunctions> lists=new ArrayList();
            if (menuList!=null){
                for (Function func:menuList){
                    //得到列表实体对象
                    RoleFunctions roleFunctions = new RoleFunctions();
                    roleFunctions.setMainFunction(func);
                    roleFunctions.setSubFunctionList(functionService.getFunctionMenuListService(func));
                    lists.add(roleFunctions);
                }
                //将list集合转换成json对象再转化成json字符串
                resultString= JSONArray.fromObject(lists).toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("============================得到菜单列表失败:"+e.getMessage()+"===========================================");
        }
        return resultString;
    }
}
