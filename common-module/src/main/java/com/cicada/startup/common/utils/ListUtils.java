package com.cicada.startup.common.utils;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * ListUtils
 * <p/>
 * 创建时间: 15/10/27 上午11:08 <br/>
 *
 * @author zhaohaiyang
 * @since v0.0.1
 */
public class ListUtils {

    /**
     * 判断集合是否为空
     *
     * @param list
     * @return
     */
    public static boolean isEmpty(List<? extends Object> list) {
        if (null != list && !list.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 判断集合不为空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List<? extends Object> list) {
        return !isEmpty(list);

    }

    /**
     * 将字符串分割未List
     *
     * @param str
     * @param tag
     * @return
     */
    public static List<String> str2List(String str, String tag) {
        List<String> list = new ArrayList<String>();
        if (!TextUtils.isEmpty(str)) {
            String[] array = str.split(tag);
            for (int i = 0; i < array.length; i++) {
                list.add(array[i]);
            }
        }
        return list;
    }

    public static String list2String(List<String> list, String tag) {
        if (isNotEmpty(list)) {
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                buf.append(list.get(i));
                if (i < list.size() - 1) {
                    buf.append(tag);
                }
            }
            return buf.toString();
        }
        return "";
    }

    public static String list2String(Collection<String> collection, String tag) {
        StringBuffer buf = new StringBuffer();
        Iterator<String> iterator = collection.iterator();
        for (int i = 0; i < collection.size(); i++) {
            buf.append(iterator.next());
            if (i < collection.size() - 1) {
                buf.append(tag);
            }
        }
        return buf.toString();
    }
}
