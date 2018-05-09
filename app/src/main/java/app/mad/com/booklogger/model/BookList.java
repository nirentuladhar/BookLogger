package app.mad.com.booklogger.model;
import com.google.api.services.books.model.Volume;
import java.util.List;


/**
 * Created by Niren on 6/5/18.
 */

public class BookList {
    String totalItems;
    List<BookItem> items;

    public String getTotalItems () {
        return totalItems;
    }
    public List<BookItem> getItems () {
        return items;
    }


    public class BookItem {
        String id;
        VolumeInfo volumeInfo;

        public String getId() {
            return id;
        }
        public VolumeInfo getVolumeInfo() {
            return volumeInfo;
        }
    }


    public class VolumeInfo {
        String title;
        List<String> authors;
        String description;
        int pageCount;
        float averageRating;
        int ratingsCount;
        Volume.VolumeInfo.ImageLinks imageLinks;

        public String getTitle() {
            return title;
        }
        public List<String> getAuthors() {
            return authors;
        }
        public String getDescription() {
            return description;
        }
        public int getPageCount() {
            return pageCount;
        }
        public float getAverageRating() {
            return averageRating;
        }
        public int getRatingsCount() {
            return ratingsCount;
        }
        public String getThumbnail() {
            return imageLinks.getThumbnail();
        }
    }
}
