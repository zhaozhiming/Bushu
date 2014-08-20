package com.github.zzm.bushu.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.zzm.bushu.app.R;
import com.github.zzm.bushu.app.model.Book;

import java.io.*;
import java.net.URL;
import java.util.List;

import static java.lang.String.format;

public class BooksAdapter extends BaseAdapter {
    private static final String STORAGE_BASE_URL = "http://depblog-dpdomain.stor.sinaapp.com/";
    private static final String LDPI = "ldpi";
    private static final String MDPI = "mdpi";
    private static final String HDPI = "hdpi";
    private static final String XHDPI = "xhdpi";
    private static final String XXHDPI = "xxhdpi";
    private Context context;
    private List<Book> books;

    public BooksAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        if (convertView != null) return convertView;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView = inflater.inflate(R.layout.book, null);

        Book book = books.get(position);
        String bookName = book.getName();

        TextView textView = (TextView) gridView.findViewById(R.id.book_name);
        textView.setText(bookName);

        TextView borrowPeopleView = (TextView) gridView.findViewById(R.id.book_borrow_people);
        borrowPeopleView.setText(format("borrowed: %s", book.getBorrowPeople()));

        TextView returnDaysView = (TextView) gridView.findViewById(R.id.book_return_days);
        returnDaysView.setText(format("after %d days return", book.returnDays()));

        ImageView imageView = (ImageView) gridView.findViewById(R.id.book_image);
        Bitmap myBitmap = getImageBitmap(bookName);

        imageView.setImageBitmap(myBitmap);

        return gridView;
    }

    private Bitmap getImageBitmap(String bookName) {
        File imageFile = new File(context.getFilesDir(), bookName + ".png");
        Log.v(this.getClass().getName(), "file path: " + imageFile.getAbsolutePath());
        Bitmap myBitmap;
        if (imageFile.exists() && imageFile.length() != 0) {
            myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        } else {
            myBitmap = BitmapFactory.decodeFile(storageImage(bookName).getAbsolutePath());
        }
        return myBitmap;
    }

    private File storageImage(String bookName) {
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(format("%s.png", bookName), Context.MODE_PRIVATE);
            outputStream.write(getImageBytes(bookName));
            outputStream.close();
        } catch (Exception e) {
            Log.e(this.getClass().getName(), "storage image error:" + e.getMessage());
            e.printStackTrace();
        }
        return new File(context.getFilesDir(), format("%s.png", bookName));
    }

    private byte[] getImageBytes(String bookName) throws IOException {
        String url = format("%s%s/%s.png", STORAGE_BASE_URL, getScreenDensity(), bookName);
        Log.v(this.getClass().getName(), format("url: %s", url));
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

    private String getScreenDensity() {
        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return LDPI;
            case DisplayMetrics.DENSITY_MEDIUM:
                return MDPI;
            case DisplayMetrics.DENSITY_HIGH:
                return HDPI;
            case DisplayMetrics.DENSITY_XHIGH:
                return XHDPI;
            case DisplayMetrics.DENSITY_XXHIGH:
                return XXHDPI;
            default:
                return MDPI;
        }
    }
}
