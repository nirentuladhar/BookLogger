package app.mad.com.booklogger.activity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import app.mad.com.booklogger.model.BookList;
import app.mad.com.booklogger.service.GoogleBooksService;

import static org.junit.Assert.*;

/**
 * Created by Niren on 12/5/18.
 */

@RunWith(MockitoJUnitRunner.class)
public class SearchActivityPresenterTest {

    @Test
    public void shouldPass() {
        Assert.assertEquals(1, 1);
    }

//    @Mock
//    GoogleBooksService googleBooksService;
//
//    @Test
//    public void shouldPassBooksToView() {
////        SearchActivityView view = new MockView();
//        Mockito.when(googleBooksService.getBooks("hello"))
//                .thenReturn(Arrays.asList(new List<BookList()>, new BookList() ));
//    }


}