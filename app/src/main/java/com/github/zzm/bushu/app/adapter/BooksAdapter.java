package com.github.zzm.bushu.app.adapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.github.zzm.bushu.app.R;
import com.github.zzm.bushu.app.async.DownloadTask;
import com.github.zzm.bushu.app.model.Book;

import java.io.File;
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

        downloadImage(getImageFile(bookName), bookName);
        return gridView;
    }

    private File getImageFile(String bookName) {
        File imageFile = new File(context.getFilesDir(), bookName + ".png");
        Log.d("DEBUG", "file path: " + imageFile.getAbsolutePath());
        return imageFile;
    }

    private void downloadImage(File imageFile, String bookName) {
        if (networkOk() && imageFileEmpty(imageFile)) {
            String url = format("%s%s/%s.png", STORAGE_BASE_URL, getScreenDensity(), bookName);
            Log.d("DEBUG", format("url: %s", url));
            new DownloadTask(context, imageFile).execute(url);
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
