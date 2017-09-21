package api.vdp.visa.com.instagramapp.ui.home;

import android.arch.lifecycle.LifecycleActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import api.vdp.visa.com.instagramapp.R;
import api.vdp.visa.com.instagramapp.ui.posts.PostsListActivity;
import api.vdp.visa.com.instagramapp.utils.CookieUtils;
import api.vdp.visa.com.instagramapp.utils.SharedPreferencesUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import api.vdp.visa.com.instagramapp.utils.OAuthConstants;

public class InstagramHomeActivity extends LifecycleActivity {

    @BindView(R.id.webview)
    WebView webView;

    WebViewClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram_home);
        ButterKnife.bind(this);
        loadWebView();
    }

    private void loadWebView(){
        String url = OAuthConstants.AUTH_URL;
        client = new InstagramWebViewClient();
        webView.setWebViewClient(client);
        webView.clearCache(true);
        CookieUtils.clearCookies(this);
        webView.loadUrl(url);
    }

    private void sendToPosts(String accessToken){
        Intent postsActiviy = new Intent(this,PostsListActivity.class);
        postsActiviy.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(postsActiviy);
        finish();
    }


    public class InstagramWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            String REDIRECT_URL = OAuthConstants.AUTH_SUCCESS_URL;
            if(url.startsWith(REDIRECT_URL)){
                String accessToken = url.substring(REDIRECT_URL.length());
                SharedPreferencesUtils.insertIntoSharedPreferences(getApplicationContext(),OAuthConstants.ACCESS_TOKEN,accessToken);
                sendToPosts(accessToken);

            }else{
                super.onPageStarted(view, url, favicon);
            }
        }
    }



}
