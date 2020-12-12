package com.igzafer.viking.api.AuthGerektiren;

import android.content.Context;

import com.google.gson.Gson;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.UserDetailModels.myDetailsModel;
import com.igzafer.viking.RestApi.ManagerAll;
import com.igzafer.viking.api.LoginRegister.TokenControl;
import com.igzafer.viking.api.LoginRegister.ControlInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class getMyDetails {
    public static void get(Context ctx,getMyDetailsInterface callback){
        TokenControl.LoginControl(ctx, new ControlInterface() {
            @Override
            public void LoginSuccsess(Boolean success) {
                if(success){
                    Call<myDetailsModel> istek= ManagerAll.getInstance().getMyDetails(LocalDatabase.getToken(ctx));
                    istek.enqueue(new Callback<myDetailsModel>() {
                        @Override
                        public void onResponse(Call<myDetailsModel> call, Response<myDetailsModel> response) {
                            if(response.isSuccessful()){
                                callback.result(true,response.body(),null);
                                LocalDatabase.setUserDetails(ctx,response.body());
                            }
                            else{
                                ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
                                callback.result(false,null,message);
                            }
                        }
                        @Override
                        public void onFailure(Call<myDetailsModel> call, Throwable t) {
                            callback.result(false,null,new ErrorModel("Hata",t.getMessage()));
                        }
                    });
                }else{
                    callback.result(false,null,new ErrorModel("Hata","Beklenmeyen bir hata oluştu"));
                }
            }
        });
    }
}
