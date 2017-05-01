package com.demo.sl.common;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * JSON数据日期格式处理 java转化成json
 */
public class JsonDateValueProcessor implements JsonValueProcessor {

    private String datePattern="yyyy-MM-dd";

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public JsonDateValueProcessor(){
        super();
    }
    public JsonDateValueProcessor(String datePattern) {
        super();
        this.datePattern = datePattern;
    }

    @Override
    public Object processArrayValue(Object o, JsonConfig jsonConfig) {
        return process(o);
    }

    @Override
    public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
        return process(o);
    }
    private Object process(Object value){
        try {
            if (value instanceof Date){
                SimpleDateFormat sdf=new SimpleDateFormat(datePattern, Locale.UK);
                return sdf.format((Date) value);
            }
            return value==null?"":value.toString();
        }catch (Exception e){
            return "";
        }
    }
}
