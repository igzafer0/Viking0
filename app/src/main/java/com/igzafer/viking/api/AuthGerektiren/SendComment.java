package com.igzafer.viking.api.AuthGerektiren;

import android.content.Context;
import android.view.Window;

import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.CommentModels.addCommentModel;
import com.igzafer.viking.RestApi.ManagerAll;
import com.igzafer.viking.amaleler.Dialog;
import com.igzafer.viking.api.LoginRegister.TokenControl;
import com.igzafer.viking.api.LoginRegister.ControlInterface;
import com.igzafer.viking.api.Test.ConnectionTest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendComment{
    public void add(Context ctx, addCommentModel commentModel, Window window, SendCommentInterface callback){
        TokenControl.LoginControl(ctx, new ControlInterface() {
            @Override
            public void LoginSuccsess(Boolean success) {
                if(success){
                    Call<addCommentModel> istek = ManagerAll.getInstance().addComment(LocalDatabase.getToken(ctx),commentModel);
                    istek.enqueue(new Callback<addCommentModel>() {
                        @Override
                        public void onResponse(Call<addCommentModel> call, Response<addCommentModel> response) {
                            if(response.isSuccessful()){
                                callback.response(true);

                            }else{
                                Dialog.createDialog(window,"Hata","Bir hata oluştu, hata kodu; "+response.code(),0);
                                callback.response(false);
                            }
                        }

                        @Override
                        public void onFailure(Call<addCommentModel> call, Throwable t) {
                            callback.response(true);
                            //ilginç bir hata verdiği için bir süreliğine bunu iptal ediyoruz ve succsess true döndürüyoruz.
                           // Dialog.createDialog(window,"Hata",t.getMessage());
                        }
                    });
                }else{
                    callback.response(false);
                    Dialog.createDialog(window,"Hata","Giriş yapmalısın",0);
                }

            }
        });
    }
}
