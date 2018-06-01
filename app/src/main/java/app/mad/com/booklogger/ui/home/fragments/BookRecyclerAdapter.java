package app.mad.com.booklogger.ui.home.fragments;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.model.Book;

/**
 * Recycler Adapter class specifically for Book object. Used in home activity & fragments
 */
public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.ViewHolder>{

    private List<Book> mBook;
    private OnRowClickListener mListener;

    BookRecyclerAdapter(List<Book> book) {
        this.mBook = book;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mBookTitle;
        ImageView mBookCover;
        TextView mBookAuthors;
        LinearLayout mBookContainer;
        ViewHolder(View itemView) {
            super(itemView);
            mBookCover = itemView.findViewById(R.id.imageview_book_cover);
            mBookTitle = itemView.findViewById(R.id.textview_title);
            mBookAuthors = itemView.findViewById(R.id.textview_authors);
            mBookContainer = itemView.findViewById(R.id.layout_book_container);
        }
    }

    @NonNull
    @Override
    public BookRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card, parent, false);
        return new BookRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookRecyclerAdapter.ViewHolder holder, int position) {
        Book book = mBook.get(position);
        holder.mBookTitle.setText(book.getTitle());
        holder.mBookAuthors.setText(book.getAuthors());
        holder.mBookContainer.setOnClickListener(v -> {
            Book bookItem = getBookItem(holder.getAdapterPosition());
            mListener.onRowClick(bookItem, holder.mBookCover);
        });
        // downloads image into the holder
        Picasso.get().load(book.getImagePath()).into(holder.mBookCover);

        // image transition
        ViewCompat.setTransitionName(holder.mBookCover, book.getTitle());
    }

    void setOnRowClickListener(OnRowClickListener listener) {
        mListener = listener;
    }

    public interface OnRowClickListener {
        void onRowClick(Book bookItem, ImageView cover);
    }

    private Book getBookItem(int position) {
        return mBook.get(position);
    }

    @Override
    public int getItemCount() {
        return mBook.size();
    }
}
