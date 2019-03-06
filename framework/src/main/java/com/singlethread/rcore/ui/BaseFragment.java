package com.singlethread.rcore.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.singlethread.rcore.utils.NetworkUtil;

/**
 * 基本类
 *
 * @author Ludaiqian
 */
public abstract class BaseFragment extends Fragment implements IContext {

    private View mLayoutView;
    private boolean mIsCompleted = false;
    private LayoutInflater mActinflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (mLayoutView == null) {
            onPreInflate(inflater, container, savedInstanceState);
            mLayoutView = inflate(inflater);
            onInflateView(mLayoutView);
            onPrepareData();
            mIsCompleted = true;
        } else {
            ViewGroup parent = (ViewGroup) mLayoutView.getParent();
            if (parent != null)
                parent.removeView(mLayoutView);
            Log.d("BaseFragment", "绑定完了");
            onRefreshData();
        }
        mActinflater = inflater;
        return mLayoutView;

    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    protected void onPreInflate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    }

    protected View inflate(LayoutInflater inflater) {
        return inflater.inflate(getContentViewId(), null);
    }

    public boolean isCompleted() {
        return mIsCompleted;
    }

    protected LayoutInflater getInflater() {
        return mActinflater;
    }

    public View getContentView() {
        return mLayoutView;
    }

    /**
     * 只运行一次
     */
    public void onInflateView(View contentView) {

    }

    /**
     * 只运行一次
     */
    public void onPrepareData() {
    }

    public void onRefreshData() {

    }

    protected abstract int getContentViewId();

    public final boolean isNetworkAvailable() {
        return NetworkUtil.isNetworkAvailable(getActivity());
    }

    protected void back() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }
    /**
     * 统一构造子类
     *
     * @param className
     * @param bundle
     * @param <T>
     * @return
     */
    public static <T extends BaseFragment> T newInstance(Class<T> className,  Bundle bundle) {
        T instance = null;
        try {
            instance = className.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (bundle != null) {
            instance.setArguments(bundle);
        }
        return instance;
    }

    /**
     * Fragment跳转， 将一个layout替换为新的fragment。
     *
     * @param fm
     * @param fragmentClass
     * @param replaceLayoutId
     * @param args
     */
    public static void replace(FragmentManager fm, Class<? extends Fragment> fragmentClass, int replaceLayoutId, Bundle args) {
        replace(fm, fragmentClass, replaceLayoutId, fragmentClass.getSimpleName(), args);
    }

    /**
     * Fragment跳转， 将一个layout替换为新的fragment。
     *
     * @param fm
     * @param replaceLayoutId
     * @param fragmentClass
     * @return
     */
    public static Fragment replace(FragmentManager fm, int replaceLayoutId, Class<? extends Fragment> fragmentClass) {
        return replace(fm, fragmentClass, replaceLayoutId, fragmentClass.getSimpleName(), null);
    }

    /**
     * Fragment跳转， 将一个layout替换为新的fragment。
     *
     * @param fm
     * @param fragmentClass
     * @param tag
     * @param args
     * @return
     */
    public static Fragment replace(FragmentManager fm, Class<? extends Fragment> fragmentClass, int replaceLayoutId, String tag,
                                   Bundle args) {
        // mIsCanEixt = false;
        Fragment fragment = fm.findFragmentByTag(tag);
        boolean isFragmentExist = true;
        if (fragment == null) {
            try {
                isFragmentExist = false;
                fragment = fragmentClass.newInstance();
                if (args != null)
                    fragment.setArguments(args);
                else {
                    fragment.setArguments(new Bundle());
                }

            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            if (args != null) {
                if (fragment.getArguments() != null)
                    fragment.getArguments().putAll(args);
                else
                    fragment.setArguments(args);
            }
        }
        if (fragment == null)
            return null;
        if (fragment.isAdded()) {
            // fragment.onResume();
            return fragment;
        }
        FragmentTransaction ft = fm.beginTransaction();
        if (isFragmentExist) {
            ft.replace(replaceLayoutId, fragment);
        } else {
            ft.replace(replaceLayoutId, fragment, tag);
        }

        ft.addToBackStack(tag);
        ft.commitAllowingStateLoss();
        return fragment;
    }

}
