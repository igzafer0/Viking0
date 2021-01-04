package com.igzafer.viking.api.Auth;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.gson.Gson;
import com.igzafer.viking.DialogFragment.InternetError;
import com.igzafer.viking.Interfaces.IMainResponse;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.LoginRegisterModels.AuthModel;
import com.igzafer.viking.Model.LoginRegisterModels.GetTokenModel;
import com.igzafer.viking.RestApi.ManagerAll;
import com.igzafer.viking.api.Test.ServerControl;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Auth {
    public void Login(Context ctx, AuthModel authModel, IMainResponse callback){
        Call<GetTokenModel> istek = ManagerAll.getInstance().login(authModel);
        istek.enqueue(new Callback<GetTokenModel>() {
            @Override
            public void onResponse(Call<GetTokenModel> call, Response<GetTokenModel> response) {
                if (response.isSuccessful()){
                    LocalDatabase.setEmailandPassword(ctx,authModel);
                    LocalDatabase.setToken(ctx,response.body());
                    callback.Succsess(response);
                }else{
                    ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
                    callback.Error(message);
                }

            }

            @Override
            public void onFailure(Call<GetTokenModel> call, Throwable t) {
                callback.Error(new ErrorModel(t.getMessage()));
            }
        });
    }
    //Kayıt oluyor
    public void Register(Context ctx, AuthModel authModel, IMainResponse callback){
        Call<GetTokenModel> istek = ManagerAll.getInstance().register(authModel);
        istek.enqueue(new Callback<GetTokenModel>() {
            @Override
            public void onResponse(Call<GetTokenModel> call, Response<GetTokenModel> response) {
                if (response.isSuccessful()){
                    LocalDatabase.setEmailandPassword(ctx,authModel);
                    LocalDatabase.setToken(ctx,response.body());
                    callback.Succsess(response);
                }else{
                    ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
                    callback.Error(message);
                }

            }

            @Override
            public void onFailure(Call<GetTokenModel> call, Throwable t) {
                callback.Error(new ErrorModel(t.getMessage()));
            }
        });
    }
    //Uygulamaya önceden giriş yapmışsan ve tekrar dönüyorsan burası çalışacak.
    //1 internet var mı diye kontrol ediyor
    //Database de login kaydı yapılmış mı diye kontrol ediyor
    //Token hala geçerli mi diye kontrol ediyor
    //Geçerliyse login oldu döndürüyor
    //Geçerli değilse Login ol diyor ve databsedeki login bilgilerini çekiyor
    //Başarılıysa true değilse false döndürüyor
    public void Control(Context ctx, IMainResponse callback){
        new ServerControl().Control(new IMainResponse() {
            @Override
            public <T> void Succsess(Response<T> _response) {
                if(LocalDatabase.isLogin(ctx)){
                    TokenIsValid.getTokenValid(ctx, valid -> {
                        if (valid) {
                            callback.Succsess(_response);
                        } else {
                            new Auth().Login(ctx, LocalDatabase.getLoginDetails(ctx), new IMainResponse() {
                                @Override
                                public <T> void Succsess(Response<T> _response) {
                                    callback.Succsess(_response);
                                }

                                @Override
                                public void Error(ErrorModel _eresponse) {
                                    callback.Error(_eresponse);
                                }

                            });
                        }
                    });
                }else{
                    callback.Error(null);
                }
            }
            @Override
            public void Error(ErrorModel _eresponse) {
                try {
                    DialogFragment dialogFragment= InternetError.newInstance();
                    dialogFragment.setCancelable(false);
                    dialogFragment.show(((AppCompatActivity) ctx).getSupportFragmentManager(),"");
                }catch (Exception e){

                }

            }
        });

    }
}
