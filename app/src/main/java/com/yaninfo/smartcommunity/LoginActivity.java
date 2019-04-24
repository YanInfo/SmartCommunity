package com.yaninfo.smartcommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * @Author: zhangyan
 * @Date: 2019/4/19 10:17
 * @Description:
 * @Version: 1.0
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_enter = null;
    private EditText edit_user = null;
    private EditText edit_password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        btn_enter =  findViewById(R.id.btn_enter);
        btn_enter.setOnClickListener(this);
        edit_user = findViewById(R.id.et_user);
        edit_password = findViewById(R.id.et_pwd);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_enter:
                Toast.makeText(this,"登录成功了",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * Activity销毁时停止播放
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 返回重启加载
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }

}
