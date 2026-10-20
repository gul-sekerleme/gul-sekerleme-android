package tr.com.gulsekerleme.panel;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.view.Gravity;

public class MainActivity extends Activity {
    private static final String PANEL_URL =
        "https://gul-sekerleme-yeni.didemboncuk.workers.dev/";

    private WebView webView;
    private TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout root = new FrameLayout(this);
        webView = new WebView(this);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        message = new TextView(this);
        message.setText("Bağlantı kurulamadı. Tekrar denemek için dokunun.");
        message.setGravity(Gravity.CENTER);
        message.setTextColor(Color.rgb(90, 25, 50));
        message.setVisibility(View.GONE);
        message.setOnClickListener(v -> webView.loadUrl(PANEL_URL));

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                message.setVisibility(View.GONE);
            }
        });

        webView.setWebChromeClient(new WebChromeClient());

        root.addView(webView, new FrameLayout.LayoutParams(-1, -1));
        root.addView(message, new FrameLayout.LayoutParams(-1, -1));

        setContentView(root);
        webView.loadUrl(PANEL_URL);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
