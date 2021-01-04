package com.igzafer.viking.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.igzafer.viking.Activity.ReadPost;
import com.igzafer.viking.Model.BlogModels.BlogModel;
import com.igzafer.viking.R;
import com.igzafer.viking.TasarimsalDuzenlemeler.Dialog;
import com.igzafer.viking.TasarimsalDuzenlemeler.LoadinDialog;
import com.igzafer.viking.ViewHolder.RecylerHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

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
        View view= layoutInflater.inflate(R.layout.designblog,parent,false);
         return new RecylerHolder(view);

    }
    Boolean kilit=false;

    @SuppressLint("ClickableViewAccessibility")
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

                            picasso = new Picasso.Builder(main).build();

                            picasso.load(blogModels.get(position).getPhoto())
                                    .into(holder.imageView);
                        }
                    });
        }catch (Exception e){
            new Dialog().createDialog(window,0);
        }

        setBW(holder.imageView);
        holder.baslik.setTextColor(ContextCompat.getColor(main, R.color.dumanlı_white));
        holder.rl.setOnTouchListener((v, event) -> {
            Log.d("winter",""+event.getAction());
            if(event.getAction()!=3){
                if(!kilit){
                    kilit=true;
                    holder.rl.animate().scaleX(0.92f).scaleY(0.92f);
                }

            }else{
                kilit=false;
                holder.rl.animate().scaleX(1.0f).scaleY(1.0f);

            }
            return false;
        });

        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadinDialog.isVisible(main,true);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent(main,ReadPost.class);
                        intent.putExtra("blogid",blogModels.get(position).getId());
                        main.startActivity(intent);
                    }
                });

                //newFragment.getView().setBackground(new ColorDrawable(Color.TRANSPARENT));
                //Toast.makeText(main,String.valueOf(blogModels.get(position).getId()),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void setBW(ImageView iv){

        float brightness = 10; // change values to suite your need

        float[] colorMatrix = {
                0.33f, 0.33f, 0.33f, 0, brightness,
                0.33f, 0.33f, 0.33f, 0, brightness,
                0.33f, 0.33f, 0.33f, 0, brightness,
                0, 0, 0, 1, 0
        };

        ColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
        iv.setColorFilter(colorFilter);
    }
    @Override
    public int getItemCount() {
        return blogModels.size();
    }
}
