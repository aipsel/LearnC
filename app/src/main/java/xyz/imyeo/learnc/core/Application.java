package xyz.imyeo.learnc.core;

public class Application extends android.app.Application{

    private static Application mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static Application get() {
        return mInstance;
    }
}
