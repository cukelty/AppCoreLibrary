package com.singlethread.rcore.utils;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理字符相关工作类
 * Created by litianyuan on 2017/8/31.
 */

public class StringUtils {
    private static final String PATTERN_PHONE = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
    private static final String PATTERN_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    /**
     * 检测是否为手机号
     *
     * @param str
     * @return
     */
    public static boolean isPhoneNum(String str) {
        Pattern p = Pattern.compile(PATTERN_PHONE);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 检测密码
     * <p>
     * ^ 匹配一行的开头位置
     * (?![0-9]+$) 预测该位置后面不全是数字
     * (?![a-zA-Z]+$) 预测该位置后面不全是字母
     * [0-9A-Za-z] {6,15} 由6-15位数字或这字母组成
     * `~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？ 允许输入特殊字符
     */
    public static boolean passWordCheck(String pwd) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[`~!@#$^&*()=|{}':;',\\\\[\\\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？0-9A-Za-z]{6,15}$";
        return pwd.matches(regex);
    }

    /**
     * 判断是否为纯数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否为纯字母
     *
     * @param str
     * @return
     */
    public static boolean isChart(String str) {
        Pattern pattern = Pattern.compile("[A-za-z]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 判断用户输入的内容是否只含有换行符/n
     *
     * @param input
     * @return true -是  false-否
     */
    public static boolean isWrapOnly(String input) {
        String regEx = "[\n]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * 判断用户输入的内容是否只含有空格
     *
     * @param input
     * @return
     */
    public static boolean isSpaceOnly(String input) {
        String regEx = "[ ]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(input);
        return m.matches();
    }

    /**
     * 检查EditText中的内容是否为空
     *
     * @return true表示有为空的EditText
     */
    public static boolean etvIsEmpty(EditText... editTexts) {
        for (int i = 0, len = editTexts.length; i < len; i++) {
            if (editTexts[i].getText().toString().trim().length() == 0)
                return true;
        }
        return false;
    }

    /**
     * 根据正则表达式解析字符串变颜色
     *
     *
     * create at 2017/2/20 上午8:54
     */
    public static CharSequence getTextWithColor(String regex, String text, int color) {
        String[] strings = text.split(regex, -1);

        int count = (strings.length - 1) / 2;
        if (count <= 0) return text;
        SpannableString style = new SpannableString(text.replaceAll(regex, ""));
        for (int i = 1; i < count + 1; i++) {
            int start = 0;
            int len = (i - 1) * 2;
            for (int j = 0; j <= len; j++) {
                start += strings[j].length();
            }
            start += len;
            style.setSpan(new ForegroundColorSpan(color), start, start + strings[len + 1].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return style;
    }
}
