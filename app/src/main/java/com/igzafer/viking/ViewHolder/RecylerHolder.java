package com.igzafer.viking.ViewHolder;

import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.igzafer.viking.R;

public class RecylerHolder extends RecyclerView.ViewHolder {
    public TextView baslik;
    public RelativeLayout rl;
    public ImageView imageView;
    public RecylerHolder(View itemView){
        super(itemView);
        baslik=itemView.findViewById(R.id.baslik);
        imageView=itemView.findViewById(R.id.foto);
        rl=itemView.findViewById(R.id.rl);
    }
}
