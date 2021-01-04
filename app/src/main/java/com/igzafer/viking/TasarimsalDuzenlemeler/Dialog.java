package com.igzafer.viking.TasarimsalDuzenlemeler;

import android.graphics.Color;
import android.view.Window;

import com.crowdfire.cfalertdialog.CFAlertDialog;

public class Dialog {
    String _defErrorTitle = "Bir hata oluştu";
    String _defErrorBody = "Sebebini çözemediğimiz bir hata oluştu, mühendislerimize haber verdik.";
    //bodyli
    public void createDialog(Window w, String message, int DissmissTime){
           if(DissmissTime==0){
               try {
                   CFAlertDialog.Builder builder = new CFAlertDialog.Builder(w.getContext())
                           .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                           .setAutoDismissAfter(1500)
                           .setDialogBackgroundColor(Color.parseColor("#ffffff"))
                           .setTitle(_defErrorTitle)
                           .setMessage(message);
                   builder.show();
               }catch (Exception ignored) {

               }

           }else{
               try {
                   CFAlertDialog.Builder builder = new CFAlertDialog.Builder(w.getContext())
                           .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                           .setDialogBackgroundColor(Color.parseColor("#ffffff"))
                           .setTitle(_defErrorTitle)
                           .setMessage(message);
                   builder.show();
               }catch (Exception ignored) {

               }
           }

    }
    //bodysiz
    public void createDialog(Window w, int DissmissTime){
            if(DissmissTime==0){
                try {
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(w.getContext())
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                            .setAutoDismissAfter(1500)
                            .setDialogBackgroundColor(Color.parseColor("#ffffff"))
                            .setTitle(_defErrorTitle)
                            .setMessage(_defErrorBody);
                    builder.show();
                }
                catch (Exception ignored) {
                }

            }else{
                try {
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(w.getContext())
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION)
                            .setDialogBackgroundColor(Color.parseColor("#ffffff"))
                            .setTitle(_defErrorTitle)
                            .setMessage(_defErrorBody);
                    builder.show();
                }
                catch (Exception ignored) {
                }
            }
        }
}
