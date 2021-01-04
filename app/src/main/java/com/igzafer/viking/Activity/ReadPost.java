package com.igzafer.viking.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.igzafer.viking.DialogFragment.InternetError;
import com.igzafer.viking.Fragment.CommentFragment.EWDCommentFragment;
import com.igzafer.viking.Fragment.CommentFragment.GetCommentFragment;
import com.igzafer.viking.Interfaces.IMainResponse;
import com.igzafer.viking.LocalDatabase.CommentStaticDb;
import com.igzafer.viking.Model.ErrorModels.ErrorModel;
import com.igzafer.viking.R;
import com.igzafer.viking.TasarimsalDuzenlemeler.LoadinDialog;
import com.igzafer.viking.WebView.CustomWebview;
import com.igzafer.viking.api.Test.ServerControl;

import retrofit2.Response;

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
        setContentView(R.layout.areadpost);
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
    public static int mod =0 ;
    @Override
    public void onBackPressed() {

        if(mod==0){
            if(bottomSheetBehavior.getState()==BottomSheetBehavior.STATE_COLLAPSED){
                super.onBackPressed();
                CommentStaticDb.scroll_position=0;
            }else{
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }else{
            setBottomSheetStyle(this,new GetCommentFragment(),new EWDCommentFragment());
            mod=0;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        new ServerControl().Control(new IMainResponse() {
            @Override
            public <T> void Succsess(Response<T> _response) {
                LoadinDialog.isVisible(getApplicationContext(),true);
                customWebview.tanim(webView,blogid,getApplicationContext(),frameLayout);
            }

            @Override
            public void Error(ErrorModel _eresponse) {
                DialogFragment dialogFragment= InternetError.newInstance();
                dialogFragment.setCancelable(false);
                dialogFragment.show(getSupportFragmentManager(),"");
            }
        });

    }


    @Override
    public void success() {
        customWebview.tanim(webView,blogid,this,frameLayout);
        Log.d("winter",String.valueOf(blogid));
    }
}