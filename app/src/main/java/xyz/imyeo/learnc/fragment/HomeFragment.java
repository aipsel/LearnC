package xyz.imyeo.learnc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.json.JSONArray;

import java.util.List;

import xyz.imyeo.learnc.R;
import xyz.imyeo.learnc.model.Course;

public class HomeFragment extends AbsFragment implements AdapterView.OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = "Fragment.Home";

    private JSONArray mHotViews;

    private List<Course> mEnrolledCourses;

    private ListViewAdapter mListViewAdapter;

    private SwipeRefreshLayout mRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getTitle() {
        return getResources().getString(R.string.drawer_home);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.hot_view_pager);
        viewPager.setAdapter(new ViewPagerAdapter());
        ListView listView = (ListView) view.findViewById(R.id.home_enrolled_courses);
        mListViewAdapter = new ListViewAdapter();
        listView.setAdapter(mListViewAdapter);
        listView.setOnItemClickListener(this);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        mRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onRefresh() {

    }

    public void doRefresh() {
        ParseUser.getCurrentUser().fetchInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

            }
        });
    }

    private final class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mHotViews.length();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
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

    private final class ListViewAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        @Override
        public int getCount() {
            return mEnrolledCourses.size();
        }

        @Override
        public Course getItem(int position) {
            return mEnrolledCourses.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.layout_my_course_item, parent, false);
                holder = new ViewHolder(convertView);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Course course = getItem(position);
            holder.courseImage.setImageResource(R.drawable.user_icon);
            holder.courseTitle.setText(course.getTitle());
            holder.courseIntro.setText(course.getIntro());
            holder.courseProgress.setProgress(course.getProgress());
            return convertView;
        }
    }

    private final class ViewHolder {

        ImageView courseImage;
        TextView courseTitle;
        TextView courseIntro;
        ProgressBar courseProgress;

        public ViewHolder(View view) {
            this.courseImage = (ImageView) view.findViewById(R.id.course_image);
            courseTitle = (TextView) view.findViewById(R.id.course_title);
            courseIntro = (TextView) view.findViewById(R.id.course_intro);
            courseProgress = (ProgressBar) view.findViewById(R.id.course_progress);
            view.setTag(this);
        }
    }
}
