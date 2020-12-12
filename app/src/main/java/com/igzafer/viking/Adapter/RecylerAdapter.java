package com.igzafer.viking.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igzafer.viking.Activity.ReadPost;
import com.igzafer.viking.Model.BlogModels.BlogModel;
import com.igzafer.viking.R;
import com.igzafer.viking.ViewHolder.RecylerHolder;
import com.igzafer.viking.amaleler.Dialog;
import com.igzafer.viking.amaleler.LoadinDialog;
import com.igzafer.viking.amaleler.UnsafeHttp;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.OkHttpClient;

public class RecylerAdapter extends RecyclerView.Adapter<RecylerHolder> {
    Context main;
    Window window;
    List<BlogModel> blogModels;

    public RecylerAdapter(Context main, List<BlogModel> blogModels, Window window) {
        this.main = main;
        this.blogModels = blogModels;
        this.window=window;
    }
    Picasso picasso;
    @NonNull
    @Override
    public RecylerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(main);
        View view= layoutInflater.inflate(R.layout.blog_row,parent,false);
         return new RecylerHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecylerHolder holder, int position) {
        holder.baslik.setText(blogModels.get(position).getBaslik());
        //picasso.setLoggingEnabled(true);
        try {
            Picasso.get().load(blogModels.get(position).getPhoto())
                    .into(holder.imageView, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            OkHttpClient picassoClient = UnsafeHttp.getUnsafeOkHttpClient();
                            picasso = new Picasso.Builder(main).downloader(new OkHttp3Downloader(picassoClient)).build();

                            picasso.load(blogModels.get(position).getPhoto())
                                    .into(holder.imageView);
                        }
                    });
        }catch (Exception e){
            Dialog.createDialog(window,"Büyük bir başarısızlık oldu","Büyük bir başarısızlık oldu. Bundan haberdarız ve üzerinde çalışıyoruz",0);
        }
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadinDialog.isVisible(main,true);
                Intent intent=new Intent(main,ReadPost.class);
                intent.putExtra("blogid",blogModels.get(position).getId());
                main.startActivity(intent);

                //newFragment.getView().setBackground(new ColorDrawable(Color.TRANSPARENT));
                //Toast.makeText(main,String.valueOf(blogModels.get(position).getId()),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return blogModels.size();
    }
}
