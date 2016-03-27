package xyz.imyeo.learnc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import xyz.imyeo.learnc.R;

public class LoginFragment extends AbsFragment implements View.OnClickListener {

    public static final String TAG = "Fragment.Login";

    private EditText mPhoneEditText;

    private EditText mPasswdEditText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mPhoneEditText = (EditText) view.findViewById(R.id.login_phone);
        mPasswdEditText = (EditText) view.findViewById(R.id.login_password);
        view.findViewById(R.id.login_button_signup).setOnClickListener(this);
        view.findViewById(R.id.login_button_signin).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_button_signup:
                Flag.Builder builder = new Flag.Builder().singleton(false).addToStack(true);
                show(SignUpFragment.class, getContainerId(), getFragmentTag(), null, builder.build());
                break;
            case R.id.login_button_signin:
                String phone = mPhoneEditText.getText().toString().trim();
                String passwd = mPasswdEditText.getText().toString().trim();
                ParseUser.logInInBackground(phone, passwd, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (e == null && user != null) {
                            //login success.
                            Log.d(TAG, "done: "
                                            + user.getSessionToken() + ", "
                                            + user.getUsername() + ", "
                                            + user.getObjectId() + ", "
                                            + user.getCreatedAt() + ", "
                            );
                        } else if (user == null) {
                            // phone or password is not correct.
                            Log.d(TAG, "done: 手机号或密码输入有误！");
                            Toast.makeText(getActivity(), "手机号或密码输入有误！", Toast.LENGTH_SHORT).show();
                        } else {
                            // something went wrong.
                            Log.d(TAG, "done: 手机号或密码输入有误！");
                            Toast.makeText(getActivity(), "未知错误，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }
}
