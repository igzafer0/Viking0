package com.igzafer.viking.api.AuthGerektirmeyen;

import android.content.Context;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dinuscxj.refresh.RecyclerRefreshLayout;
import com.google.gson.Gson;
import com.igzafer.viking.Adapter.RecylerAdapter;
import com.igzafer.viking.DialogFragment.InternetError;
import com.igzafer.viking.Model.BlogModels.BlogModel;
import com.igzafer.viking.Model.BlogModels.GetBlogByPageModel;
import com.igzafer.viking.Model.BlogModels.PageModel;
import com.igzafer.viking.RestApi.ManagerAll;
import com.igzafer.viking.TasarimsalDuzenlemeler.Dialog;
import com.igzafer.viking.LocalDatabase.HomeStaticDb;
import com.igzafer.viking.api.Test.ConnectionTest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetBlog{
    public static int pageNumber=1;
    // ilk çekeceği sayfa 1. sayfa olduğu için 1 verdim
    static GetBlogByPageModel model;
    // sayfa ve sayfada kaç blog olduğunu apiye vereceğim model
    public static int maxPage=2;
    // Api kaç tane sayfa olduğunu söyleyecek ve bu veri değişecek
    // RecyclerView'e vereceğim model
    static RecylerAdapter adapter;
    public static List<BlogModel>blogModels=new ArrayList<>();
    // RecyclerView adapter'ı
    static boolean kilit=true;
    public static void getBlog(RecyclerView recy, Context ctx, Window window, Fragment fragment, RecyclerRefreshLayout refreshLayout){
        model=new GetBlogByPageModel(pageNumber,50);
        //get modelini oluşturdum
        if(kilit){
            kilit=false;
            //recycler view'de en aşağı inip sayfa değiştirme komutu verilince
            //tekrar yukarı çıkıp aşağı inerse tekrar komut vermesin diye koydum.
            ConnectionTest.iConnect(new ConnectionTest.conTest() {
                @Override
                public void Connected(Boolean connected) {
                    //internete bağlı mı değil mi diye bir kontrol
                    if(connected){
                        //internete bağlıysa bir bağlantı oluşturuyor
                        Call<List<BlogModel>> istek = ManagerAll.getInstance().getBlogByPage(model);
                        istek.enqueue(new Callback<List<BlogModel>>() {
                            @Override
                            public void onResponse(Call<List<BlogModel>> call, Response<List<BlogModel>> response) {
                                if(response.isSuccessful()){
                                    blogModels.addAll(response.body());
                                    HomeStaticDb.loadBlog(blogModels);
                                    //response'u blog modelime attım
                                    String head=response.headers().get("X-Pagination");
                                    Gson g = new Gson();
                                    PageModel p = g.fromJson(head, PageModel.class);
                                    pageNumber=p.getCurrentPage();
                                    maxPage=p.getTotalPages();

                                    //Headerdan Pagination'ı aldım ki hangi sayfada olduğumu ve maxPage'i ayarlayabileyim
                                    //Blog modelimi static veri tabanıma kaydettim.
                                    adapter= new RecylerAdapter(ctx,blogModels,window);
                                    recy.setAdapter(adapter);
                                    kilit=true;
                                    refreshLayout.setRefreshing(false);
                                }
                                else{
                                    kilit=true;
                                    Dialog.createDialog(window,"Hata","Bilinmeyen bir hata oluştu hata kodu; "+response.code(),0);
                                    //200 ok dönmeyen her türlü hatayı burada yazdırıyorum
                                }

                            }

                            @Override
                            public void onFailure(Call<List<BlogModel>> call, Throwable t) {
                                kilit=true;
                                Dialog.createDialog(window,"Hata","Bilinmeyen bir hata oluştu hata;  "+t.getMessage(),0);
                                //farklı bir hata alırsam burada yazdırıyorum
                            }
                        });

                    }else{
                        kilit=true;
                        //internete bağlı değilse dialog fragment çıkartıyor
                        DialogFragment dialogFragment= InternetError.newInstance();
                        dialogFragment.setTargetFragment(fragment, 1);
                        dialogFragment.setCancelable(false);
                        dialogFragment.show(((AppCompatActivity) ctx).getSupportFragmentManager(),"");
                    }
                }
            });
        }

    }
}
