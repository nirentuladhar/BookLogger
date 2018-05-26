package app.mad.com.booklogger.ui.home.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import app.mad.com.booklogger.R;
import app.mad.com.booklogger.model.BookList;

/**
 * Created by Niren on 18/5/18.
 */

public class HomeFragmentAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();
    private OnRowClickListener mListener;
    private Context mContext;
    private String tabTitles[] = new String[] { "Reading", "To Read", "Completed" };
    private int[] imageResId = {
            R.drawable.ic_reading_white,
            R.drawable.ic_to_read_white,
            R.drawable.ic_completed_white};

    public HomeFragmentAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position

        Drawable image = mContext.getResources().getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        // Replace blank spaces with image icon
        SpannableString sb = new SpannableString("   " + tabTitles[position]);
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    void setOnRowClickListener(OnRowClickListener listener) {
        mListener = listener;
    }

    public interface OnRowClickListener {
        void onRowClick(BookList.BookItem bookItem, ImageView cover);
    }
}
