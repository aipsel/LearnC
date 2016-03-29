package xyz.imyeo.learnc.fragment;

import android.animation.ArgbEvaluator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import xyz.imyeo.learnc.R;
import xyz.imyeo.learnc.model.Conversation;
import xyz.imyeo.learnc.widget.CircleImageView;
import xyz.imyeo.learnc.widget.FlowTagView;

public class UserFragment extends AbsFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    public static final String TAG = "Fragment.User";

    private TextView mTabQuestion, mTabShare, mTabDiscuss;

    private final int mTabCount = 3;

    private TextView mTabs[] = new TextView[mTabCount];

    private ArgbValueEvaluator mTabColorEvaluator;

    private LayoutInflater mInflater;

    private ViewPager mViewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        mTabs[0] = (TextView) view.findViewById(R.id.tab_question);
        mTabs[1] = (TextView) view.findViewById(R.id.tab_share);
        mTabs[2] = (TextView) view.findViewById(R.id.tab_discuss);
        mTabColorEvaluator = new ArgbValueEvaluator();
        mViewPager = (ViewPager) view.findViewById(R.id.user_view_pager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new ViewPagerAdapter());
        mViewPager.addOnPageChangeListener(this);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_user, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
        if (positionOffsetPixels != 0) {
            mTabs[position].setTextColor(mTabColorEvaluator.evaluate(positionOffset));
            mTabs[position + 1].setTextColor(mTabColorEvaluator.evaluate(1 - positionOffset));
        }
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private final class ArgbValueEvaluator {
        int from, to;
        ArgbEvaluator mEvaluator = new ArgbEvaluator();

        public ArgbValueEvaluator() {
            from = getResources().getColor(R.color.colorAccent);
            to = Color.WHITE;
        }

        int evaluate(float fraction) {
            return (int) mEvaluator.evaluate(fraction, from, to);
        }
    }

    private final class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mTabCount;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mInflater.inflate(R.layout.layout_user_tab_discuss, container, false);
            ListView listView = (ListView) view.findViewById(R.id.list_view);
            listView.setAdapter(new ListViewAdapter(position + 1));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    private final class ListViewAdapter extends BaseAdapter {

        private final int mType;

        private final int mItemPerFetch = 20;

        private List<Conversation> mData = new ArrayList<>();

        private boolean mIsFetchedAll = false;

        public ListViewAdapter(int type) {
            mType = type;
            fetchMore();
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Conversation getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.layout_discussion_list_item, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Conversation c = getItem(position);
            holder.userIcon.setImageResource(R.drawable.user_icon);
            holder.title.setText(c.getTitle());
            holder.tags.setTags(c.getTags());
            holder.totalComments.setText(String.valueOf(c.getCommentNumber()));
            holder.totalViews.setText(String.valueOf(c.getViews()));
            if (position + 1 == getCount()) {
                fetchMore();
            }
            return convertView;
        }

        private void fetchMore() {
            if (!mIsFetchedAll) {
                ParseQuery.getQuery(Conversation.class)
                        .whereEqualTo("creator", ParseUser.getCurrentUser())
                        .whereEqualTo("type", mType)
                        .setLimit(mItemPerFetch)
                        .setSkip(mData.size())
                        .findInBackground(new FindCallback<Conversation>() {
                            @Override
                            public void done(List<Conversation> result, ParseException e) {
                                if (e != null) {
                                    Log.e(TAG, "[done] error:" + e.getCode() + "," + e.getMessage());
                                } else {
                                    if (result.size() < mItemPerFetch) {
                                        mIsFetchedAll = true;
                                    }
                                    mData.addAll(result);
                                    notifyDataSetChanged();
                                }
                            }
                        });
            }
        }
    }

    private final class ViewHolder {

        CircleImageView userIcon;
        TextView totalViews;
        TextView totalComments;
        TextView title;
        FlowTagView tags;

        public ViewHolder(View view) {
            this.userIcon = (CircleImageView) view.findViewById(R.id.user_icon);
            totalViews = (TextView) view.findViewById(R.id.total_views);
            totalComments = (TextView) view.findViewById(R.id.total_comments);
            title = (TextView) view.findViewById(R.id.discussion_title);
            tags = (FlowTagView) view.findViewById(R.id.discussion_tag);
            view.setTag(this);
        }
    }
}
