package com.igzafer.viking.Fragment.CommentFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.igzafer.viking.R;

//edit write delete comment fragment
public class EWDCommentFragment extends Fragment {

    public EWDCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ewdcommentfragment, container, false);
    }
}
