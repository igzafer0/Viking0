package com.igzafer.viking.api.AuthGerektiren;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.igzafer.viking.Interfaces.IMainResponse;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.CommentModels.addCommentModel;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.RestApi.ManagerAll;
import com.igzafer.viking.api.Auth.Auth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Comment {
    public void sendComment(Context ctx, addCommentModel commentModel, IMainResponse callback){
        new Auth().Control(ctx, new IMainResponse() {
            @Override
            public <T> void Succsess(Response<T> _response) {
                Call<addCommentModel> istek = ManagerAll.getInstance().addComment(LocalDatabase.getToken(ctx),commentModel);
                istek.enqueue(new Callback<addCommentModel>() {
                    @Override
                    public void onResponse(Call<addCommentModel> call, Response<addCommentModel> response) {
                        if(response.isSuccessful()){
                            callback.Succsess(response);

                        }else{
                            Log.d("winter",new Gson().toJson(response));
                            ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
                            callback.Error(message);

                        }
                    }

                    @Override
                    public void onFailure(Call<addCommentModel> call, Throwable t) {
                        Log.d("winter",t.getMessage());
                        callback.Error(new ErrorModel(t.getMessage()));
                    }
                });
            }

            @Override
            public void Error(ErrorModel _eresponse) {
                callback.Error(new ErrorModel(null));
            }

        });
    }

}
