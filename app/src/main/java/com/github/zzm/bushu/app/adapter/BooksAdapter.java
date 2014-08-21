package com.github.zzm.bushu.app.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.zzm.bushu.app.R;
import com.github.zzm.bushu.app.async.DownloadTask;
import com.github.zzm.bushu.app.model.Book;
import com.github.zzm.bushu.app.model.Density;
import com.github.zzm.bushu.app.model.LogTag;

import java.io.File;
import java.util.List;

import static java.lang.String.format;

public class BooksAdapter extends BaseAdapter {
    private static final String STORAGE_BASE_URL = "http://depblog-dpdomain.stor.sinaapp.com/";
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
        String bookName = book.getEnName();

        TextView textView = (TextView) gridView.findViewById(R.id.book_name);
        textView.setText(book.getZhName());

        TextView borrowPeopleView = (TextView) gridView.findViewById(R.id.book_borrow_people);
        borrowPeopleView.setText(format("borrowed: %s", book.getBorrowPeople()));

        TextView returnDaysView = (TextView) gridView.findViewById(R.id.book_return_days);
        returnDaysView.setText(format("after %d days return", book.returnDays()));

        File imageFile = getImageFile(bookName);
        ImageView imageView = (ImageView) gridView.findViewById(R.id.book_image);
        if (imageFileEmpty(imageFile)) {
            downloadImage(bookName, imageView);
        } else {
            imageView.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
        }

        return gridView;
    }

    private File getImageFile(String bookName) {
        File imageFile = new File(context.getFilesDir(), bookName + ".png");
        Log.d(LogTag.BooksAdapter.name(), "file path: " + imageFile.getAbsolutePath());
        return imageFile;
    }

    private void downloadImage(String bookName, ImageView imageView) {
        if (networkOk()) {
            String url = format("%s%s/%s.png", STORAGE_BASE_URL, getScreenDensity(), bookName);
            Log.d(LogTag.BooksAdapter.name(), format("url: %s", url));
            new DownloadTask(getImageFile(bookName), imageView).execute(url);
        }
    }

    private boolean imageFileEmpty(File imageFile) {
        return !imageFile.exists() || imageFile.length() == 0;
    }

    private boolean networkOk() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private String getScreenDensity() {
        switch (context.getResources().getDisplayMetrics().densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return Density.LDPI.name().toLowerCase();
            case DisplayMetrics.DENSITY_MEDIUM:
                return Density.MDPI.name().toLowerCase();
            case DisplayMetrics.DENSITY_HIGH:
                return Density.HDPI.name().toLowerCase();
            case DisplayMetrics.DENSITY_XHIGH:
                return Density.XHDPI.name().toLowerCase();
            case DisplayMetrics.DENSITY_XXHIGH:
                return Density.XXHDPI.name().toLowerCase();
            default:
                return Density.HDPI.name().toLowerCase();
        }
    }
}
