package app.mad.com.booklogger.model;

/**
 * Created by Niren on 4/5/18.
 */

public class Book {
    private String mName;
    private String mAuthor;
    private String mImagePath;

    public Book(){
    };

    public Book(String name, String author, String imagePath) {
        mName = name;
        mAuthor = author;
        mImagePath = imagePath;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }
}
