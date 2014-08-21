package com.github.zzm.bushu.app.async;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.*;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, Void, String> {
    private File imageFile;
    private ImageView imageView;

    public DownloadTask(File imageFile, ImageView imageView) {
        this.imageFile = imageFile;
        this.imageView = imageView;
    }

    @Override
    protected String doInBackground(String... urls) {
        storageImage(urls[0]);
        return null;
    }

    @Override
    protected void onPostExecute(String ignore) {
        imageView.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
    }

    private void storageImage(String url) {
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(imageFile);
            outputStream.write(getImageBytes(url));
            outputStream.close();
        } catch (Exception e) {
            Log.e("ERROR", "storage image error:" + e.getMessage());
        }
    }

    private byte[] getImageBytes(String url) throws IOException {
        InputStream in = new BufferedInputStream(new URL(url).openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n;
        while (-1 != (n = in.read(buf))) {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        return out.toByteArray();
    }

}
