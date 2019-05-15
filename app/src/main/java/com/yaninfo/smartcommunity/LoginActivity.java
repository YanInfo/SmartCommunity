package com.yaninfo.smartcommunity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.yaninfo.smartcommunity.util.HttpLoginData;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * @author zhangyan
 * @data 2019/4/10
 */
public class LoginActivity extends Activity {

    private EditText user;
    private EditText password;
    private Button login;
    private Button register;
    private SharedPreferences pref;
    private CheckBox rembemberPass;
    private static final String URLLOGIN = "http://192.168.0.104:8088/client/login1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);
        pref = PreferenceManager.getDefaultSharedPreferences(this);

        // 绑定控件
        init();

        // 记住密码
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            String user1 = pref.getString("user", "");
            String password1 = pref.getString("password", "");
            user.setText(user1);
            password.setText(password1);
            rembemberPass.setChecked(true);
        }

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // 存储用户名密码的数组
                String[] data = null;
                HttpLoginData loginData;
                final String inputUser = user.getText().toString();
                final String inputPassword = password.getText().toString();

                if (TextUtils.isEmpty(inputUser)) {
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(inputPassword)) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    // Handler内部类更新UI
                    @SuppressLint("HandlerLeak") Handler handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            switch (msg.what) {
                                case 0:
                                    Toast.makeText(LoginActivity.this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                                    break;
                                case 1:
                                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    LoginActivity.this.finish();
                                    break;
                                case 2:
                                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(LoginActivity.this, "连接超时", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    break;
                            }
                        }
                    };

                    data = new String[]{inputUser, inputPassword};
                    // 实例化数据工具类
                    loginData = new HttpLoginData();
                    String jsonString = loginData.stringTojson(data);

                    URL url = null;
                    try {
                        url = new URL(URLLOGIN);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    // 利用Http向服务端发送数据
                    loginData.sendData(jsonString, handler, url);
                }

            }
        });


        /**
         * 跳转到注册页面
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

    }

    /**
     * 初始化
     */
    private void init() {
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        rembemberPass = findViewById(R.id.remember);
    }

}