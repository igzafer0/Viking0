package com.igzafer.viking.amaleler;

import android.graphics.Color;
import android.view.Window;

import com.crowdfire.cfalertdialog.CFAlertDialog;

public class Dialog {
    public static void createDialog(Window w, String tittle, String message,int DissmissTime){
        if(DissmissTime==0){
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(w.getContext())
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                    .setAutoDismissAfter(1500)
                    .setDialogBackgroundColor(Color.parseColor("#ffffff"))
                    .setTitle(tittle)
                    .setMessage(message);
            builder.show();
        }else{
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(w.getContext())
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                    .setAutoDismissAfter(DissmissTime)
                    .setDialogBackgroundColor(Color.parseColor("#ffffff"))
                    .setTitle(tittle)
                    .setMessage(message);
            builder.show();
        }


    }
}
