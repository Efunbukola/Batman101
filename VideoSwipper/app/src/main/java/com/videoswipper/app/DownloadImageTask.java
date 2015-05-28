package com.videoswipper.app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Saboor Salaam on 6/12/2014.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;
    int screenWidth, getScreenHeight;
    float t;

    public DownloadImageTask(ImageView bmImage, int w, int h) {
        this.bmImage = bmImage; this.screenWidth = w; this.getScreenHeight = h;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        int bw = mIcon11.getWidth();
        t = (float) screenWidth / (float) bw;

        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {


        bmImage.getLayoutParams().width = screenWidth;
        bmImage.getLayoutParams().height = (int) (result.getHeight() * t);
        Bitmap scaledIcon = Bitmap.createScaledBitmap(result, screenWidth, (int) (result.getHeight() * t), false);
        bmImage.setImageBitmap(result);

    }
}
