package com.demo.sl.common;

/**
 * myBatis防止sql注入类
 */
public class SQLTools {
    /**
     *myBatis模糊查询防止sql注入
     * @param keyword
     * @return
     */
    public static String transfer(String keyword){
        if (keyword.contains("%")||keyword.contains("_")){
            ////正则表达式 \\\\代表一个\
           keyword= keyword.replaceAll("\\\\","\\\\\\\\")
                           .replaceAll("\\%","\\\\%")
                           .replaceAll("\\_","\\\\_");
        }
        return keyword;
    }
}
