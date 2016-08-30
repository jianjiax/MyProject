package com.example.myproject.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kain on 2016/8/13.
 */
public class CustomUtils {

    /**
     * 是否快速点击
     *
     * @param lastClickTime 上一次点击时间
     * @return
     */
    public synchronized static boolean isFastClick(long lastClickTime) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        return false;
    }

    /**
     * md5加密
     *
     * @param buffer
     * @return MD5
     * @Description: TODO
     */
    public final static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * SHA1加密
     */
    public static String SHA1Encrypt(String strSrc) {
        // parameter strSrc is a string will be encrypted,
        // parameter encName is the algorithm name will be used.
        // encName dafault to "MD5"
        MessageDigest md = null;
        String strDes = null;

        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(bt);
            strDes = bytes2Hex(md.digest()); // to HexString
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Invalid algorithm.");
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    /**
     * get version
     *
     * @param context
     * @param isVersionNum true：get version num，false：get version name
     * @return
     */
    public static String getApplicationVersion(Context context,
                                               boolean isVersionNum) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info;

        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            if (isVersionNum) {
                return info.versionCode + "";
            } else {
                return info.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 参数自然排序，按照k=v方式&连接
     */
    public static String paramSort(HashMap<String, String> map) {
        if (map == null || map.size() < 1) {
            return "";
        }
        List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(
                map.entrySet());
        // 排序前
        for (int i = 0; i < infoIds.size(); i++) {
            String id = infoIds.get(i).toString();
        }

        // 排序
        Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {
            public int compare(Map.Entry<String, String> o1,
                               Map.Entry<String, String> o2) {
                // return (o2.getValue() - o1.getValue());
                return (o1.getKey()).toString().compareTo(o2.getKey());
            }
        });

        // 排序后
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < infoIds.size(); i++) {
            String id = infoIds.get(i).toString();
            System.out.println(id);
            sb.append(id);
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager
                        .getApplicationInfo(ctx.getPackageName(),
                                PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }

        return resultData;
    }

    public static boolean isEmptyForArrayList(ArrayList list) {
        if (list == null || list.size() < 1) {
            return true;
        }
        return false;
    }

    public static int getColorWithAlpha(float alpha, int baseColor) {
        int a = Math.min(255, Math.max(0, (int) (alpha * 255))) << 24;
        int rgb = 0x00ffffff & baseColor;
        return a + rgb;
    }

    /**
     * 比较两个数组是否相等
     *
     * @param a
     * @param a2
     * @return
     */
    public static boolean arrayEquals(String[] a, String[] a2) {
        if (a == a2)
            return true;
        if (a == null || a2 == null)
            return false;
        int length = a.length;
        if (a2.length != length)
            return false;
        for (int i = 0; i < length; i++)
            if (a[i] != a2[i])
                return false;
        return true;
    }


    /**
     * 得到屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Activity context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;

    }

    /**
     * 得到屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Activity context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }

    /**
     * 得到状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Activity context) {
        int statusHeight = 0;
        Rect frame = new Rect();
        context.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusHeight = frame.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());
                statusHeight = context.getResources().getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 是否开启 GPS
     *
     * @param context
     * @return
     */
    public static boolean isOpenGPS(Context context) {

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNPEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (isGPSEnable || isNPEnable)
            return true;


        return false;
    }

    public static void openGPS(Context context) {

        Intent GPSIntent = new Intent();

        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");

        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));

        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    public static boolean isWifiConnected(Context paramContext) {
        ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext.getSystemService("connectivity");
        if (localConnectivityManager != null) {
            NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
            if ((localNetworkInfo != null) && (localNetworkInfo.getType() == 1))
                return true;
        }
        return false;
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    /**
     * @param paramDouble
     * @param keepNum     保留多少位小数
     * @return
     */
    public static double roundDouble(double paramDouble, int keepNum) {
        return new BigDecimal(paramDouble).setScale(keepNum, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }

    /**
     * 从drawable中异步加载本地图片
     *
     * @param imageId
     * @param imageView
     */
    public static void displayFromDrawable(int imageId, ImageView imageView) {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage("drawable://" + imageId,
                imageView);
    }

    /**
     * 从内存卡中异步加载本地图片
     *
     * @param uri
     * @param imageView
     */
    public static void displayFromSDCard(String uri, ImageView imageView) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage("file://" + uri, imageView);
    }

    public static void setViewSize(View paramView, int paramInt1, int paramInt2) {
        ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
        if (paramInt2 >= 0)
            localLayoutParams.height = paramInt2;
        if (paramInt1 >= 0)
            localLayoutParams.width = paramInt1;
        paramView.setLayoutParams(localLayoutParams);
    }
}
