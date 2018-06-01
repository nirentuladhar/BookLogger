package app.mad.com.booklogger.ui.usernote;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class UserNote extends AppCompatActivity implements UserNoteContract.view {

    public static final String USER_NOTE_INTENT_CODE = "user_note";
    UserNoteContract.presenter mPresenter;
    EditText mUserNote;
    RatingBar mUserRating;
    Book mBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_review);

        mPresenter = new UserNotePresenter();
        mPresenter.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        Gson gson = new Gson();
        String bookAsString = intent.getStringExtra(BookInfo.BOOK_INFO_OBJECT);
        mBook = gson.fromJson(bookAsString, Book.class);

        mUserNote = findViewById(R.id.edittext_user_review_description);
        mUserNote.setText(mBook.getNotes());
        mUserRating = findViewById(R.id.rating_user_review);
        mUserRating.setRating(Float.valueOf(mBook.getUserRating()));

        mUserRating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    float touchPositionX = event.getX();
                    float width = mUserRating.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int)starsf + 1;

                    if (String.valueOf(stars).equals(mUserRating)) stars = 0;
                    mUserRating.setRating(stars);
                    mBook.setUserRating(String.valueOf(stars));
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
                onBackPressed();
                return true;
            case R.id.action_done:
                mBook.setNotes(mUserNote.getText().toString());
                mPresenter.saveNote();

//                mPresenter.saveNote();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public String getNote() {
        return null;
    }

    @Override
    public void displaySaved() {
        Gson gson = new Gson();
        String bookAsString = gson.toJson(mBook);
        Intent returnIntent = new Intent();
        returnIntent.putExtra(USER_NOTE_INTENT_CODE , bookAsString);
        setResult(BookInfo.RESULT_OK, returnIntent);
        finish();
//        finish();
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Book getBook() {
        return mBook;
    }
}
