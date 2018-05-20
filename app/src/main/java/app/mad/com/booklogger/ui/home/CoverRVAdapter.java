package app.mad.com.booklogger.ui.home;

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

/**
 * Created by Niren on 4/5/18.
 */

public class CoverRVAdapter extends RecyclerView.Adapter<CoverRVAdapter.ViewHolder> {

    private List<Book> mBookList;
    private Context mContext;

    public CoverRVAdapter(List<Book> bookList, Context context) {
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
    public CoverRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_cover_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CoverRVAdapter.ViewHolder holder, int position) {
        Book book = mBookList.get(position);
        Picasso.get().load(book.getImagePath()).into(holder.mBookCover);
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }


}
