package app.mad.com.booklogger.model;
import com.google.api.services.books.model.Volume;
import java.util.List;


/**
 * Model for Google Books
 */

public class BookList {
    private String totalItems;
    private List<BookItem> items;

    public BookList() {}

    public String getTotalItems () {
        return totalItems;
    }
    public List<BookItem> getItems () {
        return items;
    }

    /**
     * Inner class of BookList
     */
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
     * Inner class of BookItem
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
            // returns a formatted string from the provided list of authors
            String authors = "";
            if (this.authors != null) {
                StringBuilder auth = new StringBuilder();
                for (String author: this.authors) {
                    auth.append(author).append(", ");
                }
                authors = auth.substring(0, auth.length() - 2);
            }
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
            // attempts to get the path for the highest resolution
            // falls back if not available
            if (imageLinks != null) {
                String image = null;
                if (imageLinks.getExtraLarge() != null) {
                    image = imageLinks.getExtraLarge();
                } else if (imageLinks.getLarge() != null) {
                    image = imageLinks.getLarge();
                } else if (imageLinks.getMedium() != null) {
                    image = imageLinks.getMedium();
                } else if (imageLinks.getSmall() != null) {
                    image = imageLinks.getSmall();
                } else if (imageLinks.getThumbnail() != null) {
                    image = imageLinks.getThumbnail();
                } else if (imageLinks.getSmallThumbnail() != null) {
                    image = imageLinks.getSmallThumbnail();
                }
                if (image != null) {
                    return image;
                }
            }
            return null;
        }
    }
}
