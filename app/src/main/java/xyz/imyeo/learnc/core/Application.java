package xyz.imyeo.learnc.core;

import com.parse.Parse;
import com.parse.ParseObject;

import xyz.imyeo.learnc.R;
import xyz.imyeo.learnc.model.Comment;
import xyz.imyeo.learnc.model.Conversation;

public class Application extends android.app.Application {

    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // Initialize Parse SDK.
        ParseObject.registerSubclass(Comment.class);
        ParseObject.registerSubclass(Conversation.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getResources().getString(R.string.parse_app_id))
                .server(getResources().getString(R.string.parse_server))
                .clientKey(getResources().getString(R.string.parse_client_id))
                .build());
    }

    public static Application get() {
        return mInstance;
    }
}
