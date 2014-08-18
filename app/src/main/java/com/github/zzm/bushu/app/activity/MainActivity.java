package com.github.zzm.bushu.app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;
import com.github.zzm.bushu.app.R;
import com.github.zzm.bushu.app.adapter.ImageAdapter;
import com.github.zzm.bushu.app.model.Book;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ImageAdapter(this, getData()));

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Book> getData() {
        List<Book> books = Lists.newArrayList();

        books.add(new Book("Book1", "Tom", DateTime.now().plusDays(1).toDate()));
        books.add(new Book("Book2", "Jessy", DateTime.now().plusDays(2).toDate()));
        books.add(new Book("Book3", "Mary", DateTime.now().plusDays(3).toDate()));

        return books;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
