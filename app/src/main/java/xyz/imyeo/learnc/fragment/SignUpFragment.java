package xyz.imyeo.learnc.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.HashMap;
import java.util.Map;

import xyz.imyeo.learnc.R;

public class SignUpFragment extends AbsFragment implements View.OnClickListener {

    public static final String TAG = "Fragment.Signup";
    public static final String TITLE = "注册";

    private EditText phoneEdit, captchaEdit, passwdEdit, passwdEdit2;

    private Button getCaptchaButton;

    private CountDownTimer mCountDownTimer;

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_signup, container, false);

        phoneEdit = (EditText) view.findViewById(R.id.signup_phone);
        captchaEdit = (EditText) view.findViewById(R.id.signup_captcha);
        passwdEdit = (EditText) view.findViewById(R.id.signup_password);
        passwdEdit2 = (EditText) view.findViewById(R.id.signup_password_confirm);
        getCaptchaButton = (Button) view.findViewById(R.id.signup_button_get_captcha);
        getCaptchaButton.setOnClickListener(this);
        view.findViewById(R.id.signup_button_signup).setOnClickListener(this);

        mCountDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                getCaptchaButton.setText(String.format("%ds 后重发", millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                getCaptchaButton.setText("获取验证码");
                getCaptchaButton.setEnabled(true);
            }
        };
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCountDownTimer.cancel();
    }

    private void startCount() {
        getCaptchaButton.setEnabled(false);
        mCountDownTimer.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_button_signup: {
                String phone = phoneEdit.getText().toString().trim();
                String captcha = captchaEdit.getText().toString().trim();
                String passwd = passwdEdit.getText().toString().trim();
                String passwd2 = passwdEdit2.getText().toString().trim();

                if (phone.isEmpty()) {
                    Toast.makeText(getActivity(), "请输入手机号！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (captcha.isEmpty()) {
                    Toast.makeText(getActivity(), "请输入验证码！", Toast.LENGTH_SHORT).show();
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
                user.put("captcha", captcha);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            final int errCode = e.getCode();
                            switch (e.getCode()) {
                                case ParseException.USERNAME_TAKEN:
                                    Toast.makeText(getActivity(), "手机号已经注册！", Toast.LENGTH_SHORT).show();
                                    break;
                                case ParseException.SCRIPT_ERROR:
                                    Toast.makeText(getActivity(), "验证码错误!", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Log.d(TAG, "unknown err. code:" + errCode + ",msg:" + e.getMessage());
                                    Toast.makeText(getActivity(), "未知错误，请重试", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } else {
                            getFragmentManager().popBackStack();
                        }
                    }
                });
                break;
            }
            case R.id.signup_button_get_captcha: {
                String phone = phoneEdit.getText().toString().trim();
                if (phone.isEmpty()) {
                    Toast.makeText(getActivity(), "请输入手机号！", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, Object> params = new HashMap<>();
                params.put("phone", phone);
                params.put("template", 0); // 注册验证码模版。
                ParseCloud.callFunctionInBackground("sendSMS", params,
                        new FunctionCallback<Map<String, Object>>() {
                            @Override
                            public void done(Map<String, Object> object, ParseException e) {
                                if (e == null) {
                                    Toast.makeText(getActivity(), "验证码已发送。", Toast.LENGTH_SHORT).show();
                                    startCount();
                                } else {
                                    Log.d(TAG, "Captcha Send Error.", e);
                                    Toast.makeText(getActivity(), "请输入正确的手机号。", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        });
                break;
            }
        }
    }
}
