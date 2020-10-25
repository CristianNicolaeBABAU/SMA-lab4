package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

public class WebsearchActivity extends AppCompatActivity {
    WebView webView;
    Button bkg_service,frg_service;
    public final String EXTRA_URL = "https://images.app.goo.gl/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websearch);

        webView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.com/search?q=cat&sxsrf=ALeKk01HRjUv6pecYlvrToz4QX0wwqunrA:1603630197342&source=lnms&tbm=isch&sa=X&ved=2ahUKEwiXm42C5M_sAhWJM-wKHSEoCdIQ_AUoAXoECBwQAw&biw=2400&bih=1211");

    }

    public void loadImage(View view){
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = clipboardManager.getPrimaryClip();
        ClipData.Item item = clipData.getItemAt(0);
        String url = item.getText().toString();

        if (!url.contains("https://images.app.goo.gl/"))
            Toast.makeText(this, "URL not valid. Try another one.", Toast.LENGTH_SHORT).show();
        else {
            if(view.getId() == R.id.bLoadBackground) {
                Intent intent = new Intent(this, ImageIntentService.class);
                intent.putExtra(EXTRA_URL, url);
                startService(intent);
            }
            else if (view.getId() == R.id.bLoadForeground) {
                Intent startIntent = new Intent(this, ForegroundImageService.class);
                startIntent.setAction(ForegroundImageService.STARTFOREGROUND_ACTION);
                startIntent.putExtra(EXTRA_URL, url);
                startService(startIntent);
            }
        }
    }
}