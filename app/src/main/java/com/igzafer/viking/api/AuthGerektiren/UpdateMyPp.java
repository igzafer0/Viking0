package com.igzafer.viking.api.AuthGerektiren;

import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.RestApi.ManagerAll;
import com.igzafer.viking.api.LoginRegister.TokenControl;
import com.igzafer.viking.api.LoginRegister.TokenControlInterface;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateMyPp {
    //profil fotoğrafını değiştirmeye yarıyor
    public void update(Context ctx, Uri pp, UpdateMyDetailsInterface callback){
        TokenControl.LoginControl(ctx, new TokenControlInterface() {
            @Override
            public void LoginSuccsess(Boolean success) {
                if(success){
                    File file = new File(pp.getPath());
                    RequestBody photoContent=RequestBody.create(MediaType.parse("multipart/form-data"),file);
                    MultipartBody.Part photo = MultipartBody.Part.createFormData("objectFile",file.getName(),photoContent);
                    Call<ResponseBody> response= ManagerAll.getInstance().updatePP(photo, LocalDatabase.getToken(ctx));
                    response.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()){
                                callback.result(true,null);
                            }else{
                                ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
                                callback.result(false,message);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            callback.result(false,new ErrorModel("Hata",t.getMessage()));
                        }
                    });
//

                }else{
                    callback.result(false,new ErrorModel("Hata","Bilinmeyen bir hata oluştu."));

                }
            }
        });
    }
}
