package com.igzafer.viking.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.igzafer.viking.DialogFragment.InternetError;
import com.igzafer.viking.Fragment.CommentFragment.EWDCommentFragment;
import com.igzafer.viking.Fragment.CommentFragment.GetCommentFragment;
import com.igzafer.viking.R;
import com.igzafer.viking.WebView.CustomWebview;
import com.igzafer.viking.TasarimsalDuzenlemeler.LoadinDialog;
import com.igzafer.viking.api.Test.ConnectionTest;

public class ReadPost extends AppCompatActivity implements InternetError.succ {

    WebView webView;
    public static int blogid=0;
    CustomWebview customWebview;
    RelativeLayout frameLayout;
    public static Window window;
    public static BottomSheetBehavior bottomSheetBehavior;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readblog);
        customWebview = new CustomWebview();
        window=getWindow();
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            blogid = bundle.getInt("blogid");
        }
        setuptools();
        LoadinDialog.isVisible(getApplicationContext(),false);
        setBottomSheetStyle(this,new GetCommentFragment(),new EWDCommentFragment());
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void setBottomSheetStyle (FragmentActivity activity, Fragment getirilecek,Fragment silinecek){
        activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame, getirilecek)
                .remove(silinecek)
                .commit();

    }
    private void setuptools() {
        webView = findViewById(R.id.web);
        frameLayout=findViewById(R.id.bottomFrame);
        frameLayout.setVisibility(View.GONE);
        bottomSheetBehavior=BottomSheetBehavior.from(frameLayout);
        bottomSheetBehavior.setFitToContents(false);
        bottomSheetBehavior.setHalfExpandedRatio(0.7f);




    }

    @Override
    public void onBackPressed() {
        if(bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
            super.onBackPressed();
           }else{
           bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionTest.iConnect(new ConnectionTest.conTest() {
            @Override
            public void Connected(Boolean connected) {
                if(connected){
                    LoadinDialog.isVisible(getApplicationContext(),true);
                    customWebview.tanim(webView,blogid,getApplicationContext(),frameLayout);
                }else{
                    DialogFragment dialogFragment= InternetError.newInstance();
                    dialogFragment.setCancelable(false);
                    dialogFragment.show(getSupportFragmentManager(),"");
                }
            }
        });

    }


    @Override
    public void success() {
        customWebview.tanim(webView,blogid,this,frameLayout);
        Log.d("winter",String.valueOf(blogid));
    }
}