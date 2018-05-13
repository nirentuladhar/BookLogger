package app.mad.com.booklogger.model;
import com.google.api.services.books.model.Volume;
import java.util.List;


/**
 * Created by Niren on 6/5/18.
 */

public class BookList {
    String totalItems;
    List<BookItem> items;

    public BookList() {

    }

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

    /**
     * TODO: Create a Books.Volume.VolumeInfo instance
     */


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
        public String getAuthors() {
            String author = "";
            if (authors != null) {
                StringBuilder auth = new StringBuilder();
                for (String a: authors) {
                    auth.append(a).append(", ");
                }
                author = auth.substring(0, auth.length() - 2);
            }
            return author;
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
