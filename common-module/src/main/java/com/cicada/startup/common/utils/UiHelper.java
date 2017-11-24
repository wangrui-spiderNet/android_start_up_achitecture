package com.cicada.startup.common.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cicada.startup.common.R;
import com.cicada.startup.common.ui.wight.CustomDialog;

import java.io.File;
import java.util.List;

/**
 * ui相关帮助工具类
 * <p/>
 * 创建时间: 2014-8-20 下午3:56:12 <br/>
 * 
 * @author xnjiang
 * @version
 * @since v0.0.1
 */
public class UiHelper {
	private static final String TAG = "UiHelper";
	private static android.os.Vibrator vibrator;

	public static void showTipDialog(Context context, String message) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		builder.setMessage(message).setPositiveButton(context.getResources().getString(R.string.know), new OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				arg0.dismiss();
			}
		}).create().show();
	}

	public static void showTipDialog(Context context, String message, OnClickListener listener) {
		CustomDialog.Builder builder = new CustomDialog.Builder(context);
		CustomDialog dialog = builder.setMessage(message).setPositiveButton(context.getResources().getString(R.string.know), listener).create();
		dialog.setCancelable(false);
		dialog.show();
	}

	/**
	 * 添加快捷方式
	 * 
	 * @param activity
	 * @param shortcutNameID
	 * @param shortcutIconID
	 */
	public static void createShortCut(Activity activity, int shortcutNameID, int shortcutIconID) {
		String shortcutName = activity.getString(shortcutNameID);
		if (hasShortCut(activity, shortcutName)) {
			// TODO:暂时检测代码不起作用 deleteShortCut(activity, shortcutName);
		}
		deleteShortCut(activity, shortcutName);
		Intent intent = new Intent();
		intent.setClass(activity, activity.getClass());
		/* 以下两句是为了在卸载应用的时候同时删除桌面快捷方式 */
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		// 不允许重复创建
		shortcutintent.putExtra("duplicate", false);
		// 需要现实的名称
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
		// 快捷图片
		Parcelable icon = Intent.ShortcutIconResource.fromContext(activity.getApplicationContext(), shortcutIconID);
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		// 点击快捷图片，运行的程序主入口
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		// 发送广播。OK
		activity.sendBroadcast(shortcutintent);
	}

	/**
	 * 判断是否存在快捷方式
	 * 
	 * @param activity
	 * @param shortcutName
	 * @return
	 */
	public static boolean hasShortCut(Activity activity, String shortcutName) {
		String url = "";
		int systemversion = Integer.parseInt(android.os.Build.VERSION.SDK);
		/* 大于8的时候在com.android.launcher2.settings 里查询（未测试） */
		if (systemversion < 8) {
			url = "content://com.android.launcher.settings/favorites?notify=true";
		} else {
			url = "content://com.android.launcher2.settings/favorites?notify=true";
		}
		ContentResolver resolver = activity.getContentResolver();
		Cursor cursor = resolver.query(Uri.parse(url), null, "title=?", new String[] { shortcutName }, null);
		if (cursor != null && cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		return false;
	}

	/**
	 * 删除快捷方式
	 * 
	 * @param activity
	 * @param shortcutName
	 */
	public static void deleteShortCut(Activity activity, String shortcutName) {
		Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
		// 快捷方式的名称
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
		// 在网上看到到的基本都是一下几句，测试的时候发现并不能删除快捷方式。
		// String appClass = activity.getPackageName()+"."+
		// activity.getLocalClassName();
		// ComponentName comp = new ComponentName( activity.getPackageName(),
		// appClass);
		// shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new
		// Intent(Intent.ACTION_MAIN).setComponent(comp));
		/** 改成以下方式能够成功删除，估计是删除和创建需要对应才能找到快捷方式并成功删除 **/
		Intent intent = new Intent();
		intent.setClass(activity, activity.getClass());
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.LAUNCHER");
		shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
		activity.sendBroadcast(shortcut);
	}

	/*
	 * 获取launcherApp
	 */
	private static String getLauncherPkgName(Context context) {
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> list = activityManager.getRunningAppProcesses();
		for (RunningAppProcessInfo info : list) {
			String pkgName = info.processName;
			if (pkgName.contains("launcher") && pkgName.contains("android")) {
				return pkgName;
			}
		}
		return null;
	}

	/**
	 * 判断是否存在快捷方式（升级版本）
	 * */
	private static boolean hasShortcut(Context context, String lableName) {

		String url = "";
		url = "content://" + getLauncherPkgName(context) + ".settings/favorites?notify=true";
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(Uri.parse(url), null, "title=?", new String[] { lableName }, null);

		if (cursor != null && cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		return false;
	}

	/**
	 * 输入框是否为空
	 */
	public static boolean isEditTextEmpty(Context context, EditText editText, String msg) {
		if ((editText.getText().toString()).equals("")) {
			ToastUtils.toastShort(context, msg);
			return false;
		}
		return true;
	}

	/**
	 * 公告标题栏颜色设置
	 */
	public static SpannableStringBuilder spanBoardTitle(String title, String key) {
		if (TextUtils.isEmpty(title)) {
			return null;
		}
		int start = title.indexOf(key);
		int end = start + key.length();

		SpannableStringBuilder spannable = new SpannableStringBuilder(title);
		if (start != -1 && !TextUtils.isEmpty(key)) {
			ForegroundColorSpan span = new ForegroundColorSpan(0xffffdd66);
			spannable.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return spannable;
	}

	/**
	 * 已添加通讯录人数字体显示设置
	 */
	public static SpannableStringBuilder spanAddedContacts(String title, String key) {
		if (TextUtils.isEmpty(title)) {
			return null;
		}
		int start = title.indexOf(key);
		int end = start + key.length();

		SpannableStringBuilder spannable = new SpannableStringBuilder(title);
		if (start != -1 && !TextUtils.isEmpty(key)) {
			ForegroundColorSpan span = new ForegroundColorSpan(0xfff69936);
			spannable.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return spannable;
	}

	/**
	 * 取消通知栏提醒.
	 * 
	 * @param context
	 */
	public static void cancelNotification(Context context) {
		NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		if (mNotificationManager != null) {
			mNotificationManager.cancel(1);
		}
	}

	/**
	 * scrollview与listview合用会出现listview只显示一行多点。此方法是为了定死listview的高度就不会出现以上状况
	 * 算出listview的高度
	 */
	public static void setListViewHeightBasedOnChildrenAndCount(ListView listView, int maxCount) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalCount = listAdapter.getCount();
		int totalHeight = 0;
		if (totalCount > maxCount) {
			totalCount = maxCount;
		}
		for (int i = 0; i < totalCount; i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(1, 1);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * totalCount) + listView.getPaddingTop() + listView.getPaddingBottom();
		listView.setLayoutParams(params);
	}

	/**
	 * scrollview与listview合用会出现listview只显示一行多点。此方法是为了定死listview的高度就不会出现以上状况
	 * 算出listview的高度
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(1, 1);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1)) + listView.getPaddingTop() + listView.getPaddingBottom();
		listView.setLayoutParams(params);
	}

	/**
	 * scrollview与gridview合用会出现gridview只显示一行多点。此方法是为了定死gridview的高度就不会出现以上状况
	 * 算出gridview的高度
	 */
	public static void setGridViewHeightBasedOnChildren(GridView gridView, int num) {
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < (listAdapter.getCount() / num + 1); i++) {
			View listItem = listAdapter.getView(i, null, gridView);
			listItem.measure(1, 1);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight + (gridView.getHorizontalFadingEdgeLength() * (listAdapter.getCount() - 1));
		gridView.setLayoutParams(params);
	}

	/**
	 * 弹起软键盘
	 */
	public static void showSoftInputMethod(Context context, EditText editText) {
		try {
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 隐藏软键盘，界面无焦点
	 */
	public static boolean hideSoftInput(Context activity, View view) {
		try {
			InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (view.getWindowToken() != null) {
				return imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			}
            return false;
		} catch (Exception e) {
			e.printStackTrace();
            return false;
		}
	}

	public static void hideSoftKeyboard(View view) {
		if (view == null)
			return;
		View focusView = null;
		if (view instanceof EditText)
			focusView = view;
		Context context = view.getContext();
		if (context != null && context instanceof Activity) {
			Activity activity = ((Activity) context);
			focusView = activity.getCurrentFocus();
		}

		if (focusView != null) {
                /*
                if (focusView.isFocusable()) {
                    focusView.setFocusable(false);
                    focusView.setFocusable(true);
                }
                */
			if (focusView.isFocused()) {
				focusView.clearFocus();
			}
			InputMethodManager manager = (InputMethodManager) focusView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
			manager.hideSoftInputFromWindow(focusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
			manager.hideSoftInputFromInputMethod(focusView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 隐藏软键盘，界面有焦点
	 */
	public static boolean hideSoftInput(Activity activity) {
		try {
			InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (activity.getCurrentFocus() != null) {
				if (activity.getCurrentFocus().getWindowToken() != null) {
					return imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
            return false;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean hideSoftInputDelayed(final Activity activity, long delayed) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				hideSoftInput(activity);
			}
		}, delayed);
		return false;
	}


	public static void toggleSoftKeyboardDelay(final Context context, long time) {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}
		}, time);
	}

	/**
	 * 开启震动
	 * 
	 * @param context
	 * @param pattern
	 *            前3个的值是设置震动的大小，在这边可以把数值改成一大一小,这样就可以明显感觉出震动的差异，而最后一个值是设置震动的时间。
	 * @param repeat
	 *            当repeat = 0时，震动会一直持续，若repeat = −1时，震动只会出现一轮，运行完毕后就不会再有动作。
	 */
	public static void onVibrator(Context context, long[] pattern, int repeat) {
		vibrator = (android.os.Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		// 心跳震动
		// long[] pattern = {800,50,400,30};

		vibrator.vibrate(pattern, repeat);
	}

	/**
	 * 播放系统默认的sound
	 * 
	 * @author liuyun
	 * @param context
	 * @since v0.1
	 */
	public static void playDefaultSound(Context context) {
		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), notification);
		r.play();
	}

	/**
	 * 设置对话框的宽度为(屏幕宽度*factor).
	 * 
	 * @param dialog
	 * @param factor
	 *            对话框宽度站屏幕宽度的比例[0, 1].
	 */
	public static void setDialogWidth(Dialog dialog, float factor) {
		Window dialogWindow = dialog.getWindow();
		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		DisplayMetrics dm = new DisplayMetrics();
		dialogWindow.getWindowManager().getDefaultDisplay().getMetrics(dm);
		lp.width = (int) (dm.widthPixels * factor);
		dialogWindow.setAttributes(lp);
	}

	/**
	 * 判断程序是否安装
	 * 
	 * @param context
	 *            上下文对象
	 * @param packageName
	 *            包名
	 */
	public static boolean checkApkExist(Context context, String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			@SuppressWarnings("unused")
			ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}

	/**
	 * 软件安装
	 */
	public static void installApp(Context context, String apkPath) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
		context.startActivity(intent);
	}

	/**
	 * 打开应用
	 * 
	 * @param context
	 *            上下文对象
	 * @param packageName
	 *            包名
	 */
	public static void openApp(Context context, String packageName, String className) {
		try {
			Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
			if (intent != null) {
				context.startActivity(intent);
			} else {
				if (!TextUtils.isEmpty(className)) {
					Intent newIntent = new Intent();
					ComponentName comp = new ComponentName(packageName, className);
					newIntent.setComponent(comp);
					newIntent.setAction("android.intent.action.VIEW");
					context.startActivity(newIntent);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 打开系统的接入点设置页面.也就是(Apn Settings页面)
	 */
	public static void openApnSettings(Context context) {
		context.startActivity(new Intent(Settings.ACTION_APN_SETTINGS));
	}

	public static void setTextView(TextView textView, String text, String defaultStr) {
		if (TextUtils.isEmpty(text)) {
			textView.setText(defaultStr);
		} else {
			textView.setText(text);
		}
	}


	/**
	 * @param context
	 *            拨打电话
	 * @param phoneNumber
	 */
	public static void startDialer(Context context, String phoneNumber) {
		try {
			Intent dial = new Intent();
			dial.setAction(Intent.ACTION_DIAL);
			dial.setData(Uri.parse("tel:" + phoneNumber));
			context.startActivity(dial);
		} catch (Exception ex) {
			ToastUtils.showToastImage(context, "Sorry, we couldn't find any app to place a phone call!",0);
		}
	}

	/*
	 * 
	 * 设置小时分钟的字体大小
	 * 
	 * @param context
	 * 
	 * @return
	 */
	public static int getTimeTextSize(Context context) {
		return (int) context.getResources().getDimension(R.dimen.remindWheelViewSizeTime);
	}

	/**
	 * 设置日期字体大小
	 * 
	 * @param context
	 * @return
	 */
	public static int getDateTextSize(Context context) {
		return (int) context.getResources().getDimension(R.dimen.remindWheelViewSizeDate);
	}

}
