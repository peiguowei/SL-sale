package com.demo.sl.controller;

import com.demo.sl.common.Constants;
import com.demo.sl.common.RedisAPI;
import com.demo.sl.entity.*;
import com.demo.sl.service.authority.AuthorityService;
import com.demo.sl.service.function.FunctionService;
import com.demo.sl.service.role.RoleService;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 权限控制层 用于进行权限管理
 * @author  peizi
 * @date 2018-3-23
 */
@Controller
public class AuthorityController extends BaseController{
    /**
     * 获取日志
     */
    private Logger log=Logger.getLogger(AuthorityController.class);
    @Resource
    RoleService roleService;
    @Resource
    FunctionService functionService;
    @Resource
    AuthorityService authorityService;
    @Resource
    LoginController loginController;
    @Resource
    RedisAPI redisAPI;

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
    //得到权限回显状态
    @RequestMapping(path = "/backend/getAuthorityDefault.html",produces = "text/html;charset='utf-8'")
    @ResponseBody
    public String getDefaultState(@RequestParam Integer rid,@RequestParam Integer fid){
        String resultString="nodata";
        try {
            Authority authority = new Authority();
            authority.setRoleId(rid);
            authority.setFunctionId(fid);
            if (authorityService.getAuthorityService(authority)!=null){
                return "success";
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("==============================回显失败原因"+e.getMessage()+"=======================================");
        }
        return resultString;
    }
    @RequestMapping(path = "/backend/modifyAuthority.html",produces = {"text/html;charset=UTF-8"})
    @ResponseBody
    public String modifyAuthority(HttpServletRequest req,@RequestParam String ids){
        String resultString="nodata";
        try {
            //判断ids不为空
            if (ids!=null){
                //授权规则:先删除后修改==事务
                String[] idsArrayString= StringUtils.split(ids,"-");
                if (idsArrayString.length>0){
                    //得到当前的登录用户
                    HttpSession session = req.getSession();
                    User user = (User) session.getAttribute(Constants.SESSION_USER);
                    //调用授权业务
                    authorityService.pz_addAuthorityService(idsArrayString,user.getLoginCode());

                    //存放到redis缓存中 权限已修改加载新的权限
                    List<Menu> menuList = loginController.getFuncByCurrentUser(Integer.parseInt(idsArrayString[0]));
                    JSONArray jsonArray = JSONArray.fromObject(menuList);
                    redisAPI.set("menuList"+idsArrayString[0],jsonArray.toString());

                    //得到role 拥有功能的 url to redis
                    Authority authority = new Authority();
                    authority.setRoleId(Integer.valueOf(idsArrayString[0]));

                }
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("===============================授权失败："+e.getMessage()+"=============================");
        }
        return resultString;
    }
}
