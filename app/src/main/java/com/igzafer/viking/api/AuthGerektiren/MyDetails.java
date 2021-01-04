package com.igzafer.viking.api.AuthGerektiren;

import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.igzafer.viking.Interfaces.IMainResponse;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.UserDetailModels.myDetailsModel;
import com.igzafer.viking.RestApi.ManagerAll;
import com.igzafer.viking.api.Auth.Auth;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDetails {
    // Profil bilgilerini çekip localime kaydediyor
    public void getDetails(Context ctx, IMainResponse callback){
        new Auth().Control(ctx, new IMainResponse() {
            @Override
            public <T> void Succsess(Response<T> _response) {
                Call<myDetailsModel> istek= ManagerAll.getInstance().getMyDetails(LocalDatabase.getToken(ctx));
                istek.enqueue(new Callback<myDetailsModel>() {
                    @Override
                    public void onResponse(Call<myDetailsModel> call, Response<myDetailsModel> response) {
                        if(response.isSuccessful()){
                            callback.Succsess(response);
                            LocalDatabase.setUserDetails(ctx,response.body());
                        }
                        else{
                            ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
                            callback.Error(message);
                        }
                    }
                    @Override
                    public void onFailure(Call<myDetailsModel> call, Throwable t) {
                        callback.Error(new ErrorModel(t.getMessage()));
                    }
                });
            }

            @Override
            public void Error(ErrorModel _eresponse) {
                callback.Error(new ErrorModel("Beklenmeyen bir hata oluştu, mühendislerimize haber verdik.\n MyDetails"));
            }

        });
    }
    // Profil bilgilerini güncelleyip localime kaydediyor
    public void updateDetails(Context ctx,myDetailsModel myDetailsModel, IMainResponse callback){
        new Auth().Control(ctx, new IMainResponse() {
            @Override
            public <T> void Succsess(Response<T> _response) {
                Call<myDetailsModel> istek = ManagerAll.getInstance().updateProfile(LocalDatabase.getToken(ctx),myDetailsModel);
                istek.enqueue(new Callback<com.igzafer.viking.Model.UserDetailModels.myDetailsModel>() {
                    @Override
                    public void onResponse(Call<myDetailsModel> call, Response<myDetailsModel> response) {
                        if(response.isSuccessful()){
                            LocalDatabase.setUserDetails(ctx,myDetailsModel);
                            callback.Succsess(response);
                        }else{
                            try {
                                ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
                                callback.Error(message);
                            }catch (Exception e){
                                callback.Error(new ErrorModel("Profil bilgileri güncellenemedi."));
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<myDetailsModel> call, Throwable t) {
                        callback.Error(new ErrorModel(t.getMessage()));
                    }
                });
            }

            @Override
            public void Error(ErrorModel _eresponse) {
                callback.Error(new ErrorModel("Bilinmeyen bir hata oluştu"));
            }


        });
    }
    // Profil fotoğrafını güncelleyip localime kaydediyor
    public void changeProfilePicture(Context ctx, Uri pp, IMainResponse callback){
        new Auth().Control(ctx, new IMainResponse() {
            @Override
            public <T> void Succsess(Response<T> _response) {
                File file = new File(pp.getPath());
                RequestBody photoContent=RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part photo = MultipartBody.Part.createFormData("objectFile",file.getName(),photoContent);
                Call<ResponseBody> response= ManagerAll.getInstance().updatePP(photo, LocalDatabase.getToken(ctx));
                response.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            callback.Succsess(response);
                        }else{
                            try{
                                ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
                                callback.Error(message);
                            }catch (Exception e){
                                callback.Error(new ErrorModel(e.getMessage()));
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        callback.Error(new ErrorModel(t.getMessage()));
                    }
                });
//

            }

            @Override
            public void Error(ErrorModel _eresponse) {
                callback.Error(new ErrorModel("Bilinmeyen bir hata oluştu."));
            }

        });
    }


}
