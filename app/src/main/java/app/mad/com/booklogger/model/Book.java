package app.mad.com.booklogger.model;

/**
 * Model for Firebase
 */
public class Book {

    private String mId;
    private String mTitle;
    private String mAuthors;
    private String mDescription;
    private String mPageCount;
    private String mAverageRating;
    private String mRatingsCount;
    private String mImagePath;
    // custom fields
    private String mNotes;
    private String mUserRating = "0";

    public Book() {
    }

    public Book(String title, String authors, String imagePath) {
        mTitle = title;
        mAuthors = authors;
        mImagePath = imagePath;
    }

    public Book(String id, String title, String authors, String imagePath, String description,
                String pageCount, String averageRating, String ratingsCount,
                String notes, String userRating) {
        mId = id;
        mTitle = title;
        mAuthors = authors;
        mDescription = description;
        mPageCount = pageCount;
        mAverageRating = averageRating;
        mRatingsCount = ratingsCount;
        mImagePath = imagePath;

        mNotes = notes;
        mUserRating = userRating;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthors() {
        return mAuthors;
    }

    public void setAuthors(String authors) {
        mAuthors = authors;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPageCount() {
        return mPageCount;
    }

    public void setPageCount(String pageCount) {
        mPageCount = pageCount;
    }

    public String getAverageRating() {
        return mAverageRating;
    }

    public void setAverageRating(String averageRating) {
        mAverageRating = averageRating;
    }

    public String getRatingsCount() {
        return mRatingsCount;
    }

    public void setRatingsCount(String ratingsCount) {
        mRatingsCount = ratingsCount;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        mImagePath = imagePath;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public void setUserRating(String userRating) {
        mUserRating = userRating;
    }
}
