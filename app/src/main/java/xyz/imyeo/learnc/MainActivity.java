package xyz.imyeo.learnc;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.parse.ParseUser;

import xyz.imyeo.learnc.core.PreferenceHelper;
import xyz.imyeo.learnc.editor.PageSystem;
import xyz.imyeo.learnc.fragment.AbsFragment;
import xyz.imyeo.learnc.fragment.ConversationsFragment;
import xyz.imyeo.learnc.fragment.HomeFragment;
import xyz.imyeo.learnc.fragment.LoginFragment;
import xyz.imyeo.learnc.fragment.UserFragment;
import xyz.imyeo.learnc.widget.CircleImageView;
import xyz.imyeo.learnc.widget.CodeEditor;
import xyz.imyeo.learnc.widget.GoodScrollView;

public class MainActivity extends Activity implements View.OnClickListener,
        AbsFragment.AbsFragmentListener, GoodScrollView.ScrollInterface,PageSystem.PageSystemInterface {

    private static final String TAG = "MainActivity";

    private static final int ACTION_LOGIN = 1;

    private FragmentManager mFragmentManager;

    private DrawerLayout mNavigationDrawer;

    private Toolbar mToolbar;

    private GoodScrollView verticalScroll;
    private CodeEditor mEditor;
    private HorizontalScrollView horizontalScroll;
    private PageSystem pageSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFragmentManager = getFragmentManager();

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setActionBar(mToolbar);
        initDrawer(getResources().getDrawable(R.drawable.user_icon, getTheme()), "Yeo Yang");

        AbsFragment.Flag.Builder builder = new AbsFragment.Flag.Builder();
        builder.singleton(true);
        AbsFragment.show(mFragmentManager, HomeFragment.class, R.id.home_content,
                HomeFragment.TAG, null, builder.build());
        mToolbar.setTitle(R.string.drawer_home);

        setupTextEditor();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mNavigationDrawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        mNavigationDrawer.closeDrawer(GravityCompat.START);
        switch (v.getId()) {
            case R.id.drawer_user_panel:
                final ParseUser user = ParseUser.getCurrentUser();
                if (user != null) {
                    mToolbar.setTitle(R.string.drawer_user);
                    AbsFragment.Flag.Builder builder = new AbsFragment.Flag.Builder();
                    builder.singleton(true);
                    AbsFragment.show(mFragmentManager, UserFragment.class, R.id.home_content,
                            UserFragment.TAG, null, builder.build());
                } else {
                    Intent intent = new Intent(this, CommonActivity.class);
                    intent.putExtra(CommonActivity.EXTRA_CLASS, LoginFragment.class);
                    intent.putExtra(CommonActivity.EXTRA_TAG, LoginFragment.TAG);
                    intent.putExtra(CommonActivity.EXTRA_TITLE, LoginFragment.TITLE);
                    startActivityForResult(intent, ACTION_LOGIN);
                }
                break;
            case R.id.drawer_menu_home:
                mToolbar.setTitle(R.string.drawer_home);
                AbsFragment.Flag.Builder builder = new AbsFragment.Flag.Builder();
                builder.singleton(true);
                AbsFragment.show(mFragmentManager, HomeFragment.class, R.id.home_content,
                        HomeFragment.TAG, null, builder.build());
                break;
            case R.id.drawer_menu_tutorial:
                break;
            case R.id.drawer_menu_forum:
                mToolbar.setTitle(R.string.drawer_forum);
                AbsFragment.show(mFragmentManager, ConversationsFragment.class, R.id.home_content,
                        ConversationsFragment.TAG, null, new AbsFragment.Flag.Builder().singleton(true).build());
                break;
            case R.id.drawer_menu_self_test:
                break;
            case R.id.drawer_menu_setting:
                break;
            case R.id.drawer_menu_about:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mNavigationDrawer.isDrawerOpen(GravityCompat.START)) {
            mNavigationDrawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU
                && !mNavigationDrawer.isDrawerOpen(GravityCompat.START)) {
            mNavigationDrawer.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ACTION_LOGIN:
                if (resultCode == RESULT_OK) {
                    mToolbar.setTitle(R.string.drawer_user);
                    AbsFragment.Flag.Builder builder = new AbsFragment.Flag.Builder();
                    builder.singleton(true);
                    AbsFragment.show(mFragmentManager, UserFragment.class, R.id.home_content,
                            UserFragment.TAG, null, builder.build());
                }
                break;
        }
    }

    @Override
    public void onPageChanged(int page) {
//        pageSystemButtons.updateVisibility(false);
//        searchResult = null;
        mEditor.clearHistory();
//        invalidateOptionsMenu();
    }

    @Override
    public void onScrollChanged(int l, int t, int oldl, int oldt) {
//        pageSystemButtons.updateVisibility(Math.abs(t) > 10);
//
//        if (!PreferenceHelper.getSyntaxHighlight(this) || (mEditor.hasSelection() &&
//                searchResult == null) || updateHandler == null || colorRunnable_duringScroll == null)
//            return;
//
//        updateHandler.removeCallbacks(colorRunnable_duringEditing);
//        updateHandler.removeCallbacks(colorRunnable_duringScroll);
//        updateHandler.postDelayed(colorRunnable_duringScroll, SYNTAX_DELAY_MILLIS_SHORT);
    }

    private void setupTextEditor() {

        verticalScroll = (GoodScrollView) findViewById(R.id.vertical_scroll);
        horizontalScroll = (HorizontalScrollView) findViewById(R.id.horizontal_scroll);
        mEditor = (CodeEditor) findViewById(R.id.editor);

//        AccessoryView accessoryView = (AccessoryView) findViewById(R.id.accessoryView);
//        accessoryView.setInterface(this);

//        HorizontalScrollView parentAccessoryView = (HorizontalScrollView) view.findViewById(R.id.parent_accessory_view);
//        ViewUtils.setVisible(parentAccessoryView, PreferenceHelper.getUseAccessoryView(this));


        if (PreferenceHelper.getWrapContent(this)) {
            horizontalScroll.removeView(mEditor);
            verticalScroll.removeView(horizontalScroll);
            verticalScroll.addView(mEditor);
        }

//        verticalScroll.setScrollInterface(this);

        pageSystem = new PageSystem(this, this, "");

//        pageSystemButtons = new PageSystemButtons(this, this,
//                (FloatingActionButton) findViewById(R.id.fabPrev),
//                (FloatingActionButton) findViewById(R.id.fabNext));

        mEditor.setupEditor(verticalScroll, pageSystem);
        mEditor.replaceTextKeepCursor("int main() {\n    printf(\"hello!\\n\");\n}");
    }

    private void initDrawer(Drawable userIcon, String userName) {
        View drawerMenu = findViewById(R.id.navigation_drawer);
        drawerMenu.findViewById(R.id.drawer_user_panel).setOnClickListener(this);
        ((CircleImageView) drawerMenu.findViewById(R.id.drawer_user_icon))
                .setImageDrawable(userIcon);
        ((TextView) drawerMenu.findViewById(R.id.drawer_user_name)).setText(userName);
        drawerMenu.findViewById(R.id.drawer_menu_home).setOnClickListener(this);
        drawerMenu.findViewById(R.id.drawer_menu_tutorial).setOnClickListener(this);
        drawerMenu.findViewById(R.id.drawer_menu_forum).setOnClickListener(this);
        drawerMenu.findViewById(R.id.drawer_menu_self_test).setOnClickListener(this);
        drawerMenu.findViewById(R.id.drawer_menu_setting).setOnClickListener(this);
        drawerMenu.findViewById(R.id.drawer_menu_about).setOnClickListener(this);
    }

    @Override
    public void onChangeFragment(AbsFragment fragment, AbsFragment.Flag flag) {
        mFragmentManager.beginTransaction()
                .replace(fragment.getContainerId(), fragment, fragment.getFragmentTag())
                .commit();
    }
}
