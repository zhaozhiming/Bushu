package com.github.zzm.bushu.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.zzm.bushu.app.R;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private String[] values;

    public ImageAdapter(Context context, String[] values) {
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.length;
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
        // get layout from book.xml
        View gridView = inflater.inflate(R.layout.book, null);

        // set value into text view
        TextView textView = (TextView) gridView.findViewById(R.id.grid_item_label);
        textView.setText(values[position]);

        // set value into text view
        TextView desView = (TextView) gridView.findViewById(R.id.grid_description_label);
        desView.setText(values[position]);

        // set image based on selected text
        ImageView imageView = (ImageView) gridView
                .findViewById(R.id.grid_item_image);

        String mobile = values[position];

        if (mobile.equals("One")) {
            imageView.setImageResource(R.drawable.one_logo);
        } else if (mobile.equals("Two")) {
            imageView.setImageResource(R.drawable.two_logo);
        } else if (mobile.equals("Three")) {
            imageView.setImageResource(R.drawable.three_logo);
        } else {
            imageView.setImageResource(R.drawable.else_logo);
        }

        return gridView;
    }
}
