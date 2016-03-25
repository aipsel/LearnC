package xyz.imyeo.learnc;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import java.io.Serializable;

import xyz.imyeo.learnc.fragment.AbsFragment;

public class CommonActivity extends Activity implements AbsFragment.AbsFragmentListener {

    private static final String TAG = "Activity.Common";

    public static final String EXTRA_CLASS = "ExtraClass";
    public static final String EXTRA_TAG = "ExtraTag";

    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        mFragmentManager = getFragmentManager();

        Serializable cls = getIntent().getSerializableExtra(EXTRA_CLASS);
        String fragmentTag = getIntent().getStringExtra(EXTRA_TAG);
        if (cls == null || !(cls instanceof Class)) {
            Log.d(TAG, "onCreate: no content!!");
            finish();
        } else {
            AbsFragment.show(mFragmentManager, (Class<? extends AbsFragment>) cls,
                    R.id.content_container, fragmentTag, null, AbsFragment.FLAG_SINGLETON);
        }
    }

    @Override
    public void onChangeFragment(AbsFragment fragment, int flag) {
        mFragmentManager.beginTransaction()
                .replace(fragment.getContainerId(), fragment, fragment.getFragmentTag())
                .addToBackStack(fragment.getFragmentTag())
                .commit();
    }
}
