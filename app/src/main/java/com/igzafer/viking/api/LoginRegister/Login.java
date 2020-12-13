package com.igzafer.viking.api.LoginRegister;

import android.content.Context;

import com.google.gson.Gson;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.LoginRegisterModels.GetTokenModel;
import com.igzafer.viking.Model.LoginRegisterModels.LoginModel;
import com.igzafer.viking.RestApi.ManagerAll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login {
    //Giriş yapıyor
    public static void login(Context ctx, LoginModel loginModel, LoginInterface callback){
        Call<GetTokenModel> istek = ManagerAll.getInstance().login(loginModel);
        istek.enqueue(new Callback<GetTokenModel>() {
            @Override
            public void onResponse(Call<GetTokenModel> call, Response<GetTokenModel> response) {
                if (response.isSuccessful()){
                    LocalDatabase.setEmailandPassword(ctx,loginModel);
                    LocalDatabase.setToken(ctx,response.body());
                    callback.LoginResponse(true,null);
                }else{
                    ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
                    callback.LoginResponse(false,message);
                }

            }

            @Override
            public void onFailure(Call<GetTokenModel> call, Throwable t) {
                callback.LoginResponse(false,new ErrorModel("Hata",t.getMessage()));
            }
        });
    }
}
