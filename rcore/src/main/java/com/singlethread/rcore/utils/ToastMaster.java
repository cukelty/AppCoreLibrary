package com.singlethread.rcore.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by litianyuan on 2017/8/31.
 */

public class ToastMaster {
    private ToastMaster() {
        throw new UnsupportedOperationException("can not be instant");
    }
    public static void popToast(Context context, String toastText, int during) {
        if (context == null) {
            return;
        }
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing())
                return;
        }
        Toast sToast = Toast.makeText(context, toastText, during);
        sToast.show();
    }

    public static void popToast(Context context, int textId, int during) {
        if (context == null) {
            return;
        }
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing())
                return;
        }
        Toast sToast = Toast.makeText(context, textId, during);
        sToast.show();
    }
    public static void centerToast(Context context,int  textid){
        if (context==null){
            return;
        }
        if (context instanceof Activity){
            if (((Activity) context).isFinishing())
                return;
        }
        Toast sToast=Toast.makeText(context,textid,Toast.LENGTH_LONG);
        sToast.setGravity(Gravity.CENTER,sToast.getXOffset()/2,sToast.getYOffset()/2);
        sToast.show();
    }

    public static void centerToast(Context context,String  textid){
        if (context==null){
            return;
        }
        if (context instanceof Activity){
            if (((Activity) context).isFinishing())
                return;
        }
        Toast sToast=Toast.makeText(context,textid,Toast.LENGTH_LONG);
        sToast.setGravity(Gravity.CENTER,sToast.getXOffset()/2,sToast.getYOffset()/2);
        sToast.show();
    }

    public static void popToast(Context context, int textId) {
        popToast(context, textId, Toast.LENGTH_SHORT);
    }

    public static void popToast(Context context, String toastText) {
        popToast(context, toastText, Toast.LENGTH_SHORT);
    }

    public static void shortToast(Context context,String toastText) {
        popToast(context.getApplicationContext(), toastText, Toast.LENGTH_SHORT);
    }

    public static void shortToast(Context context,int textId) {
        popToast(context.getApplicationContext(), textId, Toast.LENGTH_SHORT);
    }

    public static void longToast(Context context,String toastText) {
        popToast(context.getApplicationContext(), toastText, Toast.LENGTH_LONG);
    }

    public static void longToast(Context context,int textId) {
        popToast(context.getApplicationContext(), textId, Toast.LENGTH_LONG);
    }

}
