package com.singlethread.rcore.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by litianyuan on 2017/8/31.
 */

public class NetworkUtil {

    private NetworkUtil() {
        throw new UnsupportedOperationException();
    }

    interface NetType {
        int UNKNOWN = 0;
        int WIFI = 1;
        int _2G = 2;
        int _3G = 3;
        int _4G = 4;
        int CABLE = 5;
    }

    public static int getNetworkType(Context context) {
        int netType = NetType.UNKNOWN;
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = mgr.getActiveNetworkInfo();
        int connectType = ConnectivityManager.TYPE_DUMMY;
        int mobileType = TelephonyManager.NETWORK_TYPE_UNKNOWN;
        if (info != null && info.isAvailable()) {
            connectType = info.getType();
            switch (connectType) {
                case ConnectivityManager.TYPE_ETHERNET:
                    netType = NetType.CABLE;
                    break;

                case ConnectivityManager.TYPE_WIFI:
                case ConnectivityManager.TYPE_WIMAX:
                    netType = NetType.WIFI;
                    break;

                case ConnectivityManager.TYPE_MOBILE:
                case ConnectivityManager.TYPE_MOBILE_DUN:
                case ConnectivityManager.TYPE_MOBILE_SUPL:
                case ConnectivityManager.TYPE_MOBILE_MMS:
                case ConnectivityManager.TYPE_MOBILE_HIPRI: {

                    String strSubTypeName = info.getSubtypeName();
                    TelephonyManager tmgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    mobileType = tmgr.getNetworkType();
                    switch (mobileType) {
                        case TelephonyManager.NETWORK_TYPE_LTE://
                        case 18: // TelephonyManager.NETWORK_TYPE_IWLAN:  //4G
                            // TODO 4g
                            netType = NetType._4G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                        case 17: // TelephonyManager.NETWORK_TYPE_TD_SCDMA:  //3G
                            netType = NetType._3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                        case 16://TelephonyManager.NETWORK_TYPE_GSM
                            netType=NetType._2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                        default:
                            // http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
                            if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                                netType = NetType._3G;
                            } else {
                                netType = NetType.UNKNOWN;
                            }
                            break;
                    }
                }
                break;
                default:
                    break;
            }
        }
        return netType;
    }

    public static String getIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        int[] ipAddr = new int[4];
        ipAddr[0] = ipAddress & 0xFF;
        ipAddr[1] = (ipAddress >> 8) & 0xFF;
        ipAddr[2] = (ipAddress >> 16) & 0xFF;
        ipAddr[3] = (ipAddress >> 24) & 0xFF;
        StringBuilder ip = new StringBuilder().append(ipAddr[0]).append(".").append(ipAddr[1]).append(".").append(ipAddr[2])
                .append(".").append(ipAddr[3]);
        return ip.toString();

    }

    public static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) != 0;
    }

    /**
     * 判断当前网络连接是否为wifi
     *
     * @param context
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            String typeName = info.getTypeName().toLowerCase(); // WIFI/MOBILE
            if (typeName.equals("wifi")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isMobileNetWork(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            return isMobileNetworkInfo(info);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断此网络是否为mobile 注：适应三星新定义的双卡双待mobile类型
     *
     * @param netInfo
     * @return
     */
    public static boolean isMobileNetworkInfo(final NetworkInfo netInfo) {
        if (null == netInfo) {
            return false;
        }
        if (ConnectivityManager.TYPE_MOBILE == netInfo.getType() || ConnectivityManager.TYPE_MOBILE + 50 == netInfo.getType()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean is3Gor4G(Context context) {
        // 联通的3G UMTS HSDPA，移动和联通的2G为GPRS/EGDE，电信2G为CDMA，电信的3G为EVDO
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            int type = tm.getNetworkType();
            if (type == TelephonyManager.NETWORK_TYPE_HSDPA || type == TelephonyManager.NETWORK_TYPE_LTE
                    || type == TelephonyManager.NETWORK_TYPE_UMTS || type == TelephonyManager.NETWORK_TYPE_HSPAP
                    || type == TelephonyManager.NETWORK_TYPE_HSPA || type == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || type == TelephonyManager.NETWORK_TYPE_EHRPD || type == TelephonyManager.NETWORK_TYPE_EVDO_A
                    || type == TelephonyManager.NETWORK_TYPE_HSUPA || type == TelephonyManager.NETWORK_TYPE_EVDO_B)
                return true;
        } catch (Exception e) {
            // 权限？？
        }
        return false;
    }

    public static boolean isNetSupport(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null)
            return false;

        // return cm.getActiveNetworkInfo().isAvailable();
        try {
            NetworkInfo[] info = cm.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

//	public static int getNetworkType(Context context) {
//		int type = -1;
//		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//		if (cm != null) {
//			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
//			if (networkInfo != null) {
//				type = networkInfo.getType();
//			}
//		}
//		return type;
//	}

	/*
     * 调用traceRoute跟踪对应host的访问情况 Handler nethdl = new Handler(){
	 *
	 * @Override public void handleMessage(Message msg) { if(msg!=null &&
	 * msg.what==TraceConstants.TraceAction.TRACE_COMPLETE.ordinal()){
	 * Toast.makeText(getApplicationContext(), "r: "+msg.obj, 1).show(); } } };
	 * NetworkUtil.diagnoseNetwork(BaseApplication.getContext(),"113.143.11.34",
	 * nethdl);
	 */

    // ------------------ common -------------------

    /**
     * 检测网络连接
     *
     * @param context
     * @return 有网络连接返回true, 否则false
     */
    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return null != info && info.isAvailable();
    }

    public static boolean isWifiConnected(Context context) {
        if (context == null) {
            return false;
        }
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static NetworkInfo getActiveNetworkInfo(Context context) {
        ConnectivityManager cmManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = null;
        try {
            info = cmManager.getActiveNetworkInfo();
        } catch (Exception e) {
        }
        return info;
    }
}
