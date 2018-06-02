package app.mad.com.booklogger.ui.usernote;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.Gson;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.model.Book;
import app.mad.com.booklogger.ui.bookinfo.BookInfo;

/**
 * Activity to display, edit and save user notes and rating
 */
public class UserNote extends AppCompatActivity implements UserNoteContract.view {

    public static final String TAG = "BL " + UserNote.class.getName();
    public static final String USER_NOTE_INTENT_CODE = "user_note";
    EditText mUserNote() { return findViewById(R.id.edittext_user_review_description); }
    RatingBar mUserRating() { return findViewById(R.id.rating_user_review); }

    Book mBook;
    UserNoteContract.presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review);

        mPresenter = new UserNotePresenter();
        mPresenter.bind(this);

        // set up action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        // sets current note and rating
        setUserNoteAndRating();

        setRatingListener();
    }

    private void setRatingListener() {
        mUserRating().setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                float touchPositionX = event.getX();
                float width = mUserRating().getWidth();
                float starsf = (touchPositionX / width) * 5.0f;
                int stars = (int)starsf + 1;

                if (String.valueOf(stars).equals(mUserRating())) stars = 0;
                mUserRating().setRating(stars);
                mBook.setUserRating(String.valueOf(stars));
                view.setPressed(false);
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) view.setPressed(true);
            if (event.getAction() == MotionEvent.ACTION_CANCEL) view.setPressed(false);
            return true;
        });
    }

    /**
     * Gets the book object from an intent
     * Sets the current note and rating
     */
    private void setUserNoteAndRating() {
        Intent intent = getIntent();
        Gson gson = new Gson();
        String bookAsString = intent.getStringExtra(BookInfo.BOOK_INFO_OBJECT);
        mBook = gson.fromJson(bookAsString, Book.class);

        String userNote = mBook.getNotes();
        if (userNote.equals("null")) {
            mUserNote().setText("");
        } else {
            mUserNote().setText(userNote);
        }
        mUserRating().setRating(Float.valueOf(mBook.getUserRating()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.unbind();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_review, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                Log.d(TAG, "Back button pressed");
                onBackPressed();
                return true;
            case R.id.action_done:
                mBook.setNotes(mUserNote().getText().toString());
                mPresenter.saveNote();
                Log.d(TAG, "Note saved");
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * If the user saves the note, returns the new book object through an intent
     * And, closes the current activty
     */
    @Override
    public void displaySaved() {
        Gson gson = new Gson();
        String bookAsString = gson.toJson(mBook);
        Intent returnIntent = new Intent();
        returnIntent.putExtra(USER_NOTE_INTENT_CODE , bookAsString);
        setResult(BookInfo.RESULT_OK, returnIntent);
        Log.d(TAG,"Note saved and return intent");
        finish();
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Book getBook() {
        return mBook;
    }
}
