package com.demo.sl.controller;

import com.demo.sl.common.Constants;
import com.demo.sl.common.PageSupport;
import com.demo.sl.common.SQLTools;
import com.demo.sl.entity.DataDictionary;
import com.demo.sl.entity.Role;
import com.demo.sl.entity.User;
import com.demo.sl.service.data_dictionary.DataDictionaryService;
import com.demo.sl.service.role.RoleService;
import com.demo.sl.service.user.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
 * 用户控制层
 */
@Controller
public class UserController extends BaseController{
    private Logger logger=Logger.getLogger(UserController.class);
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private DataDictionaryService dataDictionaryService;
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
        User loginUser = (User)session.getAttribute(Constants.SESSION_USER);
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
    //得到用户列表的信息(分页)
    @RequestMapping("/backend/userlist.html")
    public ModelAndView userList(Model model,HttpServletRequest req,
                                    @RequestParam(required = false) Integer currentpage,
                                    @RequestParam(required = false) String s_loginCode,
                                    @RequestParam(required = false) String s_referCode,
                                    @RequestParam(required = false)String s_roleId,
                                    @RequestParam(required = false)String s_isStart){
        /*得到session的对象*/
        HttpSession session = req.getSession();
//        得到菜单列表
        Map<String, Object> baseModel = (Map<String, Object>) session.getAttribute(Constants.SESSION_BASE_MODEL);
        List<Role> roleList=null;
        List<DataDictionary> cardTypeList=null;
        if (baseModel==null){
            return new ModelAndView("redirect:/");
        }else {
            //创建datadictionary实例
            DataDictionary dataDictionary=new DataDictionary();
            dataDictionary.setTypeCode("CARD_TYPE");
            //调用查询数字字典的业务 目的：查询所有的证件类型
            try {
                cardTypeList = dataDictionaryService.getDataDictionaryListService(dataDictionary);
            }catch (Exception e){
                e.printStackTrace();
                logger.info("===================得到数据字典信息失败 ON.1====================");
            }
            try{
                //得到角色列表的信息（用户管理搜索栏）
                roleList = roleService.getRoleListService();
            }catch (Exception e){
                e.printStackTrace();
                logger.info("======================得到角色信息表失败===========");

            }
        }
        //模糊查询
        User user=new User();
        if (s_loginCode!=null){
            user.setLoginCode("%"+ SQLTools.transfer(s_loginCode)+"%");
        }
        if (s_referCode!=null){
            user.setReferCode("%"+SQLTools.transfer(s_referCode)+"%");
        }
        if (StringUtils.isNotEmpty(s_isStart)){//不是空的
            user.setIsStart(Integer.valueOf(s_isStart));
        }else{
            user.setIsStart(null);
        }
        if (StringUtils.isNotEmpty(s_roleId)){
            user.setRoleId(Integer.valueOf(s_roleId));
        }else {
            user.setRoleId(null);
        }
        //分页处理
        //创建pageSupport的实例
        PageSupport page=new PageSupport();
        //统计总共有多少条 两种情况1.有条件 2.无条件
        //调用查询总条数业务
        int count=0;
        try{
            count= userService.getUserCountService(user);
            page.setTotalCount(count);//设置总个数
        }catch (Exception e){
            e.printStackTrace();
            logger.info("=================查询得到总条数异常==============");
        }
        //分页导航条以及内容

        if (page.getTotalCount()>0){//总页数不为0
            if (currentpage!=null){//证明参数
                page.setPage(currentpage);
            }
            if (page.getPage()<=0){//防止恶意传参
                page.setPage(1);
            }
            if (page.getPage()>page.getPageCount()){
                page.setPage(page.getPageCount());
            }
            user.setStartNum((page.getPage()-1)*page.getPageSize());//分页查询起始行
            user.setPageSize(page.getPageSize());//显示多少行
            //调用查询内容的服务
            List<User> userList=null;
            try {
                userList = userService.getUserListService(user);
            }catch (Exception e){
                e.printStackTrace();
                logger.info("==================得到用户详细信息失败================");
            }
            page.setItems(userList);
        }else{
            page.setItems(null);
        }
        model.addAllAttributes(baseModel);
        model.addAttribute("page",page);
        model.addAttribute("s_loginCode",s_loginCode);
        model.addAttribute("s_referCode",s_referCode);
        model.addAttribute("s_roleId",s_roleId);
        model.addAttribute("s_isStart",s_isStart);
        model.addAttribute("roleList",roleList);
        model.addAttribute("cardTypeList",cardTypeList);
        return new ModelAndView("/backend/userlist");
    }
    @RequestMapping(path = "/backend/loadUserTypeList.html",produces = "text/html;charset=utf-8")
    @ResponseBody
    public Object getUserTypeList(@RequestParam(required = false)String s_role){
        String getJson="";
        try {
            DataDictionary dataDictionary=new DataDictionary();
            dataDictionary.setTypeCode("USER_TYPE");
            List<DataDictionary> userTypeList = dataDictionaryService.getDataDictionaryListService(dataDictionary);
            JSONArray jsonArray = JSONArray.fromObject(userTypeList);
            getJson= jsonArray.toString();
        }catch (Exception e){
            e.printStackTrace();
            logger.info("===================得到数据字典信息失败 ON.2====================");
        }
        return getJson;
    }
}
