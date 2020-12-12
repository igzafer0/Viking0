package com.igzafer.viking.amaleler;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.igzafer.viking.R;

public class StatusAndNavbar {
    public static void setColor(Context ctx, Window w, int status, int nav){
        w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if(status!=0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                w.setStatusBarColor(ContextCompat.getColor(ctx, status));
            }
        }if(nav!=0){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                w.setNavigationBarColor(ContextCompat.getColor(ctx, nav));
            }
        }



    }
}
