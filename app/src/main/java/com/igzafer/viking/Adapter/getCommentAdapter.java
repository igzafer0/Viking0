package com.igzafer.viking.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.igzafer.viking.Activity.ReadPost;
import com.igzafer.viking.Fragment.CommentFragment.EWDCommentFragment;
import com.igzafer.viking.Fragment.CommentFragment.GetCommentFragment;
import com.igzafer.viking.Model.CommentModels.getCommentModel;
import com.igzafer.viking.R;
import com.igzafer.viking.RestApi.BaseUrl;
import com.igzafer.viking.ViewHolder.CommentHolder;

import java.util.List;

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
