package com.lyt.lib.mvp.internal.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * 创建日期： 2018/8/1 09:51
 * <p/>
 * 描 述：
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
@SuppressWarnings("ALL")
public abstract class FragmentPresenter<T extends IDelegate, D extends IMode> extends Fragment {
    public T viewDelegate;
    protected D dataMode;
    protected Activity mContext;

    public abstract void onInit(@Nullable Bundle savedInstanceState);

    protected abstract Class<T> getDelegateClass();

    protected abstract Class<D> getModeClass();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            viewDelegate = getDelegateClass().newInstance();
        } catch (java.lang.InstantiationException e) {
            throw new RuntimeException("create IDelegate error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IDelegate error");
        }
        try {
            dataMode = getModeClass().newInstance();
        } catch (java.lang.InstantiationException e) {
            throw new RuntimeException("create IMode error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IMode error");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        viewDelegate.create(inflater, container, savedInstanceState);
        DensityManager.setCustomDensity(this.getActivity(), this.getActivity().getApplication());
        return viewDelegate.getRootView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewDelegate.onCreate();
        dataMode.onCreate();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onInit(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (viewDelegate.getOptionsMenuId() != 0) {
            inflater.inflate(viewDelegate.getOptionsMenuId(), menu);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (viewDelegate == null) {
            try {
                viewDelegate = getDelegateClass().newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        if (dataMode == null) {
            try {
                dataMode = getModeClass().newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if (dataMode != null) {
            dataMode.onPause();
        }
        if (viewDelegate != null) {
            viewDelegate.onPause();
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        if (dataMode != null) {
            dataMode.onStop();
        }
        if (viewDelegate != null) {
            viewDelegate.onStop();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (dataMode != null) {
            dataMode.onDestroy();
        }
        if (viewDelegate != null) {
            viewDelegate.onDestroy();
        }
    }


}
