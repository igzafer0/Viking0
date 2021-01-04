package com.igzafer.viking.RestApi;

import com.igzafer.viking.Model.BlogModels.BlogModel;
import com.igzafer.viking.Model.BlogModels.GetBlogByPageModel;
import com.igzafer.viking.Model.CommentModels.addCommentModel;
import com.igzafer.viking.Model.CommentModels.getCommentModel;
import com.igzafer.viking.Model.LoginRegisterModels.AuthModel;
import com.igzafer.viking.Model.LoginRegisterModels.GetTokenModel;
import com.igzafer.viking.Model.UserDetailModels.myDetailsModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ManagerAll extends BaseManager {
    private static ManagerAll ourInstance = new ManagerAll();
    public static synchronized ManagerAll getInstance(){
        return ourInstance;
    }


    public Call<GetTokenModel> register(AuthModel authModel){
        Call<GetTokenModel>call=getRestApiClient().register(authModel);
        return call;
    }
    public Call<GetTokenModel> login(AuthModel authModel){
        Call<GetTokenModel>call=getRestApiClient().login(authModel);
        return call;
    }
    public Call<List<BlogModel>> getBlogByPage(String key ,GetBlogByPageModel model){
        Call<List<BlogModel>>call=getRestApiClient().getBlogByPage("Bearer "+key, model);
        return call;
    }

    public Call<myDetailsModel> getMyDetails(String key){
        Call<myDetailsModel>call=getRestApiClient().getMyDetails("Bearer "+key);
        return call;
    }
    public Call<ResponseBody> tokenIsValid(String key){
        Call<ResponseBody>call=getRestApiClient().getTokenIsValid("Bearer "+key);
        return call;
    }
    public Call<myDetailsModel> updateProfile(String key, myDetailsModel model){
        Call<myDetailsModel>call=getRestApiClient().updateProfile("Bearer "+key,model);
        return call;
    }
    public Call<ResponseBody> updatePP(MultipartBody.Part photo,String key){
        Call<ResponseBody>call=getRestApiClient().Upload(photo,"Bearer "+key);
        return call;
    }
    public Call<ResponseBody> connTest(){
        Call<ResponseBody>call=getRestApiClient().connTest();
        return call;
    }
    public Call<List<getCommentModel>> getComment(int blog_id){
        Call<List<getCommentModel>>call=getRestApiClient().getComment(blog_id);
        return call;
    }
    public Call<addCommentModel> addComment(String token,addCommentModel commentModel){
        Call<addCommentModel>call=getRestApiClient().addComment("Bearer "+token,commentModel);
        return call;
    }





}
