package com.igzafer.viking.api.Test;

import com.igzafer.viking.RestApi.ManagerAll;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnectionTest {
    //internete bağlı mı diye kontrol ediyor return ü true ve false
    public static void iConnect(conTest callback){
        Call<ResponseBody> istek= ManagerAll.getInstance().connTest();
        istek.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    callback.Connected(true);
                }else{
                    callback.Connected(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.Connected(false);
            }
        });
    }
    public interface conTest{
        //bağlı mı değilmi ona göre bir boolean dönüş
        void Connected(Boolean connected);
    }
}
