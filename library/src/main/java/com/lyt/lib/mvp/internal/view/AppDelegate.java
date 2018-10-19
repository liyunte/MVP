package com.lyt.lib.mvp.internal.view;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * ========================================
 * 作 者：liyunte
 * <p/>
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期： 2018/8/1 09:11
 * <p/>
 * 描 述：
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
@SuppressWarnings("ALL")
public abstract class AppDelegate implements IDelegate {
    private final SparseArray<View> mViews = new SparseArray<>();
    private View rootView;
    private ViewGroup decorView;
    private View errorView;
    private View emptyView;
    private View loadView;

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getRootLayoutId(), container, false);
        decorView = (ViewGroup) getActivity().getWindow().getDecorView();
    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    @Override
    public Toolbar getToolbar() {
        return null;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public void setLoadView(View loadView) {
        this.loadView = loadView;
    }

    public void setErrorView(View errorView) {
        this.errorView = errorView;
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    @Override
    public void onCreate() {

    }

    public <T extends View> T bindView(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) rootView.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }

    public <T extends View> T get(int id) {
        return (T) bindView(id);
    }

    public void setOnClickListener(View.OnClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            get(id).setOnClickListener(listener);
        }
    }

    public void showLoadingView() {
        if (decorView != null&&loadView!=null) {
            decorView.removeView(loadView);
            decorView.addView(loadView);
        }
    }

    public void hideLoadingView() {
        if (decorView != null && loadView != null) {
            decorView.removeView(loadView);
        }
    }

    /**
     * 显示异常
     */
    public void showErrorView() {
        if (decorView != null && errorView != null) {
            decorView.removeView(errorView);
            decorView.addView(errorView);
        }


    }

    public void hideErrorView() {
        if (decorView != null && errorView != null) {
            decorView.removeView(errorView);
        }
    }

    public void showEmptyView() {
        hideLoadingView();
        hideErrorView();
        if (decorView != null && emptyView != null) {
            decorView.removeView(emptyView);
            decorView.addView(emptyView);
        }
    }

    public void hideEmptyView() {
        if (decorView != null && emptyView != null) {
            decorView.removeView(emptyView);
        }
    }

    public void hideViews(){
        hideEmptyView();
        hideErrorView();
        hideLoadingView();
    }
    public AppCompatActivity getActivity() {
        return (AppCompatActivity) rootView.getContext();
    }

    @Override
    public void onDestroy() {
        hideViews();
        if (loadView != null) {
            loadView = null;
        }
        if (errorView != null) {
            emptyView = null;
        }
        if (errorView != null) {
            errorView = null;
        }
        if (rootView != null) {
            rootView = null;
        }
        if (mViews != null) {
            mViews.clear();
        }
        if (decorView != null) {
            decorView = null;
        }
    }

}
