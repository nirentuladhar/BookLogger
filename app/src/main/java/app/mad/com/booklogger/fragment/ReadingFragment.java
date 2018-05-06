package app.mad.com.booklogger.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.adapter.BookCoversAdapter;
import app.mad.com.booklogger.model.Book;


public class ReadingFragment extends Fragment{

    private RecyclerView mRecyclerView;
    private BookCoversAdapter mAdapter;

    private ArrayList<Book> mBookArrayList = new ArrayList<>();

    public ReadingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookFactory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reading, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);

        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new BookCoversAdapter(mBookArrayList, getActivity());
        mRecyclerView.setAdapter(mAdapter);
        super.onViewCreated(view, savedInstanceState);
    }

    public void bookFactory() {
        Book book;
        book = new Book("Harry Potter", "JK Rowling", "http://books.google.com/books/content?id=kxsQhM4D8uIC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api");
        mBookArrayList.add(book);
        book = new Book("Lord of The Rings", "JRR Tolkein", "http://books.google.com/books/content?id=gqDf__ULmR8C&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api");
        mBookArrayList.add(book);
    }

}