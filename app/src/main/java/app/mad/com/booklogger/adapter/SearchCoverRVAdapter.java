package app.mad.com.booklogger.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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
        public TextView mBookTitle;
        public ImageView mBookCover;
        public TextView mBookAuthors;
        public ViewHolder(View itemView) {
            super(itemView);
            mBookCover = itemView.findViewById(R.id.imageview_book_cover);
            mBookTitle = itemView.findViewById(R.id.textview_title);
            mBookAuthors = itemView.findViewById(R.id.textview_authors);
        }
    }

    @Override
    public SearchCoverRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false);
        return new SearchCoverRVAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchCoverRVAdapter.ViewHolder holder, int position) {
        BookList.BookItem book = mBookList.get(position);
        holder.mBookTitle.setText(book.getVolumeInfo().getTitle());
        holder.mBookAuthors.setText(book.getVolumeInfo().getAuthors());
        Picasso.get().load(book.getVolumeInfo().getThumbnail()).into(holder.mBookCover);
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }
}
