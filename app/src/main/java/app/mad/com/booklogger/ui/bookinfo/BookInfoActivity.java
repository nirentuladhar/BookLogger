package app.mad.com.booklogger.ui.bookinfo;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.ui.userreview.UserReview;
import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.ui.home.HomeActivity;

public class BookInfoActivity extends AppCompatActivity implements BookInfoContract.View {

    public static final String TAG = "BOOK_LOGGER BIA";

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String AUTHORS = "authors";
    public static final String IMAGE_PATH = "cover";
    public static final String DESCRIPTION = "description";

    public static final String PAGE_COUNT = "page_count";
    public static final String USER_RATING = "user_rating";
    public static final String AVERAGE_RATING = "average_rating";
    public static final String RATINGS_COUNT = "ratings_count";
    public static final String CURRENT_VIEW= "current_view";
    public static final String TRANSITION_NAME = "transitionSearchToBookInfo";
    public static final String NOTE ="note";

    public static final int REQUEST_CODE = 1234;
    public static final String BOOK_INFO_OBJECT = "book_object";
    BookInfoContract.Presenter mPresenter;

    TextView bookInfoTitle;
    TextView bookInfoAuthors;
    TextView bookInfoDescription;
    ImageView bookInfoImage;
    TextView bookInfoRatingsCount;
    TextView bookInfoMetadata;
    RatingBar mbookInfoAvgRating;
    RatingBar mbookInfoUserRating;
    TextView mBookInfoNotes;
    Button mDelete;
    Book book;

    String mId;
    String mTitle;
    String mAuthors;
    String mImagePath;
    String mDescription;
    String mPageCount;
    String mAverageRating;
    String mRatingsCount;
    String mUserRating;
    String mNotes;


    ImageView mCloseButton;
    Intent mIntent;

    String mCurrentRef = "";

    RadioGroup mBookFlag;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        bookInfoTitle = findViewById(R.id.book_info_title_textview);
        bookInfoAuthors = findViewById(R.id.book_info_authors_textview);
        bookInfoDescription = findViewById(R.id.book_info_description_textview);
        bookInfoImage = findViewById(R.id.book_info_cover_imageview);
        bookInfoRatingsCount = findViewById(R.id.book_info_ratings_count);
        bookInfoMetadata = findViewById(R.id.book_info_metadata_textview);
        mbookInfoUserRating = findViewById(R.id.rating_user_review);
        mBookFlag = findViewById(R.id.radiogroup_bookflag);
        mCloseButton = findViewById(R.id.button_close);
        mbookInfoAvgRating = findViewById(R.id.ratingbar_average);
        mDelete = findViewById(R.id.button_delete);
        mBookInfoNotes = findViewById(R.id.book_info_notes);

        mPresenter = new BookInfoPresenter();
        mPresenter.bind(this);
        mPresenter.loadBook();

