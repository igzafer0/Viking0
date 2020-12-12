package com.igzafer.viking.amaleler;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.igzafer.viking.Model.BlogModels.BlogModel;
import com.igzafer.viking.Model.BlogModels.GetBlogByPageModel;
import com.igzafer.viking.Model.CommentModels.getCommentModel;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.LoginRegisterModels.LoginModel;
import com.igzafer.viking.Model.UserDetailModels.myDetailsModel;
import com.igzafer.viking.RestApi.BaseManager;
import com.igzafer.viking.RestApi.ManagerAll;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VikingApi extends BaseManager {
    static SharedPreferences preferences;
    static SharedPreferences.Editor editor;
    static List<BlogModel>blogModels;
    static getCommentModel cm;
    static Boolean kilit=true;
    public static int pageNumber=1;
    public static int maxPage=2;
    static GetBlogByPageModel blogModel;

//    public static void getMyDetails(final myDetails callback,Context ctx){
//        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
//        editor = preferences.edit();
//        Call<myDetailsModel> istek = ManagerAll.getInstance().getUser(preferences.getString("key",""));
//        istek.enqueue(new Callback<myDetailsModel>() {
//            @Override
//            public void onResponse(Call<myDetailsModel> call, Response<myDetailsModel> response) {
//                if(response.isSuccessful()){
//                    myDetailsModel myDetailsModel =response.body();
//                   // Log.d("winter",userDetailsModel.getNick()+response.code());
//                    callback.details_Succ(myDetailsModel);
//                }
//                else if(response.code()==401){
//                    callback.details_Err(new ErrorModel("401","Login"));
//                   // Log.d("winter","401");
//                }else {
//                    ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
//                    callback.details_Err(message);
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<myDetailsModel> call, Throwable t) {
//
//            }
//        });
//    }
    public static void updateMyDetails(final myDetails callback, Context ctx, myDetailsModel model){
        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
        editor = preferences.edit();
        Call<myDetailsModel> istek = ManagerAll.getInstance().updateProfile(preferences.getString("key",""),model);
        istek.enqueue(new Callback<myDetailsModel>() {
            @Override
            public void onResponse(Call<myDetailsModel> call, Response<myDetailsModel> response) {
                if(response.isSuccessful()){
                    myDetailsModel myDetailsModel =response.body();
                   // Log.d("winter",userDetailsModel.getNick()+response.code());
                    callback.details_Succ(myDetailsModel);
                }
                else if(response.code()==401){
                    callback.details_Err(new ErrorModel("401","Login"));
                   // Log.d("winter","401");
                }else {
                    ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
                    callback.details_Err(message);
                }

            }

            @Override
            public void onFailure(Call<myDetailsModel> call, Throwable t) {

            }
        });
    }



    public interface Register{
        void Reg_Error(ErrorModel errorModel);
    }
    public interface conTest{
        void donus(Boolean con);
    }
    public interface getComment{
        void Log_Succ(List<getCommentModel> getCommentModel);
        void Log_Error(ErrorModel errorModel);
    }
    public interface Login{
        void Log_Succ(LoginModel loginModel);
        void Log_Error(ErrorModel errorModel);
    }
    public interface myDetails{
        void details_Succ(myDetailsModel model);
        void details_Err(ErrorModel errorModel);
    }
    public interface i_GetBlog{
        void blog_Succ(List<BlogModel> blogModel2);
        void blog_Err(ErrorModel errorModel);
    }
}
