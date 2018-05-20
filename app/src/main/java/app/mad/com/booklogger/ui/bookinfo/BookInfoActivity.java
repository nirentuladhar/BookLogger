package app.mad.com.booklogger.ui.bookinfo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.ui.search.SearchActivity;

public class BookInfoActivity extends AppCompatActivity implements BookInfoContract.View {

    BookInfoPresenter mPresenter;

    TextView bookInfoTitle;
    TextView bookInfoAuthors;
    TextView bookInfoDescription;
    ImageView bookInfoImage;

    Book book;

    String mId;
    String mTitle;
    String mAuthors;
    String mImagePath;
    String mDescription;
    String mPageCount;
    String mAverageRating;
    String mRatingsCount;

    Button mToRead;
    Button mCompleted;
    Button mReading;
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        bookInfoTitle = findViewById(R.id.book_info_title_textview);
        bookInfoAuthors = findViewById(R.id.book_info_authors_textview);
        bookInfoDescription = findViewById(R.id.book_info_description_textview);
        bookInfoImage = findViewById(R.id.book_info_cover_imageview);
        mToRead = findViewById(R.id.button_to_read);
        mCompleted = findViewById(R.id.button_completed);
        mReading = findViewById(R.id.button_reading);

        mPresenter = new BookInfoPresenter();
        mPresenter.bind(this);
        mPresenter.loadBook();

        mToRead.setOnClickListener(v -> mPresenter.addBookToRead(book));
        mCompleted.setOnClickListener(v -> mPresenter.addBookToCompleted(book));
        mReading.setOnClickListener(v -> mPresenter.addBookToReading(book));
    }

    @Override
    protected void onDestroy() {
        mPresenter.unbind();
        super.onDestroy();
    }

    @Override
    public void displayBookInfo() {
        supportPostponeEnterTransition();

        mIntent = getIntent();

        mId = mIntent.getStringExtra(SearchActivity.SEARCH_ID);
        mTitle = mIntent.getStringExtra(SearchActivity.SEARCH_TITLE);
        mDescription = mIntent.getStringExtra(SearchActivity.SEARCH_DESCRIPTION);
        mAuthors = mIntent.getStringExtra(SearchActivity.SEARCH_AUTHORS);
        mImagePath = mIntent.getStringExtra(SearchActivity.SEARCH_COVER);
        mPageCount = mIntent.getStringExtra(SearchActivity.SEARCH_PAGE_COUNT);
        mAverageRating = mIntent.getStringExtra(SearchActivity.SEARCH_AVERAGE_RATING);
        mRatingsCount = mIntent.getStringExtra(SearchActivity.SEARCH_RATINGS_COUNT);

        book = new Book();

        book.setId(mId);
        book.setTitle(mTitle);
        book.setDescription(mDescription);
        book.setAuthors(mAuthors);
        book.setImagePath(mImagePath);
        book.setPageCount(mPageCount);
        book.setAverageRating(mAverageRating);
        book.setRatingsCount(mRatingsCount);

        bookInfoTitle.setText(mTitle);
        bookInfoAuthors.setText(mAuthors);
        bookInfoDescription.setText(mDescription);

        String imageTransitionName = mIntent.getStringExtra(SearchActivity.TRANSITION_NAME);
        bookInfoImage.setTransitionName(imageTransitionName);

        Picasso.get()
                .load(mIntent.getStringExtra(SearchActivity.SEARCH_COVER))
                .noFade()
                .into(bookInfoImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError(Exception e) {
                        supportStartPostponedEnterTransition();
                    }
                });

    }
}
