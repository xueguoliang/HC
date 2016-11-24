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
    }

    private  void LoginClicked() {
        EditText editUsername = (EditText) findViewById(R.id.username);
        EditText editPassword = (EditText) findViewById(R.id.password);

        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();

        boolean bOK = Jni.instance().Login(username, password);
        if (bOK) {
            Log.i(tag, "login success");
        } else {
            Log.e(tag, "login error");
        }
    }
}
