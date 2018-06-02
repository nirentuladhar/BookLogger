package app.mad.com.booklogger.ui.bookinfo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.ui.usernote.UserNote;
import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.ui.home.Home;

/**
 * Displays an expanded view of a book
 * This Activity is usually launched when a user clicks on a RecyclerView Book item
 */
public class BookInfo extends AppCompatActivity implements BookInfoContract.View {

    public static final String TAG = "BOOK_LOGGER BIA";
    public static final int REQUEST_CODE = 1234;

    // constants for intent
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
    public static final String BOOK_INFO_OBJECT = "book_object";

    Book mBook;
    Intent mIntent;
    BookInfoContract.Presenter mPresenter;
    String mCurrentRef = "";

    // member variables
    String mId, mTitle, mAuthors, mImagePath, mDescription, mPageCount, mAverageRating, mRatingsCount, mUserRating, mNotes;

    // activity views
    TextView mTitleTextView() { return findViewById(R.id.book_info_title_textview); }
    TextView mAuthorTextView() { return findViewById(R.id.book_info_authors_textview);}
    TextView mDescriptionTextView() { return findViewById(R.id.book_info_description_textview);}
    ImageView mCoverImageView() { return findViewById(R.id.book_info_cover_imageview); }
    TextView mRatingsCountTextView() { return findViewById(R.id.book_info_ratings_count); }
    TextView mPageCountTextView() { return findViewById(R.id.book_info_pagecount_textview); }
    RatingBar mAverageRatingBar() { return findViewById(R.id.ratingbar_average); }
    TextView mUserRatingTextView() { return findViewById(R.id.textview_user_review_rating); }
    RatingBar mUserRatingBar() { return findViewById(R.id.rating_user_review); }
    Button mDeleteButton() { return findViewById(R.id.button_delete); }
    ImageView mCloseButton() { return findViewById(R.id.button_close); }
    RadioGroup mCurrentBookFlagRadio() { return findViewById(R.id.radiogroup_bookflag); }
    Button mWriteNoteButton() { return findViewById(R.id.button_write_a_note); }
    TextView mYourNoteTextView() { return findViewById(R.id.textview_your_note); }
    Button mEditNoteButton() { return findViewById(R.id.button_edit_your_note); }
    TextView mNotesTextView() { return findViewById(R.id.book_info_note); }
    LinearLayout mNotesContainer() { return findViewById(R.id.layout_note_container); }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

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

    /**
     * sets text to views from the Book object
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void displayBookInfo() {
        supportPostponeEnterTransition();

        // sets book metadata to the view from the book object
        setViewsText();
        mPresenter.setUserNotes();
        mPresenter.setUserRatingView();
        mPresenter.setCurrentBookList();

        // image transition animation
        String imageTransitionName = mIntent.getStringExtra(BookInfo.TRANSITION_NAME);
        mCoverImageView().setTransitionName(imageTransitionName);

        // download image with the supplied link
        Picasso.get().load(mIntent.getStringExtra(IMAGE_PATH)).noFade()
        .into(mCoverImageView(), new Callback() {
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

    void setViewsText() {
        mTitleTextView().setText(mBook.getTitle());
        mAuthorTextView().setText(mBook.getAuthors());
        mDescriptionTextView().setText(mBook.getDescription());
        if (!mBook.getAverageRating().equals("null")) mAverageRatingBar().setRating(Float.valueOf(mBook.getAverageRating()));
        mPageCountTextView().setText(mBook.getPageCount() + getString(R.string.pages_text));
        mRatingsCountTextView().setText(getString(R.string.left_bracket) + mBook.getRatingsCount() + getString(R.string.right_bracket));
        mNotesTextView().setText(mBook.getNotes());
    }


    /**
     * Receives an intent and returns a new Book object
     * @return book object
     */
    @Override
    public Book getSelectedBook() {
        // get book info from google books
        mIntent = getIntent();
        mId = mIntent.getStringExtra(ID);
        mTitle = mIntent.getStringExtra(TITLE);
        mDescription = mIntent.getStringExtra(DESCRIPTION);
        mAuthors = mIntent.getStringExtra(AUTHORS);
        mImagePath = mIntent.getStringExtra(IMAGE_PATH);
        mPageCount = mIntent.getStringExtra(PAGE_COUNT);
        mUserRating = mIntent.getStringExtra(USER_RATING);
        mAverageRating = mIntent.getStringExtra(AVERAGE_RATING);
        mRatingsCount = mIntent.getStringExtra(RATINGS_COUNT);
        mNotes = mIntent.getStringExtra(NOTE);

        mCurrentRef = mIntent.getStringExtra(CURRENT_VIEW);

        // create new book object
        mBook = new Book();
        mBook.setId(mId);
        mBook.setTitle(mTitle);
        mBook.setDescription(mDescription);
        mBook.setAuthors(mAuthors);
        mBook.setImagePath(mImagePath);
        mBook.setPageCount(mPageCount);
        mBook.setAverageRating(mAverageRating);
        mBook.setRatingsCount(mRatingsCount);
        mBook.setUserRating(mUserRating);
        mBook.setNotes(mNotes);
        return mBook;
    }

    /**
     * Returns the current book displayed in the view
     * @return currentBook
     */
    @Override
    public Book getCurrentBook() {
        return mBook;
    }


    /**
     * Returns current view reference: toread, read, completed or search
     * @return CurrentViewReference
     */
    @Override
    public String getCurrentRef() {
        return mCurrentRef;
    }

