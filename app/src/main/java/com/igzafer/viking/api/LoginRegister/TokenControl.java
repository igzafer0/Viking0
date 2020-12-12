package com.igzafer.viking.api.LoginRegister;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.igzafer.viking.DialogFragment.Erro;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.api.Test.ConnectionTest;

public class TokenControl {
    //Uygulamaya önceden giriş yapmışsan ve tekrar dönüyorsan burası çalışacak.
    public static void LoginControl(Context ctx,ControlInterface callback){
        ConnectionTest.iConnect(new ConnectionTest.conTest() {
            @Override
            public void Connected(Boolean connected) {
                if(connected){
                    //internet var mı
                    if(LocalDatabase.isLogin(ctx)){
                        //local database de giriş yapılmış mı
                        TokenIsValid.getTokenValid(ctx, valid -> {
                            if (valid) {
                                //token geçerli mi, geçerli true döndürüyor.
                                callback.LoginSuccsess(true);
                            } else {
                                //token geçerli değil, login oluyor
                                Login.login(ctx, LocalDatabase.getLoginDetails(ctx), new LoginInterface() {
                                    @Override
                                    public void LoginResponse(Boolean succsess, ErrorModel errorModel) {
                                        if(succsess) {
                                            callback.LoginSuccsess(true);
                                            //login oldu true döndürüyor
                                        } else {
                                            callback.LoginSuccsess(false);
                                            //login olamadı false döndürüyor
                                        }
                                    }

                                });
                            }
                    });
                }else{
                    callback.LoginSuccsess(false);
                }

                }else{
                    DialogFragment dialogFragment= Erro.newInstance();
                    dialogFragment.setCancelable(false);
                    dialogFragment.show(((AppCompatActivity) ctx).getSupportFragmentManager(),"");
                }
            }
        });
    }
}
