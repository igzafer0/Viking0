package com.igzafer.viking.api.Auth;

import android.content.Context;

import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.RestApi.ManagerAll;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenIsValid{
    // Token hala geçerli mi diye kontrol ediyor
    public static void getTokenValid(Context ctx,TokenIsValidInterface callback){
        Call<ResponseBody> istek= ManagerAll.getInstance().tokenIsValid(LocalDatabase.getToken(ctx));
        istek.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful())
                {
                    callback.isValid(true);
                }
                else {
                    callback.isValid(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.isValid(false);
            }
        });

    }
}
