package com.github.zzm.bushu.app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
import com.github.zzm.bushu.app.R;
import com.github.zzm.bushu.app.model.Book;
import com.google.common.collect.Lists;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.view.CardGridView;
import org.joda.time.DateTime;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        GridView gridView = (GridView) findViewById(R.id.gridView);
//        gridView.setAdapter(new BooksAdapter(this, getData()));
//
        CardGridView gridView = (CardGridView) findViewById(R.id.books_card_grid_view);
        gridView.setAdapter(new CardGridArrayAdapter(getApplicationContext(), createCards()));

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Card> createCards() {
        List<Card> cards = Lists.newArrayList();
        List<Book> books = getData();
        for (Book book : books) {
            Card card = new Card(getApplicationContext());
            CardHeader header = new CardHeader(getApplicationContext(), R.layout.base_header_layout);
            header.setTitle(book.getZhName());

            card.addCardHeader(header);
            cards.add(card);
        }
        return cards;
    }

    private List<Book> getData() {
        List<Book> books = Lists.newArrayList();
        books.add(new Book("http_the_definitive_guide", "HTTP权威指南", "Tom", DateTime.now().plusDays(1).toDate()));
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
