package com.example.saboorsalaam.veed10;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import UtilityClasses.HTML5WebView;


public class VimeoPlayerActivity extends Activity {

    HTML5WebView mWebView;
    String id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setDisplayHomeAsUpEnabled(true);


        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            id = extras.getString("video_id");
        }

        mWebView = new HTML5WebView(this);
        final WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.addJavascriptInterface(new AppJavaScriptProxy(this, mWebView, id), "androidAppProxy");

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(id))
                {
                    return true;
                }
                return false;
            }

            // autoplay when finished loading via javascript injection
            public void onPageFinished(WebView view, String url) {
                mWebView.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()");
            }
        });


        if (savedInstanceState != null) {
            mWebView.restoreState(savedInstanceState);
        } else {
            mWebView.loadUrl("http://vimeo.com/" + id + "/description");
        }

        setContentView(mWebView.getLayout());


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mWebView.saveState(outState);
    }

    @Override
    public void onStop() {
        super.onStop();
        mWebView.stopLoading();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onResume() {
        mWebView.onResume();
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.inCustomView()) {
                mWebView.hideCustomView();
                mWebView.goBack();
                NavUtils.navigateUpFromSameTask(this);
                return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }


}
