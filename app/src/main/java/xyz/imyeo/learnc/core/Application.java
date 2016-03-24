package xyz.imyeo.learnc.core;

import com.parse.Parse;

import xyz.imyeo.learnc.R;

public class Application extends android.app.Application {

    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        // Initialize Parse SDK.
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getResources().getString(R.string.parse_app_id))
                .server(getResources().getString(R.string.parse_server))
                .build());
    }

    public static Application get() {
        return mInstance;
    }
}