        setClickListeners();
    }

    @Override
    protected void onDestroy() {
        mPresenter.unbind();
        super.onDestroy();
    }

    @Override
    public void displayBookInfo() {
        supportPostponeEnterTransition();
        if (book != null) {

            // sets book metadata to the view from the given intent
            bookInfoTitle.setText(book.getTitle());
            bookInfoAuthors.setText(book.getAuthors());
            bookInfoDescription.setText(book.getDescription());
//            mbookInfoUserRating.setRating(Float.valueOf(book.getUserRating()));
            if (!book.getAverageRating().equals("null")) {
                mbookInfoAvgRating.setRating(Float.valueOf(book.getAverageRating()));
            }
            if (!book.getPageCount().equals(null)) {
                bookInfoMetadata.setText(book.getPageCount() + " pages");
            } else {
                bookInfoMetadata.setVisibility(View.GONE);
            }
            if (!book.getRatingsCount().equals(null)) {
                bookInfoRatingsCount.setText("(" + book.getRatingsCount() + ")");
            } else {
                bookInfoRatingsCount.setVisibility(View.GONE);
            }
            mBookInfoNotes.setText(book.getNotes());
            /**
             * todo add the literal string to the xml file
             */


            // checks what the current view is
            // and selects the button
            switch (mCurrentRef) {
                case HomeActivity.TOREAD_REF:
                    mBookFlag.check(R.id.radio_toread);
                    break;
                case HomeActivity.READING_REF:
                    mBookFlag.check(R.id.radio_reading);
                    break;
                case HomeActivity.COMPLETED_REF:
                    mBookFlag.check(R.id.radio_completed);
                    break;
            }




            // image transition animation
            String imageTransitionName = mIntent.getStringExtra(BookInfoActivity.TRANSITION_NAME);
            bookInfoImage.setTransitionName(imageTransitionName);


            // download image with the supplied link
            Picasso.get()
                    .load(mIntent.getStringExtra(IMAGE_PATH))
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
        } else {
            Log.e(TAG, "Current book not set.");
        }

    }

    @Override
    public void closeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public Book getCurrentBook() {

        // get book info from google books
        mIntent = getIntent();
//        mId = mIntent.getStringExtra(ID);
//        mTitle = mIntent.getStringExtra(TITLE);
//        mDescription = mIntent.getStringExtra(DESCRIPTION);
//        mAuthors = mIntent.getStringExtra(AUTHORS);
//        mImagePath = mIntent.getStringExtra(IMAGE_PATH);
//        mPageCount = mIntent.getStringExtra(PAGE_COUNT);
//        mUserRating = mIntent.getStringExtra(USER_RATING);
//        mAverageRating = mIntent.getStringExtra(AVERAGE_RATING);
//        mRatingsCount = mIntent.getStringExtra(RATINGS_COUNT);
//        mNotes = mIntent.getStringExtra(NOTE);

        Gson gson = new Gson();

        book = gson.fromJson(mIntent.getStringExtra("intent"), Book.class);

        if (mCurrentRef != null) {
            mCurrentRef = mIntent.getStringExtra(CURRENT_VIEW);
            if (mCurrentRef.equals("search")) {
                mDelete.setVisibility(View.GONE);
                findViewById(R.id.textview_user_review_rating).setVisibility(View.GONE);
                mbookInfoUserRating.setVisibility(View.GONE);
            }
        }


        // create new book object
//        book = new Book();
//        book.setId(mId);
//        book.setTitle(mTitle);
//        book.setDescription(mDescription);
//        book.setAuthors(mAuthors);
//        book.setImagePath(mImagePath);
//        book.setPageCount(mPageCount);
//        book.setAverageRating(mAverageRating);
//        book.setRatingsCount(mRatingsCount);
//        book.setUserRating(mUserRating);
//        book.setNotes(mNotes);

        return book;
    }

    @Override
    public String getCurrentRef() {
        return mCurrentRef;
    }

    @SuppressLint("ClickableViewAccessibility")
    void setClickListeners() {

        mBookFlag.setOnCheckedChangeListener((radioGroup, position) -> {
            RadioButton selectedRadioButton = findViewById(mBookFlag.getCheckedRadioButtonId());

            switch (selectedRadioButton.getId()) {
                case R.id.radio_completed:
                    mCurrentRef = HomeActivity.COMPLETED_REF;
                    mPresenter.addBook();
                    break;
                case R.id.radio_reading:
                    mCurrentRef = HomeActivity.READING_REF;
                    mPresenter.addBook();
                    break;
                case R.id.radio_toread:
                    mCurrentRef = HomeActivity.TOREAD_REF;
                    mPresenter.addBook();
                    break;
            }
        });

        mDelete.setOnClickListener(v -> displayDeleteConfirmation());


        /**
         * todo make the views constant
         */

        mCloseButton.setOnClickListener(v -> finish());

        /**
         * TODO post the user rating to firebase
         */
        mbookInfoUserRating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchPositionX = event.getX();
                    float width = mbookInfoUserRating.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int)starsf + 1;


                    if (String.valueOf(stars).equals(mUserRating)) stars = 0;
                    mPresenter.setUserRating(stars);
                    view.setPressed(false);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    view.setPressed(true);
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    view.setPressed(false);
                }
                return true;
            }
        });

        findViewById(R.id.button_write_a_note).setOnClickListener(v -> {
            Gson gson = new Gson();
            String bookAsString = gson.toJson(book);

            Intent i = new Intent(this, UserReview.class);
            i.putExtra(BOOK_INFO_OBJECT, bookAsString);
            startActivityForResult(i, REQUEST_CODE);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if(resultCode == BookInfoActivity.RESULT_OK){
                String result = data.getStringExtra("result");
                Gson gson = new Gson();
                book = gson.fromJson(result, Book.class);
                displayBookInfo();
//                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == BookInfoActivity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void displayBookDeleted() {
        Toast.makeText(this, "Book deleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayBookAdded() {
        Toast.makeText(this, "Book added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setMessage("Do you want to delete this book?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        mPresenter.deleteBook();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void displayUserRating() {
        mbookInfoUserRating.setRating(Integer.valueOf(book.getUserRating()));
    }


}
