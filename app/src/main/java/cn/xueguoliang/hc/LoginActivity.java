package cn.xueguoliang.hc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();

        boolean ok = Jni.instance().Login(username, password);
        if (ok) {
            Log.i(tag, "login success");
        } else {
            Log.e(tag, "login error");
        }
    }
}
