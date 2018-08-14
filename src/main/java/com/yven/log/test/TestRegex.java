package com.yven.log.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yven
 * @date 2018/8/14
 */
public class TestRegex {

    public static void main(String[] args) {
        // 要验证的字符串
        String str = "baike.xsoftlab.net";
        // 正则表达式规则
        String regEx = "aike.*";
//        String regEx = "^*";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        // 查找字符串中是否有匹配正则表达式的字符/字符串
        boolean rs = matcher.find();
        System.out.println(rs);
    }
}
