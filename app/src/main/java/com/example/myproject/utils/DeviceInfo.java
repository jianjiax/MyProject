package com.example.myproject.utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.example.myproject.MainApplication;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * get device info
 */
public class DeviceInfo {

    // 机器本身状态
    private String strIMEI; // IMEI号
    private String strIMSI; // IMSI号
    private String strBrand; // 手机品牌
    private String strModel; // 手机的型号
    private String strOSVer; // 操作系统版本号
    private String strSDKVer; // SDK版本
    private String strMacAddress; // MAC地址
    private static DeviceInfo deviceInfo = null;

    public static DeviceInfo getInstance() {
        if (deviceInfo == null) {
            deviceInfo = new DeviceInfo();
            deviceInfo.initValue();
        }

        return deviceInfo;
    }

    private DeviceInfo() {
    }

    private void initValue() {

        Context appCtx = MainApplication.getInstance();
        if (appCtx == null)
            return;

        TelephonyManager mTelephonyMgr = (TelephonyManager) appCtx.getSystemService(Context.TELEPHONY_SERVICE);

        try {
            // 获取手机IMEI
            strIMEI = mTelephonyMgr.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            // 获取手机IMSI
            strIMSI = mTelephonyMgr.getSubscriberId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 手机品牌
        strBrand = Build.MANUFACTURER.replace(' ', '_');

        // 手机型号
        strModel = Build.MODEL.replace(' ', '_');

        // android版本号
        strOSVer = Build.VERSION.RELEASE.replace(' ', '_');

        // SDK 版本号
        strSDKVer = Build.VERSION.SDK.replace(' ', '_');

        // MAC Address
        WifiManager wifiManager = (WifiManager) appCtx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        strMacAddress = wifiInfo.getMacAddress();
    }

    public static String getCpuName() {
        String cpuName = "N/A";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/proc/cpuinfo"));
            String line = reader.readLine();
            while (line != null) {
                if (line.toLowerCase().indexOf("processor") >= 0) {
                    String[] array = line.split(":\\s");
                    cpuName = array[1].trim();
                    break;
                }
                line = reader.readLine();
            }
            reader.close();
            reader = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cpuName;
    }

    public String getIMEI() {
        return strIMEI == null ? "" : strIMEI;
    }

    public String getIMSI() {
        return strIMSI == null ? "" : strIMSI;
    }

    public String getBrand() {
        return strBrand == null ? "" : strBrand;
    }

    public String getModel() {
        return strModel == null ? "" : strModel;
    }

    public String getOsVersion() {
        return strOSVer == null ? "" : strOSVer;
    }

    public String getSDKVersion() {
        return strSDKVer == null ? "" : strSDKVer;
    }

    public String getMACAddress() {
        return strMacAddress == null ? "" : strMacAddress;
    }

    public String getAndroidId() {
        try {
            Context ctx = MainApplication.getInstance();
            return Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            return "";
        }
    }

    public String getDeviceId() {

        String strDeviceId = getIMEI();
        strDeviceId += getIMSI();
        strDeviceId += getBrand();
        strDeviceId += getModel();
//        strDeviceId += getMACAddress();

        return CustomUtils.getMessageDigest(strDeviceId.getBytes());
    }

    public String getDeviceId2() {

        String strDeviceId = getIMEI();
        strDeviceId += getBrand();
        strDeviceId += getModel();
        strDeviceId += getMACAddress();

        return CustomUtils.getMessageDigest(strDeviceId.getBytes());
    }

}
