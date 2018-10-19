package com.lyt.lib.mvp.util;

import android.app.Activity;
import android.os.Build;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 重写activity.onSaveInstanceState方法
 *
 *  @Override
protected void onSaveInstanceState(Bundle outState) {
super.onSaveInstanceState(outState);
invokeFragmentManagerNoteStateNotSaved(this);
}
 */
public class IllegalStateHelp {
    /**
     * 处理java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
     */
    public static void invokeFragmentManagerNoteStateNotSaved(Activity context) {
       Method noteStateNotSavedMethod =null;
       Object fragmentMgr = null;
        String[] activityClassName = {"Activity", "FragmentActivity"};
        //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return;
        }
        try {
            if (noteStateNotSavedMethod != null && fragmentMgr != null) {
                noteStateNotSavedMethod.invoke(fragmentMgr);
                return;
            }
            Class cls = context.getClass();
            do {
                cls = cls.getSuperclass();
            } while (!(activityClassName[0].equals(cls.getSimpleName())
                    || activityClassName[1].equals(cls.getSimpleName())));

            Field fragmentMgrField = prepareField(cls, "mFragments");
            if (fragmentMgrField != null) {
                fragmentMgr = fragmentMgrField.get(context);
                noteStateNotSavedMethod = getDeclaredMethod(fragmentMgr, "noteStateNotSaved");
                if (noteStateNotSavedMethod != null) {
                    noteStateNotSavedMethod.invoke(fragmentMgr);
                }
            }

        } catch (Exception ex) {
        }
    }

    private static Field prepareField(Class<?> c, String fieldName) throws NoSuchFieldException {
        while (c != null) {
            try {
                Field f = c.getDeclaredField(fieldName);
                f.setAccessible(true);
                return f;
            } finally {
                c = c.getSuperclass();
            }
        }
        throw new NoSuchFieldException();
    }

    private static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
            }
        }
        return null;
    }
}
