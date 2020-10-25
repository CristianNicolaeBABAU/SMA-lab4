package com.example.lab4;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.annotation.Nullable;

public class ImageIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ImageIntentService() {
        super("Image intent service");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String param = intent.getStringExtra(WebsearchActivity.EXTRA_URL);
            handleDownloadAction(param);
        }
    }

    private void handleDownloadAction(String url){
        try {
            String longURL = URLTools.getLongUrl(url);
            Bitmap bmp = null;
            try {
                InputStream in = new URL(longURL).openStream();
                bmp = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }

            // simulate longer job ...
            Thread.sleep(5000);

            ((getApplication()).getApplicationContext()).setBitmap(bmp);
            // start second activity to show result
            Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
            startActivity(intent);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setBitmap(Bitmap bmp) {
    }
}
