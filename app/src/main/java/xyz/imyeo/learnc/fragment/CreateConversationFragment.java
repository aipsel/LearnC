package xyz.imyeo.learnc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import xyz.imyeo.learnc.R;
import xyz.imyeo.learnc.model.Conversation;
import xyz.imyeo.learnc.widget.FlowTagView;

public class CreateConversationFragment extends AbsFragment {

    public static final String TAG = "Fragment.CConversation";

    public static final String TITLE = "发布";

    private EditText titleEdit, contentEdit;
    private Spinner typeSpinner;
    private FlowTagView tagView;

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_conversation, container, false);
        titleEdit = (EditText) view.findViewById(R.id.conversation_title_editor);
        contentEdit = (EditText) view.findViewById(R.id.conversation_content);
        typeSpinner = (Spinner) view.findViewById(R.id.conversation_type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.conversation_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        typeSpinner.setAdapter(adapter);
        tagView = (FlowTagView) view.findViewById(R.id.discussion_tag);
        tagView.addTag("+");
        tagView.addTag("指针");
        tagView.addTag("运算符");

        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_forum_publish, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_publish:
                if (ParseUser.getCurrentUser() != null)
                    new Conversation().setCreator(ParseUser.getCurrentUser())
                            .setTitle(titleEdit.getText().toString())
                            .setDescription(contentEdit.getText().toString())
                            .setType(typeSpinner.getSelectedItemPosition() + 1)
                            .addTags(tagView.getTags())
                            .saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null) {
                                        Log.e(TAG, "[done] error: " + e.getCode() + ", " + e.getMessage());
                                        Toast.makeText(getActivity(), "发表失败, 请重试!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.d(TAG, "done: save success!");
                                        getActivity().finish();
                                    }
                                }
                            });
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
