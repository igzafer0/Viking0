package com.igzafer.viking.DialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import com.igzafer.viking.R;
import com.igzafer.viking.RestApi.BaseManager;
import com.igzafer.viking.RestApi.BaseUrl;
import com.igzafer.viking.amaleler.Connection;
import com.igzafer.viking.amaleler.CustomWebview;
import com.igzafer.viking.amaleler.LoadinDialog;

import java.util.Timer;
import java.util.TimerTask;

public class ReadBlog extends DialogFragment implements  Erro.succ{


    public static DialogFragment newInstance() {
        return new ReadBlog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.readTheme);
    }

    View view;
    WebView webView;
    int blogid;
    CustomWebview customWebview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.readblog, container, false);

        setuptools();
        return view;
    }

    private void setuptools() {
        Bundle bundle = getArguments();
        blogid = bundle.getInt("blogid", 1);
        customWebview = new CustomWebview();
        webView = view.findViewById(R.id.web);




    }


    @Override
    public void onStart() {
        super.onStart();
        if(Connection.checkInternetConnection(getContext())){
            LoadinDialog.isVisible(getContext(),true);
            customWebview.tanim(webView,blogid,view.getContext(),null);
        }else{
            DialogFragment dialogFragment= Erro.newInstance();
            dialogFragment.setCancelable(false);
            dialogFragment.show(getChildFragmentManager(),"");
            dialogFragment.onAttach(getContext());


        }


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoadinDialog.isVisible(getActivity().getApplicationContext(),false);

    }


    @Override
    public void success() {
        Toast.makeText(getContext(),"text",Toast.LENGTH_LONG).show();
        Log.d("winter","sssssss");
    }
}
