package com.lyt.lib.mvp.internal.mode;

/**
 * ========================================
 * 作 者：liyunte
 * <p/>
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期： 2018/8/1 09:57
 * <p/>
 * 描 述：
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */

public interface IMode {
    /**
     * 初始化网络请求
     */
     void onCreate();

    /**
     *暂停
     */
    void onPause();

    /**
     * 停止
     */
    void onStop();

    /**
     * 回收
     */
    void onDestroy();
}
