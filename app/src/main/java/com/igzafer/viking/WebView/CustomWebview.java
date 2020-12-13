package com.igzafer.viking.WebView;

import android.content.Context;
import android.net.http.SslError;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.igzafer.viking.RestApi.BaseUrl;
import com.igzafer.viking.TasarimsalDuzenlemeler.LoadinDialog;

public class CustomWebview extends WebViewClient {
    Context main;
    RelativeLayout rl;
    public void tanim(final WebView webView, int blogid, Context context,RelativeLayout rl1){
        main=context;
        rl=rl1;
        //LoadinDialog.isVisible(main,true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setDisplayZoomControls(false);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.setWebViewClient(this);
        webView.loadUrl(BaseUrl.site_url+"blogs/getblog?id="+blogid);


    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        LoadinDialog.isVisible(main,false);
        rl.setVisibility(View.VISIBLE);

    }

    @Override
    public void onPageCommitVisible(WebView view, String url) {
        super.onPageCommitVisible(view, url);

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        view.loadUrl(url);
        System.out.println("hello");
        return false;
    }

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);




    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
        Log.d("winter http", "" + errorResponse);

    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        //super.onReceivedSslError(view, handler, error);
        handler.proceed();
        Log.d("winter ssl", "" + error);

    }
}
