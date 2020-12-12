package com.igzafer.viking.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.igzafer.viking.Adapter.getCommentAdapter;
import com.igzafer.viking.DialogFragment.Erro;
import com.igzafer.viking.Fragment.CommentFragment.EWDCommentFragment;
import com.igzafer.viking.Fragment.CommentFragment.GetCommentFragment;
import com.igzafer.viking.Model.CommentModels.addCommentModel;
import com.igzafer.viking.Model.CommentModels.getCommentModel;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.R;
import com.igzafer.viking.amaleler.Connection;
import com.igzafer.viking.amaleler.CustomWebview;
import com.igzafer.viking.amaleler.Dialog;
import com.igzafer.viking.amaleler.LoadinDialog;
import com.igzafer.viking.amaleler.apiAuth;
import com.igzafer.viking.api.AuthGerektiren.SendComment;
import com.igzafer.viking.api.AuthGerektiren.SendCommentInterface;
import com.igzafer.viking.api.AuthGerektirmeyen.GetComment;

import java.util.List;

public class ReadPost extends AppCompatActivity implements Erro.succ {

    WebView webView;
    public static int blogid=0;
    CustomWebview customWebview;
    RelativeLayout frameLayout;

    //getCommentAdapter adapter;
    //RecyclerView recyclerView;
    public static boolean selectedcomment=true;
    public static int selectedcommentid=0;
    public static Window window;
    public static BottomSheetBehavior bottomSheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readblog);
        customWebview = new CustomWebview();
        //LoadinDialog.isVisible(this,false);
       // LoadinDialog.isVisible(con,false);
        //sendComment = new SendComment();
        window=getWindow();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            blogid = bundle.getInt("blogid");
        }
        setuptools();
        LoadinDialog.isVisible(getApplicationContext(),false);
        setBottomSheetStyle(this,new GetCommentFragment(),new EWDCommentFragment());
    }
    //EditText comment;
    //TextView paylas;
    //SendComment sendComment;
    //SpinKitView spinKitView;
    RelativeLayout edit_rl;
    //yorumları bottomsheet kaydırılınca sürekli çekmesin diye boolean atadım
    Boolean kilit=true;
    @SuppressLint("ClickableViewAccessibility")
    public static void setBottomSheetStyle (FragmentActivity activity, Fragment getirilecek,Fragment silinecek){
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, getirilecek)
                .remove(silinecek)
                .commit();
        Log.d("winter","winter");
    }
    private void setuptools() {
        webView = findViewById(R.id.web);
        frameLayout=findViewById(R.id.bottomFrame);
       // bottomLayout.setVisibility(View.INVISIBLE);

        bottomSheetBehavior=BottomSheetBehavior.from(frameLayout);
//        comment=bottomLayout.findViewById(R.id.comment);
        bottomSheetBehavior.setFitToContents(false);
        bottomSheetBehavior.setHalfExpandedRatio(0.7f);

//        spinKitView=bottomLayout.findViewById(R.id.loading);
//        spinKitView.setVisibility(View.GONE);
//        paylas=bottomLayout.findViewById(R.id.paylas);
//        paylas.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//              paylas.setVisibility(View.GONE);
//              spinKitView.setVisibility(View.VISIBLE);
//              sendComment.add(getApplicationContext(), new addCommentModel(blogid, comment.getText().toString()), getWindow(), new SendCommentInterface() {
//                  @Override
//                  public void response(Boolean succsess) {
//                      if(succsess){
//                          comment.setText("");
//                          paylas.setVisibility(View.VISIBLE);
//                          spinKitView.setVisibility(View.GONE);
//                          getComment();
//                      }else{
//                          paylas.setVisibility(View.VISIBLE);
//                          spinKitView.setVisibility(View.GONE);
//                      }
//
//                  }
//              });
//            }
//        });
//        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                if(newState==6) {
//                    if (kilit){
//                        kilit = false;
//                        getComment();
//                    }
//
//                }else if(newState==4){
//                    kilit=true;
//
//                }
//
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });
//        recyclerView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (v.getId() == R.id.recy) {
//                    bottomSheetBehavior.setDraggable(true);
//                    return false;
//                }
//                return true;
//            }
//        });
//        comment.setOnTouchListener((v, event) -> {
//            if (v.getId() == R.id.comment) {
//                bottomSheetBehavior.setDraggable(false);
//                return false;
//            }
//            return true;
//        });
//




    }

    @Override
    public void onBackPressed() {
        if(bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
            super.onBackPressed();
           }else{
           bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private void setUpRecy() {

//        recyclerView=bottomLayout.findViewById(R.id.recy);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setHasFixedSize(false);

    }
    GetComment _getComment = new GetComment();
//    private void getComment(){
//        _getComment.get(getApplicationContext(),blogid,getWindow(),recyclerView,edit_rl);
//
//    }

    @Override
    protected void onStart() {
        super.onStart();
        if(Connection.checkInternetConnection(getApplicationContext())){
            LoadinDialog.isVisible(this,true);
            customWebview.tanim(webView,blogid,this,frameLayout);
        }else{
            DialogFragment dialogFragment= Erro.newInstance();
            dialogFragment.setCancelable(false);
            dialogFragment.show(getSupportFragmentManager(),"");
        }
    }


    @Override
    public void success() {
        customWebview.tanim(webView,blogid,this,frameLayout);
        Log.d("winter",String.valueOf(blogid));
    }
}