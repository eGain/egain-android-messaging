package com.egain.ps.sdk.internal.data.ui;

import android.app.Activity;
import android.content.Intent;

import com.egain.ps.sdk.R;

public class brandingUtil {

    private static int sTheme;

    public final static int DEFAULT = 0;
    public final static int CUSTOM = 1;

    public static void setTheme(int theme) {
        sTheme = theme;
    }

    public static int getTheme() {
        return sTheme;
    }

    public static void changeToTheme(Activity activity, int theme) {
        sTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);
    }

    public static void onActivityCreateSetTheme(Activity activity) {
        switch (sTheme) {
            default:
            case DEFAULT:
                activity.setTheme(R.style.Theme_EGainLiveChat);
                System.out.println("chose default theme");
                break;
            case CUSTOM:
                activity.setTheme(R.style.Theme_Custom);
                System.out.println("chose custom theme");
                break;
        }
    }
}
