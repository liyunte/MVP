package com.lyt.lib.mvp.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * ========================================
 * 作 者：liyunte
 * <p/>
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期： 2018/7/25 11:15
 * <p/>
 * 描 述：屏幕适配
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */

public class DensityManager {
    private static  float sNoncompatDensity;
    private static  float sNoncompatScaledDensity;
    public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application){
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity==0){
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks2() {
                @Override
                public void onTrimMemory(int level) {

                }

                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                        if (newConfig!=null&&newConfig.fontScale>0){
                            sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                        }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        final float targetDensity = appDisplayMetrics.widthPixels/360;
        final float targetScaledDensity = targetDensity*sNoncompatScaledDensity/sNoncompatDensity;
        final int targetDensityDpi = (int)(160*targetDensity);
        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;
        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
