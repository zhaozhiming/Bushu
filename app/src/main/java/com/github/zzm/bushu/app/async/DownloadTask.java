package com.github.zzm.bushu.app.async;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.github.zzm.bushu.app.R;

import java.io.*;
import java.net.URL;

import static java.lang.String.format;

public class DownloadTask extends AsyncTask<String, Void, String> {
    private Context context;

    public DownloadTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        storageImage(urls[0], urls[1]);
        return urls[1];
    }

    @Override
    protected void onPostExecute(String bookName) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView = inflater.inflate(R.layout.book, null);

        ImageView imageView = (ImageView) gridView.findViewById(R.id.book_image);
        File imageFile = getImageFile(bookName);

        imageView.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
    }

    private File getImageFile(String bookName) {
        File imageFile = new File(context.getFilesDir(), bookName + ".png");
        Log.d("DEBUG", "file path: " + imageFile.getAbsolutePath());
        return imageFile;
    }

    private void storageImage(String url, String bookName) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(format("%s.png", bookName), Context.MODE_PRIVATE);
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
