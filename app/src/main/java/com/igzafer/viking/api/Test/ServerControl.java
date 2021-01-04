package com.igzafer.viking.api.Test;

import com.google.gson.Gson;
import com.igzafer.viking.Interfaces.IMainResponse;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.RestApi.ManagerAll;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServerControl {
    //Server down olmuş mu diye kontrol ediyor
    public void Control(IMainResponse callback){
        Call<ResponseBody> istek= ManagerAll.getInstance().connTest();
        istek.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    callback.Succsess(response);
                }else{
                    ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
                    callback.Error(message);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.Error(new ErrorModel(t.getMessage()));
            }
        });
    }
    public interface conTest{
        //bağlı mı değilmi ona göre bir boolean dönüş
        void Connected(Boolean connected);
    }
}
