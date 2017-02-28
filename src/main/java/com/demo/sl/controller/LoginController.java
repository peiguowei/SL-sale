package com.demo.sl.controller;



import com.demo.sl.common.RedisAPI;
import com.demo.sl.entity.Function;
import com.demo.sl.entity.Menu;
import com.demo.sl.entity.User;
import com.demo.sl.service.FunctionService;
import com.demo.sl.service.UserService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *登录控制层 用于登录
 */
@Controller
public class LoginController extends BaseController {

    @Resource
    private UserService userService;
    @Resource
    private FunctionService functionService;
    @Resource
    private RedisAPI redisAPI;
    //引入日志
    private Logger logger=Logger.getLogger(LoginController.class);

    //登录操作
    @RequestMapping(path = "/login.html",method = RequestMethod.POST)
    @ResponseBody
    public String login(String user, HttpServletRequest req){
        if (user==null||"".equals(user)){
            return "nodata";
        }
        //将json字符串转换成json对象
        JSONObject jsonObject = JSONObject.fromObject(user);
        //将json对象转换成java对象
        User userOne=(User) JSONObject.toBean(jsonObject,User.class);
        //调用登录服务
        String result = userService.userLoginService(userOne,req);
        return result;
    }
    //登录成功跳转页面
    @RequestMapping(path = "/main.html",method = RequestMethod.GET)
    public ModelAndView mainPage(HttpServletRequest req){
        //得到session对象
        HttpSession session = req.getSession();
        //得到登录信息
        User user =(User)session.getAttribute("user");
        logger.debug("main==========================================");
        List<Menu> menuList=new ArrayList();//菜单列表结合
        if (user!=null){   //user不等于空 证明已经登录
            Map<String,Object>model = new HashMap();
            model.put("user",user);//用户的登录信息
            if (!redisAPI.exist("menuList"+user.getRoleId())){//第一次登陆 redis中没有数据
                //根据当前用户获取主功能菜单列表mList
                List<Function> mList = functionService.getFunctionMainMenuService(user.getRoleId());
                if(mList!=null){//主菜单列表不为空
                    for (Function function:mList){
                        Menu menu=new Menu();
                        menu.setMainMenu(function);
                        function.setRoleId(user.getRoleId());
                        //根据单个主功能信息得到子功能菜单列表
                        List<Function> subList = functionService.getFunctionSubMenuService(function);
                        if (subList!=null){//子菜单列表不为空
                            menu.setSubMenu(subList);
                        }
                        menuList.add(menu);//这个集合既有主功能又有子功能菜单
                    }
                    //json
                    JSONArray jsonArray = JSONArray.fromObject(menuList);//得到json对象
                    String jsonString = jsonArray.toString();//得到json字符串
                    logger.info("=============================================================================================="+jsonString);
                    model.put("menuList",jsonString);
                    redisAPI.set("menuList"+user.getRoleId(),jsonString);
                }
            }else {//redis有数据 直接从redis中拿去数据
                String redisMenuListString = redisAPI.get("menuList" + user.getRoleId());
                logger.info("=========================================================================================menuList from redis"+redisMenuListString);
                if (redisMenuListString!=null && !redisMenuListString.equals("")){
                    model.put("menuList",redisMenuListString);
                }else {
                    return new ModelAndView("redirect:/");
                }
            }
            session.setAttribute("functionMenu",model);
            return new ModelAndView("main",model);
        }
        return new ModelAndView("redirect:/");
    }
    //注销登录
    @RequestMapping(path = "/logout.html",method = RequestMethod.GET)
    public String logout(HttpServletRequest req){
        HttpSession session = req.getSession();//得到session对象
        session.removeAttribute("user");//清空session
        session.invalidate();//session失效
        return "index";
    }
}
