package com.igzafer.viking.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.igzafer.viking.R;

public class PlaceholderHolder extends RecyclerView.ViewHolder {
    public ShimmerFrameLayout face;
    public TextView txt;
    public PlaceholderHolder(View itemView){
        super(itemView);
        face=itemView.findViewById(R.id.facebook);
        txt=itemView.findViewById(R.id.txt);
    }
}