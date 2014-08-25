package com.github.zzm.bushu.app.card;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.zzm.bushu.app.R;
import com.github.zzm.bushu.app.model.Book;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

public class BookDetailCard extends Card {
    private Book book;

    public BookDetailCard(Context context, Book book) {
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
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        TextView title = (TextView) view.findViewById(R.id.book_content_inner_title);
        title.setText("借:" + book.getBorrowPeople());

        TextView subtitle = (TextView) view.findViewById(R.id.book_content_inner_subtitle);
        subtitle.setText("剩" + book.returnDays() + "天还");
    }

}