    void setClickListeners() {
        deleteListener();
        closeListener();
        bookFlagRadioListener();
        userRatingListener();
        writeNoteListener();
        editNoteListener();
    }

    /**
     * Receives a new book object from UserNote if the user saves changes
     * @param requestCode:
     * @param resultCode:
     * @param data: new Book object
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if(resultCode == BookInfo.RESULT_OK){
                String result = data.getStringExtra(UserNote.USER_NOTE_INTENT_CODE);
                Gson gson = new Gson();
                mBook = gson.fromJson(result, Book.class);
                displayBookInfo();
            }
        }
    }






    @Override
    public void displayToReadSelected() {
        mCurrentBookFlagRadio().check(R.id.radio_toread);
    }

    @Override
    public void displayReadingSelected() {
        mCurrentBookFlagRadio().check(R.id.radio_reading);
    }

    @Override
    public void displayCompletedSelected() {
        mCurrentBookFlagRadio().check(R.id.radio_completed);
    }

    @Override
    public void hideUserRating() {
        mUserRatingTextView().setVisibility(View.GONE);
        mUserRatingBar().setVisibility(View.GONE);
    }


    /**
     * Displays 'Write a Review' button and hides everything else
     */
    public void displayWriteReview() {
        mWriteNoteButton().setVisibility(View.VISIBLE);
        mYourNoteTextView().setVisibility(View.GONE);
        mNotesTextView().setVisibility(View.GONE);
        mEditNoteButton().setVisibility(View.GONE);
    }

    /**
     * Displays the note and the edit button
     * Hides 'Write a Review' button
     */
    public void displayEditReview() {
        mWriteNoteButton().setVisibility(View.GONE);
        mYourNoteTextView().setVisibility(View.VISIBLE);
        mNotesTextView().setVisibility(View.VISIBLE);
        mEditNoteButton().setVisibility(View.VISIBLE);
    }

    /**
     * Displays the note container for all activities except search
     */
    public void displayNoteContainer() {
        mNotesContainer().setVisibility(View.VISIBLE);
    }

    /**
     * Hides the note container for search activity
     */
    public void hideNoteContainer() {
        mNotesContainer().setVisibility(View.GONE);
    }


    @Override
    public void closeActivity() {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }

    @Override
    public void displayBookDeleted() {
        Toast.makeText(this, R.string.book_deleted, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayBookAdded() {
        Toast.makeText(this, R.string.book_added, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayDeleteConfirmation() {
        // deletes the book when the user says yes
        new AlertDialog.Builder(this)
                .setMessage(R.string.dialog_delete_book)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> mPresenter.deleteBook())
                .setNegativeButton(android.R.string.no, null).show();
    }

    @Override
    public void displayUserRating() {
        mUserRatingBar().setVisibility(View.VISIBLE);
        mUserRatingBar().setRating(Integer.valueOf(mBook.getUserRating()));
    }


    /**
     * Displays a delete confirmation first
     * If the user selects yes, it deletes the book
     */
    private void deleteListener() {
        mDeleteButton().setOnClickListener(v -> displayDeleteConfirmation());
    }

    /**
     * Closes this activity
     */
    private void closeListener() {
        mCloseButton().setOnClickListener(v -> finish());
    }

    /**
     * Launches a new activity where users can edit their notes
     */
    private void editNoteListener() {
        mEditNoteButton().setOnClickListener(v -> {
            Gson gson = new Gson();
            String bookAsString = gson.toJson(mBook);

            Intent i = new Intent(this, UserNote.class);
            i.putExtra(BOOK_INFO_OBJECT, bookAsString);
            startActivityForResult(i, REQUEST_CODE);
        });
    }

    /**
     * Launches a new activity where users can create a new note
     */
    private void writeNoteListener() {
        findViewById(R.id.button_write_a_note).setOnClickListener(v -> {
            Gson gson = new Gson();
            String bookAsString = gson.toJson(mBook);

            Intent i = new Intent(this, UserNote.class);
            i.putExtra(BOOK_INFO_OBJECT, bookAsString);
            startActivityForResult(i, REQUEST_CODE);
        });
    }

    /**
     * Updates book rating on click
     */
    private void userRatingListener() {
        mUserRatingBar().setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                float touchPositionX = event.getX();
                float width = mUserRatingBar().getWidth();
                float starsf = (touchPositionX / width) * 5.0f;
                int stars = (int)starsf + 1;
                // Pressing on the selected star twice resets stars to 0
                if (String.valueOf(stars).equals(mUserRating)) stars = 0;
                mPresenter.setUserRating(stars);
                mBook.setUserRating(String.valueOf(stars));
                view.setPressed(false);
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) view.setPressed(true);
            if (event.getAction() == MotionEvent.ACTION_CANCEL) view.setPressed(false);
            return true;
        });
    }

    /**
     * Checks the current view and checks the bookListFlag radio accordingly
     */
    private void bookFlagRadioListener() {
        mCurrentBookFlagRadio().setOnCheckedChangeListener((radioGroup, position) -> {
            RadioButton selectedRadioButton = findViewById(mCurrentBookFlagRadio().getCheckedRadioButtonId());
            switch (selectedRadioButton.getId()) {
                case R.id.radio_completed:
                    mCurrentRef = Home.COMPLETED_REF;
                    mPresenter.addBook();
                    break;
                case R.id.radio_reading:
                    mCurrentRef = Home.READING_REF;
                    mPresenter.addBook();
                    break;
                case R.id.radio_toread:
                    mCurrentRef = Home.TOREAD_REF;
                    mPresenter.addBook();
                    break;
            }
        });
    }

}
