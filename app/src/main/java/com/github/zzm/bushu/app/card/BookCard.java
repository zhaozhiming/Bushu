package com.github.zzm.bushu.app.card;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.github.zzm.bushu.app.R;
import com.github.zzm.bushu.app.activity.BookDetailActivity;
import com.github.zzm.bushu.app.model.Book;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

public class BookCard extends Card {
    public final static String EXTRA_MESSAGE = "com.github.zzm.bushu.app.card.MESSAGE";
    private Book book;

    public BookCard(Context context, Book book) {
        super(context, R.layout.book_content_view);
        this.book = book;
    }

    public void init() {
        CardHeader header = new CardHeader(getContext());
        header.setTitle(book.getZhName());
        this.addCardHeader(header);

        BookThumbnail thumbnail = new BookThumbnail(getContext(), book);
        thumbnail.setExternalUsage(true);
        this.addCardThumbnail(thumbnail);

        setOnClickListener(new OnCardClickListener() {
            @Override
            public void onClick(Card card, View view) {
                Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getContext(), BookDetailActivity.class);
                intent.putExtra(EXTRA_MESSAGE, ((BookCard) card).getBook());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView title = (TextView) view.findViewById(R.id.book_content_inner_title);
        title.setText("借:" + book.getBorrowPeople());

        TextView subtitle = (TextView) view.findViewById(R.id.book_content_inner_subtitle);
        subtitle.setText("剩" + book.returnDays() + "天还");
    }

    public Book getBook() {
        return book;
    }
}
