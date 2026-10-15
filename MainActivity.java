package tr.com.gulsekerleme.panel;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceError;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.view.Gravity;

public class MainActivity extends Activity {
    private static final String PANEL_URL = "https://gul-sekerleme-panel.didemboncuk.workers.dev/";
    private WebView webView;
    private TextView message;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout root = new FrameLayout(this);
        root.setBackgroundColor(Color.WHITE);
        webView = new WebView(this);
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setLoadsImagesAutomatically(true);
        s.setAllowFileAccess(false);
        s.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        webView.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String scheme = request.getUrl().getScheme();
                return !("https".equalsIgnoreCase(scheme));
            }
            @Override public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                if (request.isForMainFrame()) {
                    message.setVisibility(View.VISIBLE);
                    message.setText("Panel yüklenemedi. İnternet bağlantısını ve Cloudflare dağıtım durumunu kontrol edin. Yeniden denemek için ekrana dokunun.");
                }
            }
            @Override public void onPageFinished(WebView view, String url) {
                message.setVisibility(View.GONE);
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(PANEL_URL);
        message = new TextView(this);
        message.setTextColor(Color.rgb(90, 25, 50));
        message.setBackgroundColor(Color.WHITE);
        message.setGravity(Gravity.CENTER);
        message.setPadding(32, 32, 32, 32);
        message.setVisibility(View.GONE);
        message.setOnClickListener(v -> webView.loadUrl(PANEL_URL));
        root.addView(webView, new FrameLayout.LayoutParams(-1, -1));
        root.addView(message, new FrameLayout.LayoutParams(-1, -1));
        setContentView(root);
    }

    @Override public void onBackPressed() {
        if (webView != null && webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }

    @Override protected void onDestroy() {
        if (webView != null) { webView.destroy(); webView = null; }
        super.onDestroy();
    }
}
