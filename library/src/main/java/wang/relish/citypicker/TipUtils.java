/*
 * 杭州智晗信息技术有限公司 版权所有.
 * Copyright © 2020. Zhihan Co., Ltd. All Rights Reserved.
 *
 */

package wang.relish.citypicker;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static android.util.Log.INFO;
import static android.util.Log.VERBOSE;

public final class TipUtils {

    public static boolean dbg = false;
    private TipUtils() { }

    @Nullable
    public static Activity getActivity(@Nullable Context cont) {
        if (cont == null) {
            return null;
        } else if (cont instanceof Activity) {
            return (Activity) cont;
        } else if (cont instanceof ContextWrapper) {
            return getActivity(((ContextWrapper) cont).getBaseContext());
        }
        return null;
    }

    public static void log(final String tag, final int level, final String format, Object... args) {
        if (dbg) {
            switch (level) {
                case Log.DEBUG:
                    Log.d(tag, String.format(format, args));
                    break;
                case Log.ERROR:
                    Log.e(tag, String.format(format, args));
                    break;
                case INFO:
                    Log.i(tag, String.format(format, args));
                    break;
                case Log.WARN:
                    Log.w(tag, String.format(format, args));
                    break;
                default:
                case VERBOSE:
                    Log.v(tag, String.format(format, args));
                    break;
            }
        }
    }

    public static boolean equals(@Nullable Object a, @Nullable Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }

    public static boolean rectContainsRectWithTolerance(@NonNull final Rect parentRect, @NonNull final Rect childRect, final int t) {
        return parentRect.contains(childRect.left + t, childRect.top + t, childRect.right - t, childRect.bottom - t);
    }

    public static Point calculateDynamiccoordinate(){
        Point point = new Point();
        return point;
    }
}
