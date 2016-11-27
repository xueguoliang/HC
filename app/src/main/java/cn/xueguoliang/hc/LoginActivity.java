package cn.xueguoliang.hc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class LoginActivity extends AppCompatActivity {

    private  static final  String tag = "LoginActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = (Button)findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginClicked();
            }
        });

        Button regBtn = (Button)findViewById(R.id.reg);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegClicked();
            }
        });
    }

    private void RegClicked() {
        EditText editUsername = (EditText) findViewById(R.id.username);
        EditText editPassword = (EditText) findViewById(R.id.password);
        EditText editMobile = (EditText)findViewById(R.id.mobile);
        EditText editEmail = (EditText)findViewById(R.id.email);
        EditText editID = (EditText)findViewById(R.id.id);

        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();
        String mobile = editMobile.getText().toString();
        String email = editEmail.getText().toString();
        String id = editID.getText().toString();


        boolean ok = Jni.instance().Reg(username, password, mobile, email, id);
        if(ok)
        {
            Log.i(tag, "register success");
        }
        else {
            Log.e(tag, "register error");
        }
    }

    private  void LoginClicked() {
        EditText editUsername = (EditText) findViewById(R.id.username);
        EditText editPassword = (EditText) findViewById(R.id.password);
        Spinner spinType = (Spinner)findViewById(R.id.type);


        String type = spinType.getSelectedItem().toString();
        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();

        boolean ok = Jni.instance().Login(username, password, type);
        if (ok) {
            Log.i(tag, "login success");

            Intent intent;

            if(type.equals("司机")) {
                // 跳转到DriverMainActivity
                intent = new Intent(getApplication(), DriverMainActivity.class);
            }
            else {
                intent = new Intent(getApplication(), PassangerMainActivity.class);
            }
            startActivity(intent);

            finish();

        } else {
            Log.e(tag, "login error");
        }
    }
}
