package com.igzafer.viking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.igzafer.viking.Model.BlogModels.BlogModel;
import com.igzafer.viking.R;
import com.igzafer.viking.ViewHolder.PlaceholderHolder;

import java.util.List;
import java.util.Random;

public class PlaceholderAdapter  extends RecyclerView.Adapter<PlaceholderHolder> {
    Context main;
    List<BlogModel> blogModels;

    public PlaceholderAdapter(Context main, List<BlogModel> blogModels) {
        this.main = main;
        this.blogModels = blogModels;
    }

    @NonNull
    @Override
    public PlaceholderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(main);
        View view= layoutInflater.inflate(R.layout.placeholder,parent,false);

        return new PlaceholderHolder(view);

    }
    Random rnd=new Random();
    @Override
    public void onBindViewHolder(@NonNull PlaceholderHolder holder, int position) {
        holder.face.startShimmer();
        int x=rnd.nextInt(2);
        if(x==0){
            holder.txt.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return blogModels.size();
    }
}