package com.demo.sl.controller;

import com.demo.sl.common.Constants;
import com.demo.sl.common.JsonDateValueProcessor;
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
import net.sf.json.JsonConfig;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
//   根据数据字典获取用户类型
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
//    判断用户名是否已存在
    @RequestMapping(path = "/backend/logincodeisexit.html",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getLoginCodeIsExit(@RequestParam(required = false)String loginCode,
                                     @RequestParam(required = false)String id ){
        logger.info("=================得到输入的用户==============="+loginCode);
        logger.info("=================得到传入的id================="+id);
        String result="failed";
        User user=new User();
        user.setLoginCode(loginCode);
        if (!id.equals("-1")){//修改用户操作 判断输入用户是否存在
            user.setId(Integer.valueOf(id));
        }
        try{
            int num = userService.getLoginCodeIsExitService(user);
            if (num==0){
                result="only";
            }else {
                result="repeat";
            }
        }catch (Exception e){
            e.printStackTrace();
            return result;
        }
        return result;
    }
//    添加用户
    @RequestMapping(path = "/backend/adduser.html",method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest req, @ModelAttribute("addUser")User addUser){
//        得到session对象
        HttpSession session = req.getSession();
//        得到用户菜单列表 判断是否存在
         if(session.getAttribute(Constants.SESSION_BASE_MODEL)==null){
             return new ModelAndView("redirect:/");
         }
         try{
             String idCard = addUser.getIdCard();//证件号码后六位是初始密码
             String password = idCard.substring(idCard.length() - 6);
             addUser.setPassword(password);
             addUser.setPassword2(password);
             addUser.setCreateTime(new Date());//设置创建时间
//             得到当前用户的信息
             User user = (User)session.getAttribute(Constants.SESSION_USER);
             addUser.setReferId(user.getId());//设置推荐人的id
             addUser.setLastUpdateTime(new Date());//设置最新更改时间
//             调用添加用户业务
             userService.getAddUserService(addUser);
         }catch (Exception e){
             e.printStackTrace();
             logger.info("==================新增用户失败=======================");
         }
        return new ModelAndView("redirect:/backend/userlist.html");
    }
//    照片上传
    @RequestMapping(path = "/backend/upload.html",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public Object fileUpload(@RequestParam(value ="a_fileInputID",required =false)MultipartFile cardFile,
                             @RequestParam(value = "a_fileInputBank",required = false)MultipartFile bankFile,
                             @RequestParam(value ="m_fileInputID",required = false)MultipartFile mCardFile,
                             @RequestParam(value = "m_fileInputBank",required = false)MultipartFile mBankFile,
                             HttpServletRequest req){
        logger.info("====================开始=========================");
//        得到保存上传图片的文件夹地址
        String path=req.getSession().getServletContext().getRealPath("static"+ File.separator+"uploadFiles");
        logger.info("==================保存文件==================="+path);
//        创建数据字典实例
        DataDictionary dataDictionary=new DataDictionary();
        dataDictionary.setTypeCode("PERSONALFILE_SIZE");
        List<DataDictionary> list=null;
//        根据条件查询出允许上传图片的大小
        try {
            list = dataDictionaryService.getDataDictionaryListService(dataDictionary);

        }catch (Exception e){
            e.printStackTrace();
            logger.info("=====================查询数据字典得到允许上传图片的大小=================");
        }
        Integer fileSize=50000;
        if(list!=null){
            if (list.size()==1){
//                得到允许上传图片大小
                fileSize=Integer.valueOf(list.get(0).getValueName());
            }
        }
       if (cardFile!=null){//确定上传了文件
            //得到上传文件的文件名
           String oldFileName = cardFile.getOriginalFilename();
           //得到上传文件的后缀
           String suffix = FilenameUtils.getExtension(oldFileName);
           logger.info("==================后缀================="+suffix);
           if (cardFile.getSize()>fileSize){//上传的图片超出大小
                return "1";
           }else if (suffix.equalsIgnoreCase("jpg")|| suffix.equalsIgnoreCase("png")
                   || suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("pneg")){
//                保存的文件名
               String fileName=System.currentTimeMillis()+ RandomUtils.nextInt(1000000)+"_IDcard.jpg";
               logger.info("===================文件名为=================="+fileName);
               File file=new File(path,fileName);
               if (!file.exists()){//文件不存在，创建
                   file.mkdirs();
               }
               try {//将上传的文件保存到文件夹
                    cardFile.transferTo(file);
               }catch (Exception e){
                   e.printStackTrace();
                   logger.info("=============上传图片保存失败====================");
               }
               String url=req.getContextPath()+"/static/uploadFiles/"+fileName;
               return url;
            }else {//图片格式不正确
               return "2";
           }
       }
        if (bankFile!=null){//确定上传了文件
            //得到上传文件的文件名
            String oldFileName = bankFile.getOriginalFilename();
            //得到上传文件的后缀
            String suffix = FilenameUtils.getExtension(oldFileName);
            logger.info("==================后缀================="+suffix);
            if (bankFile.getSize()>fileSize){//上传的图片超出大小
                return "1";
            }else if (suffix.equalsIgnoreCase("jpg")|| suffix.equalsIgnoreCase("png")
                    || suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("pneg")){
//                保存的文件名
                String fileName=System.currentTimeMillis()+ RandomUtils.nextInt(1000000)+"_bank.jpg";
                logger.info("===================文件名为=================="+fileName);
                File file=new File(path,fileName);
                if (!file.exists()){//文件不存在，创建
                    file.mkdirs();
                }
                try {//将上传的文件保存到文件夹
                    bankFile.transferTo(file);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info("=============上传图片保存失败====================");
                }
                String url=req.getContextPath()+"/static/uploadFiles/"+fileName;
                return url;
            }else {//图片格式不正确
                return "2";
            }
        }
        if (mCardFile!=null){//确定上传了文件
            //得到上传文件的文件名
            String oldFileName = mCardFile.getOriginalFilename();
            //得到上传文件的后缀
            String suffix = FilenameUtils.getExtension(oldFileName);
            logger.info("==================后缀================="+suffix);
            if (mCardFile.getSize()>fileSize){//上传的图片超出大小
                return "1";
            }else if (suffix.equalsIgnoreCase("jpg")|| suffix.equalsIgnoreCase("png")
                    || suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("pneg")){
//                保存的文件名
                String fileName=System.currentTimeMillis()+ RandomUtils.nextInt(1000000)+"_IDcard.jpg";
                logger.info("===================文件名为=================="+fileName);
                File file=new File(path,fileName);
                if (!file.exists()){//文件不存在，创建
                    file.mkdirs();
                }
                try {//将上传的文件保存到文件夹
                    mCardFile.transferTo(file);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info("=============上传图片保存失败====================");
                }
                String url=req.getContextPath()+"/static/uploadFiles/"+fileName;
                return url;
            }else {//图片格式不正确
                return "2";
            }
        }
        if (mBankFile!=null){//确定上传了文件
            //得到上传文件的文件名
            String oldFileName = mBankFile.getOriginalFilename();
            //得到上传文件的后缀
            String suffix = FilenameUtils.getExtension(oldFileName);
            logger.info("==================后缀================="+suffix);
            if (mBankFile.getSize()>fileSize){//上传的图片超出大小
                return "1";
            }else if (suffix.equalsIgnoreCase("jpg")|| suffix.equalsIgnoreCase("png")
                    || suffix.equalsIgnoreCase("jpeg") || suffix.equalsIgnoreCase("pneg")){
//                保存的文件名
                String fileName=System.currentTimeMillis()+ RandomUtils.nextInt(1000000)+"_bank.jpg";
                logger.info("===================文件名为=================="+fileName);
                File file=new File(path,fileName);
                if (!file.exists()){//文件不存在，创建
                    file.mkdirs();
                }
                try {//将上传的文件保存到文件夹
                    mBankFile.transferTo(file);
                }catch (Exception e){
                    e.printStackTrace();
                    logger.info("=============上传图片保存失败====================");
                }
                String url=req.getContextPath()+"/static/uploadFiles/"+fileName;
                return url;
            }else {//图片格式不正确
                return "2";
            }
        }
       return null;
    }
//    删除上传的图片
    @RequestMapping(path = "/backend/delpic.html",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String delpic(@RequestParam(value = "id",required = false)String id,
                         @RequestParam(value = "picpath",required = false)String picpath,
                         HttpServletRequest req){
        String result="failed";//删除失败返回
        if (picpath==null||picpath==""){
            result="success";
        }else {
//            将图片路径解析成物理路径
            String[] paths=picpath.split("/");
//            得到路径
            String path=req.getSession().getServletContext().getRealPath(paths[1]+File.separator+paths[2]+File.separator+paths[3]);
            File file=new File(path);
            if (file.exists()){
                if (file.delete()){
                    if (id.equals("0")){//添加用户时删除
                        result="success";
                    }else {//修改用户时删除
                        User user=new User();
                        user.setId(Integer.valueOf(id));
                        if (picpath.indexOf("_IDcard.jpg")!=-1){
                            user.setIdCardPicPath(picpath);
                        }else if (picpath.indexOf("_bank.jpg")!=-1){
                            user.setBankPicPath(picpath);
                        }
                        try {
//                            调用删除业务
                            if (userService.delpicService(user)){
                                result="success";
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            logger.info("==============删除图片失败==============");
                        }

                    }
                }
            }
        }
        return result;

    }
//    查看用户信息
    @RequestMapping(path = "/backend/getuser.html",produces = "text/html;charset=UTF-8",method = RequestMethod.POST)
    @ResponseBody
    public Object getViewUser(@RequestParam(required = false) Integer id){
//        返回到前台的是json数据格式
        String cJson="";
        if (id==null||id.equals("")){
            return "nodate";
        }else {
            try {
                User user = new User();
                user.setId(id);
                User userView = userService.getUserService(user);
//                进行日期格式处理  java转转成json
                JsonConfig jsonConfig = new JsonConfig();
                jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
//              java对象转换成json对象
                JSONObject jo = JSONObject.fromObject(userView, jsonConfig);
                cJson=jo.toString();//将json对象转换成json字符串
            }catch (Exception e){
                e.printStackTrace();
                return "failed";
            }
        }
        return cJson;
    }
//    修改用户信息
    @RequestMapping(path = "/backend/modifyuser.html",method = RequestMethod.POST)
    public ModelAndView modifyUser(HttpServletRequest req,@ModelAttribute("modifyUser")User modifyUser){
        //判断用户是否登录
        HttpSession session = req.getSession();
        if (session.getAttribute(Constants.SESSION_USER)==null){//用户不存在
//            返回到登录页面
            return new ModelAndView("redirect:/");
        }else {
            try{
//                设置更新时间
                modifyUser.setLastUpdateTime(new Date());
//                调用更新服务
                userService.updatePwd(modifyUser);
            }catch (Exception e){
                e.printStackTrace();
            }
            return new ModelAndView("redirect:/backend/userlist.html");
        }
    }

}
