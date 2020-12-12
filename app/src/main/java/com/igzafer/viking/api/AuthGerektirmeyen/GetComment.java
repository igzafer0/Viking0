package com.igzafer.viking.api.AuthGerektirmeyen;

import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.igzafer.viking.Activity.ReadPost;
import com.igzafer.viking.Adapter.RecylerAdapter;
import com.igzafer.viking.Adapter.getCommentAdapter;
import com.igzafer.viking.Model.CommentModels.getCommentModel;
import com.igzafer.viking.RestApi.ManagerAll;
import com.igzafer.viking.amaleler.Dialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetComment {
    getCommentAdapter adapter;
    //Yorumları çekip otomatik adaptera ve recye atıyor. Edit menüsü olan relative layout da burada veriliyor.
    public void get(Context ctx, int BlogId, Window window, RecyclerView recy){
        Call<List<getCommentModel>> istek= ManagerAll.getInstance().getComment(BlogId);
        istek.enqueue(new Callback<List<getCommentModel>>() {
            @Override
            public void onResponse(Call<List<getCommentModel>> call, Response<List<getCommentModel>> response) {
                if(response.isSuccessful()){
                    ReadPost.selectedcomment=true;
                   // rl.setVisibility(View.GONE);
                    adapter= new getCommentAdapter(ctx,response.body());
                    recy.setAdapter(adapter);
                }else {
                    Dialog.createDialog(window,"Bir hata oluştu","Hata kodu; "+response.code(),0);
                }
            }

            @Override
            public void onFailure(Call<List<getCommentModel>> call, Throwable t) {
                Dialog.createDialog(window,"Bir hata oluştu","Hata kodu; "+t.getMessage(),0);
            }
        });
    }
}
