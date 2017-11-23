package com.cicada.startup.common.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentOperateUtil {

	public static void addFragmentWithAnim(FragmentManager fm,
			Class<? extends Fragment> clazz, int resId, String tag,
			Bundle args, boolean isAddBackStack) {
		int[] anims = new int[] { android.R.anim.fade_in,
				android.R.anim.fade_out, android.R.anim.fade_in,
				android.R.anim.fade_out };
		fragmentOperate(fm, clazz, resId, tag, args, anims, false,
				isAddBackStack);
	}

	public static void addFragment(FragmentManager fm,
			Class<? extends Fragment> clazz, int resId, String tag,
			Bundle args, boolean isAddBackStack) {
		fragmentOperate(fm, clazz, resId, tag, args, null, false,
				isAddBackStack);
	}

	public static void replaceFragmentWithAnim(FragmentManager fm,
			Class<? extends Fragment> clazz, int resId, String tag,
			Bundle args, boolean isAddBackStack) {
		int[] anims = new int[] { android.R.anim.fade_in,
				android.R.anim.fade_out, android.R.anim.fade_in,
				android.R.anim.fade_out };
		fragmentOperate(fm, clazz, resId, tag, args, anims, true,
				isAddBackStack);
	}


	public static Fragment replaceFragment(FragmentManager fm,
			Class<? extends Fragment> clazz, int resId, String tag,
			Bundle args, boolean isAddBackStack) {
		return fragmentOperate(fm, clazz, resId, tag, args, null, true,
				isAddBackStack);
	}

	/**
	 * @param fm
	 * @param clazz
	 * @param resId
	 * @param tag
	 * @param args
	 * @param anims
	 *            切换过渡动画数组，如果数组长度为 2 那么就是 enterAnim, exitAnim, 如果�?4 就是
	 *            enterAnim, exitAnim, popEnterAnim, popExitAnim
	 * @param isReplace
	 *            false 添加（add）， true 替换（replace�?
	 */
	public static Fragment fragmentOperate(FragmentManager fm,
			Class<? extends Fragment> clazz, int resId, String tag,
			Bundle args, int[] anims, boolean isReplace, boolean isAddBackStack) {
		Fragment fragment = getFragmentInstance(fm, clazz, tag, args);
		FragmentTransaction ft = fm.beginTransaction();
		if (fragment.isAdded()) {
			ft.show(fragment);
			return fragment;
		}
		setCustomAnim(ft, anims);
		ft = isReplace ? ft.replace(resId, fragment, tag) : ft.add(resId,
				fragment, tag);
		if (isAddBackStack) {
			ft.addToBackStack(tag);
		}
		ft.commitAllowingStateLoss();
		return fragment;
	}

	public static Fragment getFragmentInstance(FragmentManager fm,
			Class<? extends Fragment> clazz, String tag, Bundle args) {
		Fragment fragment = fm.findFragmentByTag(tag);
		if (fragment == null) {
			fragment = createNewFragment(clazz);
		}

		if (args != null && !args.isEmpty()) {
			fragment.getArguments().putAll(args);
		}
		return fragment;
	}

	public static Fragment createNewFragment(Class<? extends Fragment> clazz) {
		Fragment fragment = null;
		try {
			fragment = clazz.newInstance();
			fragment.setArguments(new Bundle());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return fragment;
	}

	/**
	 * @param ft
	 * @param anims
	 *            切换过渡动画数组，如果数组长度为 2 那么就是 enterAnim, exitAnim, 如果�?4 就是
	 *            enterAnim, exitAnim, popEnterAnim, popExitAnim
	 */
	public static void setCustomAnim(FragmentTransaction ft, int[] anims) {
		if (anims == null) {
			return;
		}
		int animCount = anims.length;
		if (animCount == 2) {
			ft.setCustomAnimations(anims[0], anims[1]);
		} else if (animCount == 4) {
			ft.setCustomAnimations(anims[0], anims[1], anims[2], anims[3]);
		}
	}

	public static boolean isExistFragment(FragmentManager fm, String tag) {
		Fragment fragment = fm.findFragmentByTag(tag);
		if (fragment == null) {
			return false;
		} else {
			return true;
		}
	}

	public static void popFragmentBackStack(FragmentManager fm, String name,
			int flags) {
		fm.popBackStack(name, flags);
	}

	public static void popFragmentBackStack(FragmentManager fm) {
		fm.popBackStack();
	}

	public static void popBackAllStack(FragmentManager fm) {
		fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}
}
