package app.mad.com.booklogger.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import app.mad.com.booklogger.R;

public class BookInfoActivity extends AppCompatActivity implements BookInfoView {

    BookInfoPresenter mPresenter;

    TextView bookInfoTitle;
    TextView bookInfoAuthors;
    TextView bookInfoDescription;
    ImageView bookInfoImage;
    Button mToRead;
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

        mPresenter = new BookInfoPresenter();
        mPresenter.bind(this);
        mPresenter.loadBook();

        mToRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addToRead();
            }
        });



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

        bookInfoTitle.setText(mIntent.getStringExtra(SearchActivity.SEARCH_TITLE));
        bookInfoDescription.setText(mIntent.getStringExtra(SearchActivity.SEARCH_DESCRIPTION));
        bookInfoAuthors.setText(mIntent.getStringExtra(SearchActivity.SEARCH_AUTHORS));


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
