package com.github.zzm.bushu.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.github.zzm.bushu.app.R;
import com.github.zzm.bushu.app.model.Book;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.List;

import static java.lang.String.format;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private List<Book> books;

    public ImageAdapter(Context context, List<Book> books) {
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

        TextView textView = (TextView) gridView.findViewById(R.id.book_name);
        textView.setText(books.get(position).getName());

        TextView borrowPeopleView = (TextView) gridView.findViewById(R.id.book_borrow_people);
        borrowPeopleView.setText("borrowed: " + books.get(position).getBorrowPeople());

        TextView returnDaysView = (TextView) gridView.findViewById(R.id.book_return_days);
        DateTime returnDate = new DateTime(books.get(position).getReturnDate());
        int days = Days.daysBetween(DateTime.now().withTimeAtStartOfDay(), returnDate.withTimeAtStartOfDay()).getDays();
        returnDaysView.setText(format("after %d days return", days));

        return gridView;
    }
}
