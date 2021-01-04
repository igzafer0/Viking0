package com.igzafer.viking.api.AuthGerektiren;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.igzafer.viking.Interfaces.IMainResponse;
import com.igzafer.viking.LocalDatabase.HomeStaticDb;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.BlogModels.BlogModel;
import com.igzafer.viking.Model.BlogModels.GetBlogByPageModel;
import com.igzafer.viking.Model.BlogModels.PageModel;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.RestApi.ManagerAll;
import com.igzafer.viking.api.Auth.Auth;
import com.igzafer.viking.api.Test.ServerControl;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Blogs {
    public static int pageNumber=1;
    public static int maxPage=2;
    public static boolean kilit=true;
    public void get(Context ctx, IMainResponse callback){
      new Auth().Control(ctx, new IMainResponse() {
          @Override
          public <T> void Succsess(Response<T> _response) {
              if(kilit){
                  new ServerControl().Control(new IMainResponse() {
                      @Override
                      public <T> void Succsess(Response<T> _response) {
                          Call<List<BlogModel>> istek = ManagerAll.getInstance().getBlogByPage(LocalDatabase.getToken(ctx),new GetBlogByPageModel(pageNumber,50));
                          istek.enqueue(new Callback<List<BlogModel>>() {
                              @Override
                              public void onResponse(Call<List<BlogModel>> call, Response<List<BlogModel>> response) {
                                  if(response.isSuccessful()){
                                      HomeStaticDb.blogModel.addAll(response.body());
                                      HomeStaticDb.homeFirst=false;
                                      String head=response.headers().get("X-Pagination");
                                      Gson g = new Gson();
                                      PageModel p = g.fromJson(head, PageModel.class);
                                      pageNumber=p.getCurrentPage();
                                      maxPage=p.getTotalPages();
                                      Log.d("winter",p.getTotalPages()+""+p.getCurrentPage());
                                      kilit=true;
                                      callback.Succsess(response);
                                  }else{
                                      kilit=true;
                                      ErrorModel message = new Gson().fromJson(response.errorBody().charStream(), ErrorModel.class);
                                      callback.Error(message);
                                  }
                              }

                              @Override
                              public void onFailure(Call<List<BlogModel>> call, Throwable t) {
                                  kilit=true;
                                  callback.Error(new ErrorModel(t.getMessage()));
                              }
                          });
                      }

                      @Override
                      public void Error(ErrorModel _eresponse) {
                          kilit=true;
                          callback.Error(new ErrorModel("500"));
                      }
                  });
              }
          }

          @Override
          public void Error(ErrorModel _eresponse) {
              callback.Error(_eresponse);
          }
      });
    }
}
