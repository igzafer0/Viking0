package com.igzafer.viking.api.AuthGerektiren;

import android.content.Context;

import com.google.gson.Gson;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.UserDetailModels.myDetailsModel;
import com.igzafer.viking.RestApi.ManagerAll;
import com.igzafer.viking.api.LoginRegister.TokenControl;
import com.igzafer.viking.api.LoginRegister.TokenControlInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateMyDetails {
    //User datails update
  public static void update(Context ctx,myDetailsModel myDetailsModel, UpdateMyDetailsInterface callback){
      TokenControl.LoginControl(ctx, new TokenControlInterface() {
          @Override
          public void LoginSuccsess(Boolean success) {
              if(success){
                  Call<myDetailsModel> istek = ManagerAll.getInstance().updateProfile(LocalDatabase.getToken(ctx),myDetailsModel);
                  istek.enqueue(new Callback<com.igzafer.viking.Model.UserDetailModels.myDetailsModel>() {
                      @Override
                      public void onResponse(Call<myDetailsModel> call, Response<myDetailsModel> response) {
                          if(response.isSuccessful()){

                              LocalDatabase.setUserDetails(ctx,myDetailsModel);
                              callback.result(true,null);
                          }else{
                              ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
                              callback.result(false,message);
                          }
                      }

                      @Override
                      public void onFailure(Call<myDetailsModel> call, Throwable t) {
                          callback.result(false,new ErrorModel("Hata",t.getMessage()));
                      }
                  });
              }else{
                  callback.result(false,new ErrorModel("Hata","Bilinmeyen bir hata oluştu"));
              }
          }
      });




  }
}
