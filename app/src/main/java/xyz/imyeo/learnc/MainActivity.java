package xyz.imyeo.learnc;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import xyz.imyeo.learnc.fragment.AbsFragment;
import xyz.imyeo.learnc.fragment.HomeFragment;
import xyz.imyeo.learnc.fragment.LoginFragment;
import xyz.imyeo.learnc.widget.CircleImageView;

public class MainActivity extends Activity implements View.OnClickListener,
        AbsFragment.AbsFragmentListener {

    private static final String TAG = "MainActivity";

    private FragmentManager mFragmentManager;

    private DrawerLayout mNavigationDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFragmentManager = getFragmentManager();

        initDrawer(getResources().getDrawable(R.drawable.user_icon), "Yeo Yang");

        AbsFragment.show(mFragmentManager, HomeFragment.class, R.id.home_content,
                HomeFragment.TAG, null, AbsFragment.FLAG_SINGLETON);
    }

    @Override
    public void onClick(View v) {
        mNavigationDrawer.closeDrawer(GravityCompat.START);
        switch (v.getId()) {
            case R.id.drawer_user_panel:
                if (ParseUser.getCurrentUser().isAuthenticated()) {
                    // user fragment
                    Log.d(TAG, "onClick: User has logged in.");
                    ParseUser.logOutInBackground(new LogOutCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e != null) {
                                Log.d(TAG, "onClick: logout error.", e);
                            } else {
                                Log.d(TAG, "onClick: logout.");
                            }
                        }
                    });
                } else {
                    Intent intent = new Intent(this, CommonActivity.class);
                    intent.putExtra(CommonActivity.EXTRA_CLASS, LoginFragment.class);
                    intent.putExtra(CommonActivity.EXTRA_TAG, LoginFragment.TAG);
                    startActivity(intent);
                }
                break;
            case R.id.drawer_menu_home:
                AbsFragment.show(mFragmentManager, HomeFragment.class, R.id.home_content,
                        HomeFragment.TAG, null, AbsFragment.FLAG_SINGLETON);
                break;
            case R.id.drawer_menu_tutorial:
                break;
            case R.id.drawer_menu_forum:
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
    public void onChangeFragment(AbsFragment fragment, int flag) {
        mFragmentManager.beginTransaction()
                .replace(fragment.getContainerId(), fragment, fragment.getFragmentTag())
                .commit();
    }
}
