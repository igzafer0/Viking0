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
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RestApi {
    //kayıt ol
    @POST("auth/register")
    Call<GetTokenModel>register(@Body AuthModel authModel);
    //Giriş yap
    @POST("auth/login")
    Call<GetTokenModel> login(@Body AuthModel authModel);
    //Blokları sayfa sırasına göre çekme
    @POST("blogs/getbypage")
    Call<List<BlogModel>>getBlogByPage(@Header("Authorization")String token, @Body GetBlogByPageModel getBlogByPageModel);

    //Profil bilgilerini çekme
    @GET("users/getMyProfil")
    Call<myDetailsModel>getMyDetails(@Header("Authorization")String token);
    //Profili güncelleme
    @POST("users/updateUser")
    Call<myDetailsModel>updateProfile(@Header("Authorization")String token, @Body myDetailsModel model);
    //Token geçerli mi diye kontrol etme
    @GET("tests/auth")
    Call<ResponseBody>getTokenIsValid(@Header("Authorization")String token);
    //İnternet ve server bağlantısı test etme
    @GET("tests/okComment")
    Call<ResponseBody>connTest();
    //Profil fotoğrafı değiştirme
    @Multipart
    @POST("users/updateUserPp")
    Call<ResponseBody> Upload(@Part MultipartBody.Part objectFile, @Header("Authorization")String token);
    //Bloğun yorumlarını çekme
    @GET("blogs/getBlogComment")
    Call<List<getCommentModel>>getComment(@Query("blogId") int id);
    //Yorum gönderme
    @POST("blogs/addBlogComment")
    Call<addCommentModel>addComment(@Header("Authorization")String token, @Body addCommentModel commentModel);
}
