package com.github.zzm.bushu.app.card;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.github.zzm.bushu.app.model.Book;
import com.github.zzm.bushu.app.model.Density;
import com.github.zzm.bushu.app.model.LogTag;
import com.squareup.picasso.Picasso;
import it.gmariotti.cardslib.library.internal.CardThumbnail;

import static java.lang.String.format;

public class BookThumbnail extends CardThumbnail {
    private static final String STORAGE_BASE_URL = "http://depblog-dpdomain.stor.sinaapp.com/";

    private Book book;

    public BookThumbnail(Context context, Book book) {
        super(context);
        this.book = book;
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View viewImage) {
        String url = format("%s%s/%s.png", STORAGE_BASE_URL,
                getScreenDensity(), book.getEnName());
        Log.d(LogTag.MainActivity.name(), "url:" + url);
        Picasso.with(getContext()).load(url).into((ImageView) viewImage);
    }

    private String getScreenDensity() {
        switch (getContext().getResources().getDisplayMetrics().densityDpi) {
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
