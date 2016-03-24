package xyz.imyeo.learnc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import xyz.imyeo.learnc.R;

public class SignUpFragment extends AbsFragment {

    public static final String TAG = "Fragment.Login";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_signup, container, false);

        view.findViewById(R.id.signup_button_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText phoneEdit = (EditText) view.findViewById(R.id.signup_phone);
                EditText passwdEdit = (EditText) view.findViewById(R.id.signup_password);
                EditText passwdEdit2 = (EditText) view.findViewById(R.id.signup_password_confirm);

                String phone = phoneEdit.getText().toString().trim();
                String passwd = passwdEdit.getText().toString().trim();
                String passwd2 = passwdEdit2.getText().toString().trim();

                if (phone.isEmpty()) {
                    Toast.makeText(getActivity(), "请输入手机号！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (passwd.isEmpty()) {
                    Toast.makeText(getActivity(), "请输入密码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!passwd.equals(passwd2)) {
                    Toast.makeText(getActivity(), "两次密码输入不一致！", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser user = new ParseUser();
                user.setUsername(phone);
                user.setPassword(passwd);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            e.printStackTrace();
                            Log.d(TAG, "done: 注册失败！");
                            Toast.makeText(getActivity(), "注册失败！", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d(TAG, "done: 注册成功！");
                            Toast.makeText(getActivity(), "注册成功！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
        return view;
    }
}
