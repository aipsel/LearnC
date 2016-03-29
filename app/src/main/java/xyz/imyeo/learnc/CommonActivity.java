package xyz.imyeo.learnc;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toolbar;

import java.io.Serializable;

import xyz.imyeo.learnc.fragment.AbsFragment;

public class CommonActivity extends Activity implements AbsFragment.AbsFragmentListener {

    private static final String TAG = "Activity.Common";

    public static final String EXTRA_CLASS = "ExtraClass";
    public static final String EXTRA_TITLE = "ExtraTitle";
    public static final String EXTRA_TAG = "ExtraTag";
    public static final String EXTRA_ARG = "ExtraArgument";

    private FragmentManager mFragmentManager;

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setActionBar(mToolbar);
        mFragmentManager = getFragmentManager();

        Intent intent = getIntent();
        Serializable cls = intent.getSerializableExtra(EXTRA_CLASS);
        String fragmentTag = intent.getStringExtra(EXTRA_TAG);
        String title = intent.getStringExtra(EXTRA_TITLE);
        Bundle argument = intent.getBundleExtra(EXTRA_ARG);
        mToolbar.setTitle(title);
        if (cls == null || !(cls instanceof Class)) {
            Log.d(TAG, "onCreate: no content!!");
            finish();
        } else {
            AbsFragment.Flag.Builder builder = new AbsFragment.Flag.Builder();
            builder.singleton(true);
            AbsFragment.show(mFragmentManager, (Class<? extends AbsFragment>) cls,
                    R.id.content_container, fragmentTag, argument, builder.build());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mFragmentManager.getBackStackEntryCount() > 0) {
                mFragmentManager.popBackStack();
            } else {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChangeFragment(AbsFragment fragment, AbsFragment.Flag flag) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.right_in, R.animator.left_out,
                R.animator.left_in, R.animator.right_out);
        transaction.replace(fragment.getContainerId(), fragment, fragment.getFragmentTag());
        if (flag.addToStack) {
            transaction.addToBackStack(fragment.getFragmentTag());
        }
        mToolbar.setTitle(fragment.getTitle());
        transaction.commit();
    }
}
