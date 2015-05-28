package com.app.veed.veed;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.KeyEvent;

import com.app.veed.veed.UtilityClasses.HTML5WebView;


public class vimeo_player_activity extends Activity {

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

        /*
        final WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);

        mWebView.setWebViewClient(new WebViewClient() {
            // autoplay when finished loading via javascript injection
            public void onPageFinished(WebView view, String url) {
                mWebView.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()");
            }
        });


        mWebView.setWebChromeClient(new WebChromeClient());

        */


        if (savedInstanceState != null) {
            mWebView.restoreState(savedInstanceState);
        } else {
            mWebView.loadUrl("http://player.vimeo.com/video/" + id);
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
