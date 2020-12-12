package com.igzafer.viking.amaleler;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.igzafer.viking.Model.CommentModels.getCommentModel;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.Model.UserDetailModels.myDetailsModel;

import java.util.List;

public class apiAuth {
    // auth booleanı şöyle çalışıyor;
    // auth ile gerçekleşmesi gereken bir işlem; örn verilerimi çek
    // getuser mototu activityden tetikleniyor o aut metotuna gidiyor benim keyim hala geçerli mi diye soruyor
    // auth ona geçerliyse true değilse false döndürüyor
    // getuser metotu authtan true alırsa normalce işlerini yapıyor
    // eğer false ise vikingapideki login metotuna localdeki kadi şifreyi verip login ediyo
    //   static boolean Auth(Context ctx,Auth_res callback){
//        Call<ResponseBody> istek= ManagerAll.getInstance().getAuth(SharedPr.getToken(ctx));
//        istek.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.isSuccessful()){
//                    //Log.d("winter","basarılı");
//                    callback.ret(true);
//                }
//                else if(response.code()==401){
//                    //Log.d("winter","basarisiz");
//                    callback.ret(false);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                callback.ret(false);
//            }
//        });
//     return false;
  //  }
    public interface Auth_res{
        void ret(Boolean donus);

    }
    public interface getUser_res{
        void donus_Succ(myDetailsModel model);
        void donus_Err(ErrorModel model);
    }
    public interface setUser_res{
        void donus_Succ(myDetailsModel model);
        void donus_Err(ErrorModel model);
    }
    public interface updateUser{
        void donus(Boolean donus);
    }
    public interface getComment{
        void donus_Succ(List<getCommentModel> model);
        void donus_Err(ErrorModel model);
    }
    static SharedPreferences preferences;
    static SharedPreferences.Editor editor;
    public static void updateUser(final Activity a, Context ctx, setUser_res call, myDetailsModel model){
//        apiAuth.Auth(ctx, new Auth_res() {
//            @Override
//            public void ret(Boolean donus) {
//                if(donus){
//                VikingApi.updateMyDetails(new VikingApi.myDetails() {
//                    @Override
//                    public void details_Succ(UserDetailsModel model) {
//                        call.donus_Succ(model);
//                    }
//
//                    @Override
//                    public void details_Err(ErrorModel errorModel) {
//                        call.donus_Err(errorModel);
//                    }
//                },ctx,model);
//                }else{
//                    VikingApi.login(new VikingApi.Login() {
//                        @Override
//                        public void Log_Succ(LoginModel loginModel) {
//                            VikingApi.updateMyDetails(new VikingApi.myDetails() {
//                                @Override
//                                public void details_Succ(UserDetailsModel model) {
//                                    call.donus_Succ(model);
//                                }
//
//                                @Override
//                                public void details_Err(ErrorModel errorModel) {
//                                    call.donus_Err(errorModel);
//                                }
//                            },ctx,model);
//                        }
//
//                        @Override
//                        public void Log_Error(ErrorModel errorModel) {
//                            SharedPr.setLoginFalse(ctx);
//                            Intent i = new Intent(ctx, MainActivity.class);
//                            a.startActivity(i);
//                            a.finish();
//                        }
//                    },new LoginModel(preferences.getString("kadi",""),preferences.getString("pass",""),"",""),ctx,a,true);
//
//                }
//            }
//        });
    }
    public static void getUser(final Activity a, Context ctx,getUser_res call){
//        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
//
//        apiAuth.Auth(ctx, new Auth_res() {
//            @Override
//            public void ret(Boolean donus) {
//                if(donus){
//                    VikingApi.getMyDetails(new VikingApi.myDetails() {
//                        @Override
//                        public void details_Succ(UserDetailsModel model) {
//                            Log.d("winter",model.getAvatar());
//                            call.donus_Succ(model);
//                        }
//
//                        @Override
//                        public void details_Err(ErrorModel errorModel) {
//                            call.donus_Err(errorModel);
//                        }
//                    },ctx);
//                }else{
//                    VikingApi.login(new VikingApi.Login() {
//                        @Override
//                        public void Log_Succ(LoginModel loginModel) {
//                            VikingApi.getMyDetails(new VikingApi.myDetails() {
//                                @Override
//                                public void details_Succ(UserDetailsModel model) {
//                                    call.donus_Succ(model);
//                                }
//
//                                @Override
//                                public void details_Err(ErrorModel errorModel) {
//                                    call.donus_Err(errorModel);
//                                }
//                            },ctx);
//                        }
//
//                        @Override
//                        public void Log_Error(ErrorModel errorModel) {
//                            SharedPr.setLoginFalse(ctx);
//                            Intent i = new Intent(ctx, MainActivity.class);
//                            a.startActivity(i);
//                            a.finish();
//                        }
//                    },new LoginModel(preferences.getString("kadi",""),preferences.getString("pass",""),"",""),ctx,a,true);
//
//                }
//            }
//        });
    }
    public static void updatePP(final Activity a, Context ctx, Uri uri, final updateUser cal){
//        apiAuth.Auth(ctx, new Auth_res() {
//            @Override
//            public void ret(Boolean donus) {
//                if(donus){
//                    File file = new File(uri.getPath());
//                    RequestBody photoContent=RequestBody.create(MediaType.parse("multipart/form-data"),file);
//                    MultipartBody.Part photo = MultipartBody.Part.createFormData("objectFile",file.getName(),photoContent);
//                    Call<ResponseBody> response= ManagerAll.getInstance().updatePP(photo, SharedPr.getToken(ctx));
//                    response.enqueue(new retrofit2.Callback<ResponseBody>() {
//                        @Override
//                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                            Log.d("winter",SharedPr.getToken(ctx));
//                            Log.d("winter",String.valueOf(response.code()));
//                            cal.donus(true);
//                        }
//
//                        @Override
//                        public void onFailure(Call<ResponseBody> call, Throwable t) {
//                            Log.d("winter",t.getMessage());
//                            cal.donus(false);
//                        }
//                    });
//
//
//                }else {
//                    VikingApi.login(new VikingApi.Login() {
//                        @Override
//                        public void Log_Succ(LoginModel loginModel) {
//                            File file = new File(uri.getPath());
//                            RequestBody photoContent=RequestBody.create(MediaType.parse("multipart/form-data"),file);
//                            MultipartBody.Part photo = MultipartBody.Part.createFormData("objectFile",file.getName(),photoContent);
//                            Call<ResponseBody> response= ManagerAll.getInstance().updatePP(photo, SharedPr.getToken(ctx));
//                            response.enqueue(new retrofit2.Callback<ResponseBody>() {
//                                @Override
//                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                                    Log.d("winter",SharedPr.getToken(ctx));
//                                    Log.d("winter",String.valueOf(response.code()));
//                                    cal.donus(true);
//                                }
//
//                                @Override
//                                public void onFailure(Call<ResponseBody> call, Throwable t) {
//                                    Log.d("winter",t.getMessage());
//                                    cal.donus(false);
//                                }
//                            });
//
//
//                        }
//
//                        @Override
//                        public void Log_Error(ErrorModel errorModel) {
//
//                        }
//                    }, new LoginModel(preferences.getString("kadi", ""), preferences.getString("pass", ""), "", ""), ctx, a, true);
//
//                }
//            }
//        });
    }
    public static void getComment(Context ctx,Activity a,getComment call,int id){
//        preferences = PreferenceManager.getDefaultSharedPreferences(ctx);
//        apiAuth.Auth(ctx, new Auth_res() {
//            @Override
//            public void ret(Boolean donus) {
//                if(donus){
//                  VikingApi.commentGet(new VikingApi.getComment() {
//                      @Override
//                      public void Log_Succ(List<CommentModel> commentModel) {
//                          call.donus_Succ(commentModel);
//                      }
//
//                      @Override
//                      public void Log_Error(ErrorModel errorModel) {
//                          call.donus_Err(errorModel);
//                      }
//                  },ctx,id);
//                }else{
//                    VikingApi.login(new VikingApi.Login() {
//                        @Override
//                        public void Log_Succ(LoginModel loginModel) {
//                            VikingApi.commentGet(new VikingApi.getComment() {
//                                @Override
//                                public void Log_Succ(List<CommentModel> commentModel) {
//                                    call.donus_Succ(commentModel);
//                                }
//
//                                @Override
//                                public void Log_Error(ErrorModel errorModel) {
//                                    call.donus_Err(errorModel);
//                                }
//                            },ctx,id);
//                        }
//
//                        @Override
//                        public void Log_Error(ErrorModel errorModel) {
//                            SharedPr.setLoginFalse(ctx);
//                            Intent i = new Intent(ctx, MainActivity.class);
//                            a.startActivity(i);
//                            a.finish();
//                        }
//                    },new LoginModel(preferences.getString("kadi",""),preferences.getString("pass",""),"",""),ctx,a,true);
//
//                }
//            }
//        });
    }
}
