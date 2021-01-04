package com.igzafer.viking.api.AuthGerektirmeyen;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.igzafer.viking.Adapter.getCommentAdapter;
import com.igzafer.viking.LocalDatabase.CommentStaticDb;
import com.igzafer.viking.Model.CommentModels.getCommentModel;
import com.igzafer.viking.RestApi.ManagerAll;
import com.igzafer.viking.TasarimsalDuzenlemeler.Dialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetComment {
    getCommentAdapter adapter;
    //Yorumları çekip otomatik adaptera ve recye atıyor. Edit menüsü olan relative layout da burada veriliyor.
    public void get(Context ctx, int BlogId, Window window, RecyclerView recy, NestedScrollView scrollView, SpinKitView spinner){
        Call<List<getCommentModel>> istek= ManagerAll.getInstance().getComment(BlogId);
        istek.enqueue(new Callback<List<getCommentModel>>() {
            @Override
            public void onResponse(Call<List<getCommentModel>> call, Response<List<getCommentModel>> response) {
                if(response.isSuccessful()){

                   // rl.setVisibility(View.GONE);
                    scrollView.setVisibility(View.INVISIBLE);
                    adapter= new getCommentAdapter(ctx,response.body());
                    recy.setAdapter(adapter);

                    Handler hndler= new Handler();
                    hndler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.setVisibility(View.VISIBLE);
                            spinner.setVisibility(View.GONE);
                            scrollView.scrollTo(0, CommentStaticDb.scroll_position);
                        }
                        // Bazı sorunlardan dolayı 1ms gecikme ekledim, 1ms sonra nestedScrollView Static database'deki posisyona gidecek
                        // Bu sayede sayfa değiştirince geri geldiğimizde kaldığımız yerden devam edeceğiz
                    },1);


                }else {
                    new Dialog().createDialog(window,0);
                }
            }

            @Override
            public void onFailure(Call<List<getCommentModel>> call, Throwable t) {
                new Dialog().createDialog(window,0);
            }
        });
    }
}
