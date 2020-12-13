package com.igzafer.viking.api.LoginRegister;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.igzafer.viking.DialogFragment.InternetError;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.api.Test.ConnectionTest;

public class TokenControl {
    //Uygulamaya önceden giriş yapmışsan ve tekrar dönüyorsan burası çalışacak.
    //1 internet var mı diye kontrol ediyor
    //Database de login kaydı yapılmış mı diye kontrol ediyor
    //Token hala geçerli mi diye kontrol ediyor
    //Geçerliyse login oldu döndürüyor
    //Geçerli değilse Login ol diyor ve databsedeki login bilgilerini çekiyor
    //Başarılıysa true değilse false döndürüyor
    public static void LoginControl(Context ctx, TokenControlInterface callback){
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
                    DialogFragment dialogFragment= InternetError.newInstance();
                    dialogFragment.setCancelable(false);
                    dialogFragment.show(((AppCompatActivity) ctx).getSupportFragmentManager(),"");
                }
            }
        });
    }
}
