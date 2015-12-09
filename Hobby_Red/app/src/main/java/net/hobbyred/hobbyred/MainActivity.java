package net.hobbyred.hobbyred;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.facebook.FacebookSdk;

public class MainActivity extends AppCompatActivity {

    private WebView myWebView;
    private String url = "http://www.hobbyred.net";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FacebookSdk.sdkInitialize(getApplicationContext());

        myWebView = (WebView) findViewById(R.id.webview);


        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);


        if (Build.VERSION.SDK_INT >= 21) {
            myWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager.getInstance().setAcceptThirdPartyCookies(myWebView, true);
        }

        myWebView.loadUrl(url);
        myWebView.setWebViewClient(new WebViewClient());
    }

    /**
     * Acción botón atrás, navega hacia atrás dentro del webview
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (myWebView.canGoBack()) {
                        myWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * Evento app en segundo plano
     * se detiene la actividad del webview
     */
    @Override
    protected void onPause(){
        super.onPause();
        if(myWebView != null){
            myWebView.onPause();
            myWebView.pauseTimers();
        }
    }

    /**
     * Evento reanudar app desde segundo plano
     * se reanuda la actividad del webview
     */
    @Override
    protected void onResume(){
        super.onResume();
        if(myWebView != null){
            myWebView.onResume();
            myWebView.resumeTimers();
        }
    }
}
