package xyz.imyeo.learnc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import xyz.imyeo.learnc.R;
import xyz.imyeo.learnc.core.DataPipe;
import xyz.imyeo.learnc.model.Conversation;

public class ConversationDetailFragment extends AbsFragment {

    public static final String TAG = "Fragment.ConversationD";

    public static final String TITLE = "详情";

    private Conversation conversation;

    private View mView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataPipe data = getArguments().getParcelable(DataPipe.ARG_KEY);
        if (data != null) {
            conversation = (Conversation) data.get("conversation");
        } else {
            throw new RuntimeException("should pass the conversation data.");
        }
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_conversation_detail, container, false);
        ((TextView) mView.findViewById(R.id.conversation_content)).setText(conversation.getDescription());
        return mView;
    }
}
