package com.cicada.startup.common.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.util.List;

/**
 * market
 * <p>
 * Create time: 16/5/12 15:38
 *
 * @author liuyun.
 */
public class MarketUtils {

    public static List<ResolveInfo> getMarketList(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        return infos;
    }

    public static void gotoMarket(Context paramContext) {
        StringBuilder localStringBuilder = new StringBuilder().append("market://details?id=");
        String str = paramContext.getPackageName();
        localStringBuilder.append(str);
        Uri localUri = Uri.parse(localStringBuilder.toString());
        Intent localIntent = new Intent("android.intent.action.VIEW", localUri);
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        paramContext.startActivity(localIntent);
    }

    public static boolean hasMarket(Context paramContext) {
        List<ResolveInfo> localList = getMarketList(paramContext);
        if (null != localList && localList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
