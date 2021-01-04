package com.igzafer.viking.Fragment.CommentFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.igzafer.viking.Activity.ReadPost;
import com.igzafer.viking.R;
import com.igzafer.viking.TasarimsalDuzenlemeler.Dialog;

//edit write delete comment fragment
public class EWDCommentFragment extends Fragment {

    public EWDCommentFragment() {
        // Required empty public constructor
    }
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fcommentreply, container, false);
        tools();
        return view;
    }
    ImageView back;
    private void tools() {
    back=view.findViewById(R.id.back);
    back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            back();
        }
    });
    }

    private void back() {
        try {
            ReadPost.setBottomSheetStyle((FragmentActivity) requireContext(),new GetCommentFragment(),this);
            ReadPost.mod=0;
        }catch (Exception e){
            if(getActivity()!=null){
                new Dialog().createDialog(getActivity().getWindow(),0);
            }
        }


    }
}
