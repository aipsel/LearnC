package xyz.imyeo.learnc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.imyeo.learnc.R;

public class UserFragment extends AbsFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final String TAG = "Fragment.User";

    private TextView mTabQuestion, mTabShare, mTabDiscuss;

    private final int mTabCount = 3;

    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        mTabQuestion = (TextView) view.findViewById(R.id.tab_question);
        mTabShare = (TextView) view.findViewById(R.id.tab_share);
        mTabDiscuss = (TextView) view.findViewById(R.id.tab_discuss);
        mViewPager = (ViewPager) view.findViewById(R.id.user_view_pager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.addOnPageChangeListener(this);
        return view;
    }

    @Override
    public String getTitle() {
        return getResources().getString(R.string.drawer_user);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private static final class MyAdapter extends PagerAdapter {

        private LayoutInflater mInflater;

        private View[] mViews;

        public MyAdapter(int tabCount, LayoutInflater inflater) {
            mViews = new View[tabCount];
            mInflater = inflater;
        }

        @Override
        public int getCount() {
            return mViews.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container,position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
