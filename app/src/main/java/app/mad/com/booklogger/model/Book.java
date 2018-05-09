package app.mad.com.booklogger.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Niren on 4/5/18.
 */

public class Book {

    private String mId;

    @SerializedName("title")
    String mTitle;
    private String mAuthor;
    private String mImagePath;
    private String mSynopsis;

    private String mNotes;
    private int mRating = 0;

    private boolean mIsReading = false;
    private boolean mToRead = true;
    private boolean mHasCompleted =false;

    private String mDateModified;
    private String mDateAdded;


    public Book(){
    }

    public Book(String name, String author, String imagePath) {
        mTitle = name;
        mAuthor = author;
        mImagePath = imagePath;
    }

    public Book(String id, String name, String author, String imagePath, String synopsis,
                String notes, int rating,
                boolean isReading, boolean toRead, boolean hasCompleted,
                String dateModified, String dateAdded) {
        mId = id;
        mTitle = name;
        mAuthor = author;
        mImagePath = imagePath;
        mSynopsis = synopsis;

        mNotes = notes;
        mRating = rating;

        mIsReading = isReading;
        mToRead = toRead;
        mHasCompleted = hasCompleted;

        mDateModified = dateModified;
        mDateAdded = dateAdded;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String synopsis) {
        mSynopsis = synopsis;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public boolean isReading() {
        return mIsReading;
    }

    public void setReading(boolean reading) {
        mIsReading = reading;
    }

    public boolean isToRead() {
        return mToRead;
    }

    public void setToRead(boolean toRead) {
        mToRead = toRead;
    }

    public boolean isHasCompleted() {
        return mHasCompleted;
    }

    public void setHasCompleted(boolean hasCompleted) {
        mHasCompleted = hasCompleted;
    }

    public String getDateModified() {
        return mDateModified;
    }

    public void setDateModified(String dateModified) {
        mDateModified = dateModified;
    }

    public String getDateAdded() {
        return mDateAdded;
    }

    public void setDateAdded(String dateAdded) {
        mDateAdded = dateAdded;
    }
}
