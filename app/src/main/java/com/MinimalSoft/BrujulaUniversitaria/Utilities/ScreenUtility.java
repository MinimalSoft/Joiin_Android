package com.MinimalSoft.BrujulaUniversitaria.Utilities;

import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenUtility {

    private static float density;
    private static float dpWidth;
    private static float dpHeight;
    private static float physicalWidth;
    private static float physicalHeight;
    private static int dpAppBarLayoutHeight;

    public ScreenUtility(AppCompatActivity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        density = activity.getResources().getDisplayMetrics().density;
        physicalWidth = outMetrics.widthPixels;
        physicalHeight = outMetrics.heightPixels;

        dpHeight = physicalWidth / density;
        dpWidth = physicalHeight / density;
        dpAppBarLayoutHeight = 140; // Computed value XD
    }

    public static int getPhysicalHeight() {
        return (int) physicalHeight;
    }

    public static int getPhysicalWidth() {
        return (int) physicalWidth;
    }

    public static float getDpHeight() {
        return dpHeight;
    }

    public static float getDpWidth() {
        return dpWidth;
    }

    public static float getDensity() {
        return density;
    }

    public static int getAppBarPhysicalHeight() {
        return (int) (dpAppBarLayoutHeight * density);
    }
}
