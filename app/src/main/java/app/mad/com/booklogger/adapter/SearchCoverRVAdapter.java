package app.mad.com.booklogger.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.model.BookList;

/**
 * Created by Niren on 11/5/18.
 */

public class SearchCoverRVAdapter extends RecyclerView.Adapter<SearchCoverRVAdapter.ViewHolder>{

    private List<BookList.BookItem> mBookList;
    private Context mContext;

    public SearchCoverRVAdapter(List<BookList.BookItem> bookList, Context context) {
        this.mBookList = bookList;
        this.mContext = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mBookCoverCard;
        public ImageView mBookCover;
        public ViewHolder(View itemView) {
            super(itemView);
//            mBookCoverCard = itemView.findViewById(R.id.text_book_cover_card);
            mBookCover = itemView.findViewById(R.id.imageview_book_cover);
        }
    }

    @Override
    public SearchCoverRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_cover_card, parent, false);
        return new SearchCoverRVAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchCoverRVAdapter.ViewHolder holder, int position) {
        BookList.BookItem book = mBookList.get(position);
        Picasso.get().load(book.getVolumeInfo().getThumbnail()).into(holder.mBookCover);
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }
}
