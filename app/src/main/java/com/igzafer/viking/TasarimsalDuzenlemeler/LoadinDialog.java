package com.igzafer.viking.TasarimsalDuzenlemeler;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.igzafer.viking.R;

public class LoadinDialog {
    static Dialog dialog;
    public static void isVisible(Context context, Boolean kontrol){

        if(kontrol){
            try {
                dialog = new Dialog(context);
                dialog.setContentView(R.layout.screenloading);
                dialog.setCancelable(false);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }catch (Exception e){

            }


        }else{
          try {
              dialog.dismiss();
          }catch (Exception e){

          }

        }

    }


}
