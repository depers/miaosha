package cn.bravedawn.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/6/5 22:10
 */
public class StrUtil {

    private static Pattern BLANK_PATTERN = Pattern.compile("\\s*|\t|\r|\n");

    /**
     * 去除字符串中的空格、换行符、制表符
     * @param str 字符串
     * @return
     */
    public static String plainText(String str) {
        Matcher matcher = BLANK_PATTERN.matcher(str);
        return matcher.replaceAll("");
    }
}
