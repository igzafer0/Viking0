package com.igzafer.viking.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.igzafer.viking.Activity.ReadPost;
import com.igzafer.viking.Fragment.CommentFragment.EWDCommentFragment;
import com.igzafer.viking.Fragment.CommentFragment.GetCommentFragment;
import com.igzafer.viking.LocalDatabase.LocalDatabase;
import com.igzafer.viking.Model.CommentModels.getCommentModel;
import com.igzafer.viking.R;
import com.igzafer.viking.RestApi.BaseUrl;
import com.igzafer.viking.ViewHolder.CommentHolder;
import com.igzafer.viking.amaleler.Dialog;
import com.igzafer.viking.amaleler.UnsafeHttp;
import com.igzafer.viking.api.AuthGerektirmeyen.GetComment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.OkHttpClient;

public class getCommentAdapter extends RecyclerView.Adapter<CommentHolder> {
    Context main;
    List<getCommentModel> getCommentModels;
    View view;
    public getCommentAdapter(Context main, List<getCommentModel> getCommentModels) {
        this.main = main;
        this.getCommentModels = getCommentModels;

    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(main);
        view= layoutInflater.inflate(R.layout.commentrow,parent,false);
        return new CommentHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        try {
            holder.date.setText(getCommentModels.get(position).getTime());
            holder.text.setText(getCommentModels.get(position).getText());
            holder.name.setText(getCommentModels.get(position).getNick());
            Glide.with(main).load(BaseUrl.pp_Url+getCommentModels.get(position).getAvatar()).into(holder.avatar);
            //Log.d("winter",""+position);
        }catch (Exception e){
            Log.d("winter",e.getMessage());
        }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ReadPost.setBottomSheetStyle((FragmentActivity)main,new EWDCommentFragment(),new GetCommentFragment());

                }
            });

    }

    @Override
    public int getItemCount() {
        return getCommentModels.size();
    }
}
