package com.singlethread.rcore.widget.ImageView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;

/**
 * Created with Android Studio
 * <p>
 * Created by litianyuan on 2017/9/6.
 */

public class RemoteImageView extends AppCompatImageView {
    private static final int NONE = -1;
    /**
     * 网络图片url
     */
    private String mUrl;

    private int mDefaultImageResource = NONE;
    private Drawable mDefaultDrawable;
    // TODO: 2017/1/10 有可能导致内存溢出的地方或者是splash界面图加载了更大的图片
    private Transformation<Bitmap> bitmapTransformation;
    private int mErrorImageResource = NONE;
    private Drawable mErrorDrawable;

    public String getImageUri() {
        return mUrl;
    }

    public RemoteImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public RemoteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RemoteImageView(Context context) {
        super(context);
        init();
    }

    private void init() {
        // buildingOptions();
    }

    /**
     * 加载网络图片
     *
     * @param url eg. http://yourwebsite.com/abz.jpg
     */
    public void setImageUri(String url) {
        if (isActivityDestoryed())
            return;
        if (TextUtils.isEmpty(url)) {
            if (mDefaultDrawable != null) {
                setImageDrawable(mDefaultDrawable);
            } else if (mDefaultImageResource != NONE) {
                setImageResource(mDefaultImageResource);
            }
            return;
        }
        //判断url是否是双斜杠开头的
        if (url.startsWith("//")){
            url="https:"+url;
        }
        mUrl = url;
        DrawableTypeRequest<String> request= Glide.with(getContext()).load(url);
        if (bitmapTransformation != null) {
            request.bitmapTransform(bitmapTransformation);
        }
        if (mDefaultDrawable != null) {
            request.placeholder(mDefaultDrawable);
        } else if (mDefaultImageResource != NONE) {
            request.placeholder(mDefaultImageResource);
        }
        if (mErrorDrawable != null) {
            request.error(mErrorDrawable);
        } else if (mErrorImageResource != NONE) {
            request.error(mErrorImageResource);
        }
        request.into(this);
    }

    @SuppressLint("NewApi")
    private boolean isActivityDestoryed() {
        Context context = getContext();
        if (context instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return ((Activity) context).isDestroyed();
            } else {
                return ((Activity) context).isFinishing();
            }
        }
        return false;
    }

    public void setDefaultImageDrawable(Drawable defaultDrawable) {
        this.mDefaultDrawable = defaultDrawable;
    }

    @SuppressWarnings("deprecation")
    public void setDefaultImageBitmap(Bitmap defaultImageBitmap) {
        this.mDefaultDrawable = new BitmapDrawable(defaultImageBitmap);
    }

    /**
     * 设置默认图片资源id
     *
     * @param resId
     */
    public void setDefaultImageResource(int resId) {
        mDefaultImageResource = resId;
    }

    public void setImageUri(int defResId, String url) {
        if (mUrl == null || !this.mUrl.equals(url)) {
            setDefaultImageResource(defResId);
            setImageUri(url);
        }
    }

    public void setImageUri(Drawable defaultDrawable, String url) {
        if (mUrl == null || !this.mUrl.equals(url)) {
            setDefaultImageDrawable(defaultDrawable);
            setImageUri(url);
        }
    }

    public int getErrorImageResource() {
        return mErrorImageResource;
    }


    public void setErrorImageResource(int errorImageResource) {
        this.mErrorImageResource = errorImageResource;
    }

    public Drawable getErrorDrawable() {
        return mErrorDrawable;
    }

    public void setErrorDrawable(Drawable errorDrawable) {
        this.mErrorDrawable = errorDrawable;
    }

    public void setBitmapTransformation(Transformation<Bitmap> tf) {
        this.bitmapTransformation = tf;
    }
}
