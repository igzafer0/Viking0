package com.igzafer.viking.api.LoginRegister;

import android.content.Context;

import com.google.gson.Gson;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.LoginRegisterModels.GetTokenModel;
import com.igzafer.viking.Model.LoginRegisterModels.LoginModel;
import com.igzafer.viking.Model.LoginRegisterModels.RegisterModel;
import com.igzafer.viking.RestApi.ManagerAll;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register {
    //Kayıt oluyor
    public static void register(Context ctx, RegisterModel registerModel, RegisterInterface callback){
        Call<GetTokenModel> istek = ManagerAll.getInstance().register(registerModel);
        istek.enqueue(new Callback<GetTokenModel>() {
            @Override
            public void onResponse(Call<GetTokenModel> call, Response<GetTokenModel> response) {
                if (response.isSuccessful()){
                    LocalDatabase.setEmailandPassword(ctx,new LoginModel(registerModel.getEmail(),registerModel.getNickName()));
                    LocalDatabase.setToken(ctx,response.body());
                    callback.registerResponse(true,null);
                }else{
                    ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
                    callback.registerResponse(false,message);
                }

            }

            @Override
            public void onFailure(Call<GetTokenModel> call, Throwable t) {
                callback.registerResponse(false,new ErrorModel("Hata",t.getMessage()));
            }
        });
    }
}
