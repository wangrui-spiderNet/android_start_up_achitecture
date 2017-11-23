package com.cicada.startup.common.utils;

import java.util.Comparator;

/**
 * 类型比较工具类
 * <p/>
 * 创建时间: 2014-8-20 下午4:43:01 <br/>
 * 
 * @version
 * @since v0.0.1
 */
public class EqualUtils {
	public static final int UNCOMPARE = Integer.MIN_VALUE;

	public static <T> boolean equals(T lhs, T rhs) {
		if (lhs == rhs) {
			return true;
		}

		if (lhs == null || rhs == null) {
			return false;
		} else {
			return lhs.equals(rhs);
		}
	}

	/**
	 * 
	 * @param lhs
	 * @param rhs
	 * @return {@link #UNCOMPARE}表示lhs和rhs无法比较, 即两者都不是null;其他返回值参考
	 *         {@link Comparator#compare(Object, Object)}
	 */
	public static <T> int compare(T lhs, T rhs) {
		if (lhs == rhs) {
			return 0;
		}

		if (lhs != null) {
			return 1;
		}

		if (rhs != null) {
			return -1;
		}

		return Integer.MIN_VALUE;
	}
}
