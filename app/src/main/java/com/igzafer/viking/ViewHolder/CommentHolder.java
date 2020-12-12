package com.igzafer.viking.ViewHolder;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.igzafer.viking.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentHolder  extends RecyclerView.ViewHolder{
    public TextView text;
    public TextView date;
    public TextView name;
    public RelativeLayout rl;
    public CircleImageView avatar;

    
   // public View selectable;
    public CommentHolder(View itemView){
        super(itemView);
        text=itemView.findViewById(R.id.text);
        name=itemView.findViewById(R.id.name);
        avatar=itemView.findViewById(R.id.avatar);
        date=itemView.findViewById(R.id.date);
        rl=itemView.findViewById(R.id.rl);
    }

}
