package com.github.zzm.bushu.app.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.github.zzm.bushu.app.R;
import com.github.zzm.bushu.app.card.BookCard;
import com.github.zzm.bushu.app.card.BookDetailCard;
import com.github.zzm.bushu.app.model.Book;
import com.github.zzm.bushu.app.model.LogTag;
import it.gmariotti.cardslib.library.view.CardView;

public class BookDetailActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_detail_main_view);
        Book book = (Book) getIntent().getSerializableExtra(BookCard.EXTRA_MESSAGE);
        Log.d(LogTag.BookDetailActivity.name(), "book name:" + book.getEnName());

        BookDetailCard card = new BookDetailCard(getApplicationContext(), book);
        card.init();

        CardView cardView = (CardView) findViewById(R.id.book_detail_card_view);
        cardView.setCard(card);
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

}
