package com.igzafer.viking.Fragment.CommentFragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.igzafer.viking.Animation.Animation;
import com.igzafer.viking.Interfaces.IMainResponse;
import com.igzafer.viking.LocalDatabase.CommentStaticDb;
import com.igzafer.viking.Model.CommentModels.addCommentModel;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.R;
import com.igzafer.viking.TasarimsalDuzenlemeler.Dialog;
import com.igzafer.viking.api.AuthGerektiren.Comment;
import com.igzafer.viking.api.AuthGerektirmeyen.GetComment;

import retrofit2.Response;

import static com.igzafer.viking.Activity.ReadPost.blogid;
import static com.igzafer.viking.Activity.ReadPost.bottomSheetBehavior;
import static com.igzafer.viking.Activity.ReadPost.window;

public class GetCommentFragment extends Fragment {

    public GetCommentFragment() {
        // Required empty public constructor
    }
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fgetcomment, container, false);

        setuptools();
        setupscrool();
    return view;
    }

    private void setupscrool() {

        Log.d("winter",CommentStaticDb.scroll_position+"");
    }

    EditText comment;
    RecyclerView recyclerView;
    TextView paylas,etCharacter;

    SpinKitView spinKitView,spinner;
    NestedScrollView scrollView;
    //RelativeLayout edit_rl;

    //yorumları bottomsheet kaydırılınca sürekli çekmesin diye boolean atadım
    Boolean kilit=true;
    LinearLayout layout;
    @SuppressLint("ClickableViewAccessibility")
    private void setuptools() {
        setUpRecy();
        scrollView=view.findViewById(R.id.scrollView);
        spinner= view.findViewById(R.id.spinner);
        spinner.setVisibility(View.VISIBLE);
        etCharacter=view.findViewById(R.id.etCharacterNum);
        getComment();
        layout=view.findViewById(R.id.botomSheet);
        spinKitView=view.findViewById(R.id.loading);
        spinKitView.setVisibility(View.GONE);

        paylas=view.findViewById(R.id.paylas);
        comment=view.findViewById(R.id.comment);
        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()>20){
                    etCharacter.setVisibility(View.GONE);
                }else{
                    etCharacter.setVisibility(View.VISIBLE);
                    etCharacter.setText(String.valueOf(s.length()+"/20"));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        paylas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etCharacter.setVisibility(View.GONE);
                paylas.setVisibility(View.GONE);
                spinKitView.setVisibility(View.VISIBLE);
                new Comment().sendComment(getContext(), new addCommentModel(blogid, comment.getText().toString()), new IMainResponse() {
                    @Override
                    public <T> void Succsess(Response<T> _response) {
                        comment.setText("");
                        paylas.setVisibility(View.VISIBLE);
                        etCharacter.setVisibility(View.VISIBLE);
                        spinKitView.setVisibility(View.GONE);
                        getComment();
                    }

                    @Override
                    public void Error(ErrorModel _eresponse) {
                        comment.startAnimation(new Animation().shakeIt());
                        paylas.startAnimation(new Animation().shakeIt());
                        etCharacter.startAnimation(new Animation().shakeIt());
                        try {
                            paylas.setVisibility(View.VISIBLE);
                            etCharacter.setVisibility(View.VISIBLE);

                            spinKitView.setVisibility(View.GONE);
                            //new Dialog().createDialog(requireActivity().getWindow(),_eresponse.getBody(),0);
                        }catch (Exception e){
                            paylas.setVisibility(View.VISIBLE);
                            etCharacter.setVisibility(View.VISIBLE);
                            spinKitView.setVisibility(View.GONE);
                            if(getActivity()!=null){
                              //  new Dialog().createDialog(requireActivity().getWindow(),0);
                            }

                        }

                    }

                });
            }
        });
        settings();

    }
    @SuppressLint("ClickableViewAccessibility")
    private void settings(){
            bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if(newState==6) {
                    if (kilit){
                        kilit = false;
                        getComment();
                    }

                }else if(newState==4){
                    kilit=true;

                }

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    CommentStaticDb.scroll_position=scrollY;
                }
            });
        }
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.scrollView) {
                    bottomSheetBehavior.setDraggable(true);
                    CommentStaticDb.scroll_position=recyclerView.getScrollY();
                    Log.d("winter",recyclerView.getScrollX()+"");
                    return false;
                }
                return true;
            }
        });
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.recy) {
                    bottomSheetBehavior.setDraggable(true);
                    CommentStaticDb.scroll_position=recyclerView.getScrollY();
                    Log.d("winter",recyclerView.getScrollX()+"");
                    return false;
                }
                return true;
            }
        });

        comment.setOnTouchListener((v, event) -> {
            if (v.getId() == R.id.comment) {
                bottomSheetBehavior.setDraggable(false);
                return false;
            }
            return true;
        });



    }
        private void setUpRecy() {

            recyclerView=view.findViewById(R.id.recy);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setHasFixedSize(false);

        }
        GetComment _getComment = new GetComment();
        private void getComment(){
            try {
                _getComment.get(getContext(), blogid, window,recyclerView,scrollView,spinner);
            }catch (Exception e){
                new Dialog().createDialog(window,0);
            }


        }


    }

