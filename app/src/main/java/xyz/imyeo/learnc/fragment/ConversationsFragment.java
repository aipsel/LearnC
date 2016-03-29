package xyz.imyeo.learnc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import xyz.imyeo.learnc.CommonActivity;
import xyz.imyeo.learnc.R;
import xyz.imyeo.learnc.model.Conversation;
import xyz.imyeo.learnc.widget.CircleImageView;
import xyz.imyeo.learnc.widget.FlowTagView;

public class ConversationsFragment extends AbsFragment implements AdapterView.OnItemClickListener {

    public static final String TAG = "Fragment.Conversations";

    public static final String TITLE = "论坛";

    private List<Conversation> mData = new ArrayList<>();

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversations, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(new ListViewAdapter(inflater));
        listView.setOnItemClickListener(this);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_forum, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                Intent intent = new Intent(getActivity(), CommonActivity.class);
                intent.putExtra(CommonActivity.EXTRA_CLASS, CreateConversationFragment.class);
                intent.putExtra(CommonActivity.EXTRA_TAG, CreateConversationFragment.TAG);
                intent.putExtra(CommonActivity.EXTRA_TITLE, CreateConversationFragment.TITLE);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private final class ListViewAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        private final int mItemPerFetch = 10;

        private boolean mIsFetchedAll = false;

        public ListViewAdapter(LayoutInflater inflater) {
            mInflater = inflater;
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
