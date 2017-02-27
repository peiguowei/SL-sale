package com.demo.sl.controller;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 基类 共有的数据
 */
public class BaseController {
    /**
     * 日期国际化 custom习惯 springMVC做国际化处理 springMVC不支持这种转换 需要手动配置
     */
   @InitBinder
    public void initBinder(WebDataBinder dataBinder){
       dataBinder.registerCustomEditor(Date.class,new PropertyEditorSupport(){
           @Override
           public String getAsText() {
               return new SimpleDateFormat("yyyy-MM-dd").format((Date)getValue());
           }

           @Override
           public void setAsText(String text) throws IllegalArgumentException {
               try {
                   setValue(new SimpleDateFormat("yyyy-MM-dd").parse(text));
               } catch (ParseException e) {
                   e.printStackTrace();
                   setValue(null);
               }
           }
       });
   }
}
