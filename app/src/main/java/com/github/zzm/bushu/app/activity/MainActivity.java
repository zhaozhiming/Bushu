package com.github.zzm.bushu.app.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.Toast;
import com.github.zzm.bushu.app.R;
import com.github.zzm.bushu.app.adapter.BooksAdapter;
import com.github.zzm.bushu.app.model.Book;
import com.github.zzm.bushu.app.model.LogTag;
import com.google.common.collect.Lists;
import com.squareup.picasso.Picasso;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardGridArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
import it.gmariotti.cardslib.library.view.CardGridView;
import org.joda.time.DateTime;

import java.util.List;

import static java.lang.String.format;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        for (final Book book : books) {
            Card card = new Card(getApplicationContext());

            CardHeader header = new CardHeader(getApplicationContext());
            header.setTitle(book.getZhName());
            card.addCardHeader(header);

            String url = format("%s%s/%s.png", BooksAdapter.STORAGE_BASE_URL,
                    new BooksAdapter(getApplication(), null).getScreenDensity(), book.getEnName());
            Log.d(LogTag.MainActivity.name(), "url:" + url);

            BookThumbnail thumbnail = new BookThumbnail(getApplicationContext(), url);
            thumbnail.setExternalUsage(true);
            card.addCardThumbnail(thumbnail);

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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return item.getItemId() == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    class BookThumbnail extends CardThumbnail {
        private String url;

        public BookThumbnail(Context context, String url) {
            super(context);
            this.url = url;
        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View viewImage) {
            Picasso.with(getContext()).load(url).into((ImageView) viewImage);
        }
    }
}

