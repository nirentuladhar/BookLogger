package app.mad.com.booklogger.activity;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import app.mad.com.booklogger.model.BookList;
import app.mad.com.booklogger.repositories.BooksRepository;
import retrofit2.Call;

import static org.junit.Assert.*;

/**
 * Created by Niren on 12/5/18.
 */
public class SearchActivityPresenterTest {

    @Test
    public void shouldPass() {
        Assert.assertEquals(1, 1);
    }

//    @Test
//    public void shouldPassBooksToView() {
//        // given
//        SearchActivityView view = new MockView();
//        BooksRepository booksRepository = new MockBookRepository(true);
//        // when
//        SearchActivityPresenter presenter = new SearchActivityPresenter(view, booksRepository);
//        presenter.loadBooks();
//        // then
//        Assert.assertEquals(true, ((MockView) view).displayBooksWithBooksCalled);
//    }
//
//    @Test
//    public void shouldHandleNoBooks() {
//        SearchActivityView view = new MockView();
//        BooksRepository booksRepository = new MockBookRepository(false);
//
//        SearchActivityPresenter presenter = new SearchActivityPresenter(view, booksRepository);
//        presenter.loadBooks();
//
//        Assert.assertEquals(true, ((MockView) view).displayBooksWithNoBooksCalled);
//    }
//
//    private class MockView implements SearchActivityView {
//        boolean displayBooksWithBooksCalled;
//        boolean displayBooksWithNoBooksCalled;
//
//        @Override
//        public void displayBooks(List<BookList> bookLists) {
//            if (bookLists.size() == 3) displayBooksWithBooksCalled = true;
//        }
//
//        @Override
//        public void displayNoBooks() {
//            displayBooksWithNoBooksCalled = true;
//        }
//    }
//
//    private class MockBookRepository implements BooksRepository {
//        private boolean returnSomeBooks;
//
//        public MockBookRepository(Boolean returnSomeBooks) {
//            this.returnSomeBooks = returnSomeBooks;
//        }
//
//        @Override
//        public List<BookList> getBooks() {
//            if (returnSomeBooks) {
//                return Arrays.asList(new BookList(), new BookList(), new BookList());
//            } else {
//                return Collections.emptyList();
//            }
//        }
//    }

}