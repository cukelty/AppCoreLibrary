package com.singlethread.rcore.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.singlethread.rcore.R;

/**涉及到界面的帮助类
 * Created by litianyuan on 2017/8/31.
 */

public class ViewUtils {

    private static long lastClickTime;

    /**
     * 防止快速点击
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(500);
    }

    /**
     * 防止快速点击
     *
     * @return
     */
    public static boolean isFastDoubleClick(int limit) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (limit < timeD) {
            lastClickTime = time;
            return false;
        }
        lastClickTime = time;
        return true;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatuBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelOffset(resourceId);
        }
        return result;
    }


    public static final String RIGHT_IN = "right-in";
    public static final String BOTTOM_IN = "bottom-in";

    public static void anima(String animation, Activity ac) {
        switch (animation) {

            case RIGHT_IN:
                ac.overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                break;
            case BOTTOM_IN:
                ac.overridePendingTransition(R.anim.activity_bottom_in,
                        R.anim.activity_bottom_out);
                break;
            case "none":
            default:
                break;
        }
    }

    public static int getDisplayHeight(Context context) {
        return getDisplayMetrics(context).heightPixels;
    }

    public static int getDisplayWidth(Context context) {
        return getDisplayMetrics(context).widthPixels;
    }

    public static int getDimenPx(Context context, int dimenId) {
        return context.getResources().getDimensionPixelOffset(dimenId);
    }

    public static void hideSoftInputFromWindow(Context mContext, EditText view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
