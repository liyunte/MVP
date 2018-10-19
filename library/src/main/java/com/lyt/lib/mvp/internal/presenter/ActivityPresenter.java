package com.lyt.lib.mvp.internal.presenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.lyt.lib.mvp.internal.mode.IMode;
import com.lyt.lib.mvp.internal.view.IDelegate;
import com.lyt.lib.mvp.util.DensityManager;


/**
 * ========================================
 * 作 者：liyunte
 * <p/>
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期： 2018/8/1 09:48
 * <p/>
 * 描 述：
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
@SuppressWarnings("ALL")
public abstract class ActivityPresenter<T extends IDelegate, D extends IMode> extends android.support.v7.app.AppCompatActivity {
    protected T viewDelegate;
    protected D dataMode;
    protected AppCompatActivity mContext;

    public abstract void onInit(Bundle savedInstanceState);

    protected abstract Class<T> getDelegateClass();

    protected abstract Class<D> getModeClass();

    public ActivityPresenter() {
        try {
            viewDelegate = getDelegateClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create IDelegate error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IDelegate error");
        }
        try {
            dataMode = getModeClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create IMode error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IMode error");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        DensityManager.setCustomDensity(this, this.getApplication());
        viewDelegate.create(getLayoutInflater(), null, savedInstanceState);
        setContentView(viewDelegate.getRootView());
        initToolbar();
        viewDelegate.onCreate();
        dataMode.onCreate();
        onInit(savedInstanceState);
    }

    protected void initToolbar() {
        Toolbar toolbar = viewDelegate.getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (viewDelegate == null) {
            try {
                viewDelegate = getDelegateClass().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (dataMode == null) {
            try {
                dataMode = getModeClass().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dataMode != null) {
            dataMode.onPause();
        }
        if (viewDelegate != null) {
            viewDelegate.onPause();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (dataMode != null) {
            dataMode.onStop();
        }
        if (viewDelegate != null) {
            viewDelegate.onStop();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (viewDelegate.getOptionsMenuId() != 0) {
            getMenuInflater().inflate(viewDelegate.getOptionsMenuId(), menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewDelegate != null) {
            viewDelegate.onDestroy();
            viewDelegate = null;
        }
        if (dataMode != null) {
            dataMode.onDestroy();
            dataMode = null;
        }
    }


}
