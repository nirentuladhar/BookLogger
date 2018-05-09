package app.mad.com.booklogger.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.activity.MainActivity;
import app.mad.com.booklogger.model.Book;

/**
 * Created by Niren on 4/5/18.
 */

public class BookCoversAdapter extends RecyclerView.Adapter<BookCoversAdapter.ViewHolder> {

    private List<Book> mBookList;
    private Context mContext;

    public BookCoversAdapter(List<Book> bookList, Context context) {
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
    public BookCoversAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_cover_card, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BookCoversAdapter.ViewHolder holder, int position) {
        Book book = mBookList.get(position);
//        holder.mBookCoverCard.setText(book.getTitle());
        new DownloadImageTask(holder.mBookCover).execute(book.getImagePath());
    }

    @Override
    public int getItemCount() {
        return mBookList.size();
    }

    private class DownloadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownloadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        @Override
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
            Log.d(MainActivity.TAG, "Image");
        }
    }


}
