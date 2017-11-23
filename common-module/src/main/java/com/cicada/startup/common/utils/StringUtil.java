package com.cicada.startup.common.utils;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;

import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO 类描述
 * <p>
 * 创建时间: 2014-8-19 下午2:42:33 <br/>
 */
public class StringUtil {

    private static final String TAG = StringUtil.class.getSimpleName();

    /**
     * Java文件操作,获取不带扩展名的文件名
     *
     * @param filename
     * @return
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * Java文件操作,获取文件扩展名
     *
     * @param filename
     * @return
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * @param date
     * @return "yyyy-MM-dd"
     */
    public static String dateToYMDString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    /**
     * @param date
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String dateToString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * @param s "yyyy-MM-dd HH:mm:ss"
     * @return Date
     */
    public static Date stringToDate(String s) {
        Date date = null;
        if (!TextUtils.isEmpty(s)) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                date = dateFormat.parse(s);
            } catch (ParseException e) {
            }
        }

        return date;
    }

    /**
     * @param date
     * @return "HH:mm"
     */
    public static String dateToHHmmString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }

    /**
     * @param date
     * @return "M月d日 HH:mm" 一个M和d表示月、日不补0
     */
    public static String dateToMMddHHmmString(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("M月d日 HH:mm");
        return dateFormat.format(date);
    }

    /**
     * 用于聊天界面的时间，将传入时间与当前时间进行对比，是否今天昨天
     */
    public static String getMessageTime(Date date) {
        if (date == null) {
            return "";
        }
        String todySDF = "今天 HH:mm";
        String yesterDaySDF = "昨天 HH:mm";
        String otherSDF = "M月d日 HH:mm";
        SimpleDateFormat sfd = null;
        String time = "";
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Date now = new Date();
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(now);
        targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
        targetCalendar.set(Calendar.MINUTE, 0);
        if (dateCalendar.after(targetCalendar)) {
            sfd = new SimpleDateFormat(todySDF);
            time = sfd.format(date);
            return time;
        } else {
            targetCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(targetCalendar)) {
                sfd = new SimpleDateFormat(yesterDaySDF);
                time = sfd.format(date);
                return time;
            }
        }
        sfd = new SimpleDateFormat(otherSDF);
        time = sfd.format(date);
        return time;
    }

    public static String getMessageTimeForTest(Date date) {
        if (date == null) {
            return "";
        }
        String dateSDF = "yyyy-MM-dd HH:mm:ss.SSS";
        SimpleDateFormat sfd = new SimpleDateFormat(dateSDF);
        String time = "";
        time = sfd.format(date);
        return time;
    }

    /**
     * 用于会话列表的时间
     */
    public static String getGroupTime(Date date) {
        if (date == null) {
            return "";
        }
        String todySDF = "今天 HH:mm";
        String yesterDaySDF = "昨天 HH:mm";
        String otherSDF = "M月d日";
        SimpleDateFormat sfd = null;
        String time = "";
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Date now = new Date();
        Calendar targetCalendar = Calendar.getInstance();
        targetCalendar.setTime(now);
        targetCalendar.set(Calendar.HOUR_OF_DAY, 0);
        targetCalendar.set(Calendar.MINUTE, 0);
        if (dateCalendar.after(targetCalendar)) {
            sfd = new SimpleDateFormat(todySDF);
            time = sfd.format(date);
            return time;
        } else {
            targetCalendar.add(Calendar.DATE, -1);
            if (dateCalendar.after(targetCalendar)) {
                sfd = new SimpleDateFormat(yesterDaySDF);
                time = sfd.format(date);
                return time;
            }
        }
        sfd = new SimpleDateFormat(otherSDF);
        time = sfd.format(date);
        return time;
    }

    /**
     * 半角字符转全角字符，用于解决 TextView 显示的内容不能填满一行
     */
    public static String ToSBC(String input) {
        // 半角转全角：
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            // 转换空格
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            // 转换数字和字母
            if (c[i] < 127 && c[i] > 32)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * 半角字符转全角字符，用于解决 TextView 显示的内容不能填满一行
     *
     * @param input
     * @return
     * @author liuyun
     * @since v0.1
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 组装成逗号分割的长字符串
     */
    public static String makeSplitString(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (int i = 0; i < list.size(); i++) {
            if (first) {
                sb.append(list.get(i));
                first = false;
            } else {
                sb.append(",").append(list.get(i));
            }
        }

        LogUtils.d(TAG, "makeSplitString:" + sb.toString());
        return sb.toString();
    }

    /**
     * 将逗号分割的长字符串拆装为 List
     */
    public static ArrayList<String> splitString(String src) {
        if (TextUtils.isEmpty(src)) {
            return null;
        }
        ArrayList<String> list = new ArrayList<String>();
        String[] s = src.split(",");
        for (int i = 0; i < s.length; i++) {
            list.add(s[i]);
        }
        return list;
    }

    /**
     * 语音的时长数值组装在地址里，这里需要解析：bulletin_record1234235112time4.m4a
     */
    public static int getDuration(String src) {
        try {
            String[] s1 = src.split("time");
            if (s1 != null && s1.length > 0) {
                String[] s2 = s1[1].split("\\.");
                if (s2 != null && s2.length > 0) {
                    if (TextUtils.isEmpty(s2[0])) {
                        return 0;
                    }
                    return Integer.valueOf(s2[0]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将录音时长组装到地址上：/mnt/.../bulletin_record1234235112time4.m4a
     */
    public static String setDuration(String src, int duration) {
        String[] s = src.split("\\.");
        String result = "";
        boolean first = true;
        // 获取倒数第二个串
        int length = s.length;
        int location = length - 2;
        s[location] = s[location] + "time" + String.valueOf(duration);
        for (int i = 0; i < length; i++) {
            if (first) {
                result = result + s[i];
                first = false;
            } else {
                result = result + "." + s[i];
            }
        }

        LogUtils.d(TAG, result);
        return result;
    }

    /**
     * 获取32位的MD5加密字符串
     */
    public static String getMD5String(String strValue) {
        try {
            byte[] toencode = strValue.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.reset();
            md5.update(toencode);

            byte[] md5Bytes = md5.digest();
            StringBuilder md5StrBuff = new StringBuilder(md5Bytes.length * 2);
            for (byte b : md5Bytes) {
                md5StrBuff.append(Integer.toHexString((b & 0xf0) >>> 4));
                md5StrBuff.append(Integer.toHexString(b & 0x0f));
            }
            return md5StrBuff.toString(); // 16位加密
            // md5StrBuff.toString().toUpperCase(Locale.getDefault()).substring(8,
            // 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 数字验证
     *
     * @param str
     * @return
     */
    public static boolean isDigital(String str) {
        return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
    }

    private static final boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        // GENERAL_PUNCTUATION 判断中文的“号
        // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
        // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测一个字符串是否包含汉字字符.
     *
     * @param str
     * @return
     */
    public static final boolean hasChinese(String str) {
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测一个字符串中是否有Unicode字符。<br/>
     * 符串里如果有双字节的字符java就把每个字符都按双字节编码,如果都是单字节的字符就按单字节编码.
     * 将字符串转换成字节之后,如果字节数与字符串的长度相等则说明,字符串中只有assii码;否则则说明 字符串中包含unicode编码的字符.
     * <b>备注:</b> 有部分字符还是识别不了.
     *
     * @param str
     * @return true, 如果字符串中包含unicode字符;否则,返回false.
     */
    public static final boolean hasUnicode(String str) {
        if (str.getBytes().length != str.length()) {
            return true;
        }

        return false;
    }

    /**
     * 检测一个字符串是否是全是可见Ascii码. 参看维基百科<a
     * href="http://zh.wikipedia.org/wiki/ASCII">Ascii码表</a>
     *
     * @param s
     * @return
     */
    public static final boolean isDisplayedAscii(String s) {
        char[] charArray = s.toCharArray();
        for (char ch : charArray) {
            if (ch > 126 || ch < 32) {
                // 32~126是可见字符.其中32是"空格".
                return false;
            }
        }

        return true;
    }

    /**
     * 取一个字符串中的全部汉字,包括标点.
     *
     * @param str
     * @return
     */
    public static final String getAllChineseCharacterWithPunctuation(String str) {
        StringBuilder sb = new StringBuilder();
        char[] ch = str.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (isChinese(c)) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    /**
     * 获取一个字符串中的全部汉字,不包括标点.
     *
     * @param str
     * @return
     */
    public static final String getAllChineseCharacter(String str) {
        Pattern p = Pattern.compile("[\\u4E00-\\u9FA5]");
        Matcher m = p.matcher(str);
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            sb.append(m.group());
        }

        return sb.toString();
    }

    /**
     * only for test.
     */
    // private static final SimpleDateFormat sdf = new
    // SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
    /**
     * only for test
     */
    // public static final String generateTimeForTest(long date) {
    // return generateTimeForTest(new Date(date));
    // }

    /**
     * only for test
     */
    // public static final String generateTimeForTest(Date date) {
    // return sdf.format(date);
    // }

    /**
     * only for test 获取消息发送时间
     */
    // private static final Date parseTimeForTest(String message) {
    // if (TextUtils.isEmpty(message)) {
    // return null;
    // }
    //
    // int sendTimeStartPosition = message.lastIndexOf('#');
    // try {
    // String sendTime = null;
    // if (sendTimeStartPosition != -1
    // && sendTimeStartPosition < message.length()) {
    // sendTime = message.substring(sendTimeStartPosition + 1);
    // }
    //
    // return sdf.parse(sendTime);
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    //
    // return null;
    // }

    /**
     * only for test. 追加测试消息文本
     *
     * @param receiveType
     * push/get
     */
    // public static String appendTestContent(String receiveType, String
    // content) {
    // long now = System.currentTimeMillis();
    // String receiveTime = StringUtil.generateTimeForTest(now);
    // Date sendTime = parseTimeForTest(content );
    // if (sendTime != null) {
    // content += "#" + receiveType + "#" + receiveTime + "#" + (now -
    // sendTime.getTime());
    // }
    //
    // return content;
    // }

    private static SimpleDateFormat sdf = new SimpleDateFormat();

    /**
     * @param time
     * @return
     */
    public static String getTimeRecentMessage(Date time) {
        if (time == null)
            return "";

        Calendar now = Calendar.getInstance();
        int nowYear = now.get(Calendar.YEAR);

        Calendar compare = Calendar.getInstance();
        compare.setTime(time);
        int compareYear = compare.get(Calendar.YEAR);
        if (nowYear == compareYear) {

            int nowMonth = now.get(Calendar.MONTH);
            int nowDay = now.get(Calendar.DAY_OF_MONTH);
            int compareMonth = compare.get(Calendar.MONTH);
            int compareDay = compare.get(Calendar.DAY_OF_MONTH);
            if (nowMonth == compareMonth && nowDay == compareDay) {
                sdf.applyPattern("HH:mm");
            } else {
                sdf.applyPattern("MM-dd");
            }
        } else {
            sdf.applyPattern("MM-dd");
        }

        try {
            return sdf.format(time);
        } catch (Exception e) {

        }
        return "";
    }

    /**
     * @param lastUpdateTime
     * @return MM-dd HH:mm:ss
     */
    public static String getDateTimeLastUpdate(long lastUpdateTime) {
        sdf.applyPattern("MM-dd HH:mm:ss");
        return sdf.format(new Date(lastUpdateTime));
    }

    /**
     * @param lastUpdateTime
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getDateTimeAll(long lastUpdateTime) {
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(lastUpdateTime));
    }

    /**
     * @param lastUpdateTime
     * @return yyyy-MM-dd
     */
    public static String getDateAll(long lastUpdateTime) {
        sdf.applyPattern("yyyy-MM-dd");
        return sdf.format(new Date(lastUpdateTime));
    }

    /**
     * @param lastUpdateTime
     * @return HH:mm:ss
     */
    public static String getTimeAll(long lastUpdateTime) {
        sdf.applyPattern("HH:mm:ss");
        return sdf.format(new Date(lastUpdateTime));
    }

    /**
     * 返回与今天对比之后显示的时间信息 今天的显示 HH:mm 今天之前显示 YY-MM-dd
     *
     * @param lastUpdateTime
     * @return
     */
    public static String getDateOrTimeByToday(long lastUpdateTime) {

        Calendar now = Calendar.getInstance();
        int nowYear = now.get(Calendar.YEAR);

        Calendar compare = Calendar.getInstance();
        compare.setTime(new Date(lastUpdateTime));
        int compareYear = compare.get(Calendar.YEAR);
        if (nowYear == compareYear) {

            int nowMonth = now.get(Calendar.MONTH);
            int nowDay = now.get(Calendar.DAY_OF_MONTH);
            int compareMonth = compare.get(Calendar.MONTH);
            int compareDay = compare.get(Calendar.DAY_OF_MONTH);
            if (nowMonth == compareMonth && nowDay == compareDay) {
                sdf.applyPattern("HH:mm");
            } else {
                sdf.applyPattern("yy-MM-dd");
            }
        } else {
            sdf.applyPattern("yy-MM-dd");
        }

        try {
            return sdf.format(new Date(lastUpdateTime));
        } catch (Exception e) {

        }
        return "";
    }

    public static void main(String[] args) {
        toFormatString("dljflsj%jflasjldf");
    }

    /**
     * 为新协议（班级圈）的评论等文字内容做特殊字符转义 1. + URL 中+号表示空格 %2B 2. 空格 URL中的空格可以用+号或者编码 %20
     * 3. / 分隔目录和子目录 %2F 4. ? 分隔实际的 URL 和参数 %3F 5. % 指定特殊字符 %25 6. # 表示书签 %23 7.
     * & URL 中指定的参数间的分隔符 %26 8. = URL 中指定参数的值 %3D
     */
    public static String toFormatString(String content) {
        String newstr = content;
        newstr = newstr.replace("%", "%25");
        newstr = newstr.replace("+", "%2B");
        // newstr = newstr.replace("/", "%2F");
        // newstr = newstr.replace("#", "%23");
        // newstr = newstr.replace("?", "%3F");
        // newstr = newstr.replace("&", "%26");
        // newstr = newstr.replace("=", "%3D");

        return newstr;
    }

    public static String parseFormatString(String content) {
        String newstr = content;
        newstr = newstr.replace("%2B", "+");
        newstr = newstr.replace("%25", "%");
        return newstr;
    }

    /**
     * 处理特殊java报“非法字符: \65279 ”错误的解决方法 byte字节数组= [-17, -69, -65]
     * 输入之后没有占为符，是看不见的特殊字符。但是长度是1
     * 原因就在于某些编辑器会往utf8文件中添加utf8标记（editplus称其为签名），它会在文件开始的地方插入三个不可见的字符（0xEF 0xBB
     * 0xBF，即BOM），它的表示的是 Unicode 标记（BOM）
     *
     * @param strSpecialChar
     * @return
     * @author xnjiang
     * @since v0.0.1
     */
    public static String dealWithSpecialChar(String strSpecialChar) {
        byte[] b1 = {-17, -69, -65};
        String bb = new String(b1);
        strSpecialChar = strSpecialChar.replace(bb, "");
        return strSpecialChar;
    }

    /**
     * 过滤出数字
     *
     * @param number
     * @return
     * @author liuyun
     * @since v0.1
     */
    public static String filterNumber(String number) {
        number = number.replaceAll("[^(0-9)]", "");
        return number;
    }

    /**
     * 过滤出字母
     *
     * @param alph
     * @return
     * @author liuyun
     * @since v0.1
     */
    public static String filterAlphabet(String alph) {
        alph = alph.replaceAll("[^(A-Za-z)]", "");
        return alph;
    }

    /**
     * 过滤出中文
     *
     * @param chin
     * @return
     * @author liuyun
     * @since v0.1
     */
    public static String filterChinese(String chin) {
        chin = chin.replaceAll("[^(\\u4e00-\\u9fa5)]", "");
        return chin;
    }

    /**
     * 过滤出字母、数字和中文
     *
     * @param character
     * @return
     * @author liuyun
     * @since v0.1
     */
    public static String filterAlphabetAndNumAndChinese(String character) {
        character = character.replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5)]", "");
        return character;
    }

    /**
     * 过滤出字母、数字
     *
     * @param character
     * @return
     * @author liuyun
     * @since v0.1
     */
    public static String filterAlphabetAndNum(String character) {
        character = character.replaceAll("[^(a-zA-Z0-9]", "");
        return character;
    }

    /**
     * 替换第一个
     *
     * @param character
     * @param repalceStr
     * @return
     */
    public static String replaceFirst(String character, String repalceStr) {
        if (TextUtils.isEmpty(character)) {
            return "";
        } else {
            return repalceStr + character.substring(1, character.length());
        }
    }

    public static String replacePhoneNumber(String phoneNumber) {
        char[] chars = phoneNumber.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i >= 3 && i <= 6) {
                chars[i] = '*';
            }
        }
        return new String(chars);
    }

    public static boolean isWebSite(String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        } else {
            if (url.startsWith("http") || url.startsWith("https")) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 检测字符串中是否有网络链接
     * @param source
     * @return
     */
    public static ArrayList<String> checkWebSite(String source){
        String pattern = "http(s)?://+[0-9A-Za-z:/[-]_#[?][=][.][&]]*";
        return getRegexMatcherResults(source, pattern);
    }

    /**
     * 返回正则表达式匹配的结果
     */
    public static ArrayList<String> getRegexMatcherResults(String strSource, String strRegex) {
        try {
            if (strSource != null && !strSource.trim().equalsIgnoreCase("")) {
                Pattern pattern = Pattern.compile(strRegex);
                Matcher matcher = pattern.matcher(strSource);
                ArrayList<String> RegexMatcherResults = new ArrayList<String>();
                while (matcher.find()) {
                    RegexMatcherResults.add(matcher.group(0));
                }
                // if (matcher.find()) {
                // String[] RegexMatcherResults = new
                // String[matcher.groupCount()];
                // for(int i=1;i<=matcher.groupCount();i++){
                // Log.e(TAG,"getRegexMatcherResults="+matcher.group(i));
                // RegexMatcherResults[i-1]=matcher.group(i);
                // }
                // return RegexMatcherResults;
                // }
                return RegexMatcherResults;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 解析url地址为key  value形式
     *
     * @param linkUrl
     * @return
     */
    public static HashMap<String, String> parseProtocol(String linkUrl) {
        HashMap<String, String> map = new HashMap<String, String>();
        try {
            if (linkUrl != null) {
                String spStr[] = linkUrl.split("\\?", 2);
                if (spStr.length >= 2) {
                    StringBuilder strand = new StringBuilder();
                    strand.append(spStr[1]);
                    if (strand != null) {
                        if (strand.toString().contains("&")) {
                            String[] spStr1 = strand.toString().split("&");
                            for (int i = 0; i < spStr1.length; i++) {
                                parseProtoclKeyValue(spStr1[i], map);
                            }
                        } else {
                            parseProtoclKeyValue(strand.toString(), map);
                        }
                    }
                }
            }
            return map;
        } catch (Exception e) {
            return map;

        }
    }

    private static void parseProtoclKeyValue(String strand, HashMap<String, String> map) {
        try {
            if (strand.toString().contains("=")) {
                int index = strand.toString().indexOf("=");
                String key = strand.substring(0, index);
                if (strand.toString().lastIndexOf("=") != strand.toString().length() - 1) {
                    String[] spStr2 = new String[2];
                    spStr2[0] = key;
                    spStr2[1] = strand.substring(index + 1, strand.toString().length());
                    map.put(URLDecoder.decode(spStr2[0], "UTF-8"),
                            URLDecoder.decode(spStr2[1], "UTF-8"));
                } else {
                    map.put(URLDecoder.decode(key, "UTF-8"), "");
                }
            } else {
                map.put(URLDecoder.decode(strand, "UTF-8"), "");
            }
        } catch (Exception e) {

        }
    }

    // 将 s 进行 BASE64 编码
    public static String getBASE64(String s) {
        if (s == null) return null;
        return Base64.encodeToString(s.getBytes(), Base64.NO_WRAP);
    }

    // 获取正则表达式规则
    public static InputFilter[] getNameInputFilter(int maxLength) {
        InputFilter[] fArray = new InputFilter[2];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        fArray[1] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!source.toString().matches("[\u4e00-\u9fa5]+") && !source.toString().matches("[a-zA-Z /]+")) {
                        return "";
                    }
                }
                return null;
            }
        };

        return fArray;

    }

    // 获取正则表达式规则
    public static InputFilter[] getNumberInputFilter(int maxLength) {
        InputFilter[] fArray = new InputFilter[2];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        fArray[1] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (!source.toString().matches("[\u4e00-\u9fa5]+") && !source.toString().matches("[0-9 /]+")) {
                        return "";
                    }
                }
                return null;
            }
        };

        return fArray;

    }


    /**
     * 获取图片名称获取图片的资源id的方法
     *
     * @param imageName
     * @return
     */
    public static int getResourceDrawable(Context ctx, String imageName, int defaultResId) {
        imageName = imageName.toLowerCase();
        LogUtils.d("resourceImageName", imageName);
        int resId = ctx.getResources().getIdentifier(imageName, "drawable", ctx.getPackageName());
        if (0 == resId) {
            resId = defaultResId;
        }
        return resId;
    }

}
