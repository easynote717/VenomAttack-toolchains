package com.s3lab.easynote.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.s3lab.easynote.MainActivity;
import com.s3lab.easynote.R;
import com.s3lab.easynote.db.LoginUser;
import com.s3lab.easynote.db.model.User;
import com.s3lab.easynote.util.ActivityCollector;
import com.s3lab.easynote.util.MD5;
import com.s3lab.easynote.util.ToastUtils;
import org.litepal.LitePal;
import org.litepal.LitePalApplication;
import java.util.List;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText et_name,et_password;
    private Button login,register;
    private ImageView iv_eye,iv_more_account;
    private CheckBox cb_remember;
    private boolean passwordVisible = false;
    private ToastUtils toastUtils = new ToastUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_login);

        LitePal.initialize(this);

        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        et_name = (EditText) findViewById(R.id.et_account_name);
        et_password = (EditText) findViewById(R.id.et_password);
        iv_eye = (ImageView) findViewById(R.id.iv_eye);
        iv_more_account = (ImageView) findViewById(R.id.iv_more_accout);
        cb_remember = (CheckBox) findViewById(R.id.cb_remember);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        iv_eye.setOnClickListener(this);
        iv_more_account.setOnClickListener(this);

        if(getIntent().getBooleanExtra("first_in",false)) {
            Log.d("food","first_in");
            List<User> users = LitePal.findAll(User.class);
            for (User u : users) {
                if (u.getRemember().equals(1)) {
                    LoginUser.getInstance().login(u);
                    Intent intent1 = new Intent(Login.this, MainActivity.class);
                    startActivity(intent1);
                    toastUtils.showShort(Login.this, "Account " + u.getName() + " login successful");
                    break;
                }
            }
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        List<User> users = LitePal.findAll(User.class);
        for (User u:users){
            if(u.getRemember().equals(1)){
                et_name.setText(u.getName());
                et_password.setText("12345678");
                cb_remember.setChecked(true);
                break;
            }
        }
    }

    @Override
    public void onClick(View v){
        String name = et_name.getText().toString();
        String password=et_password.getText().toString();
        switch (v.getId()){
            case R.id.register:
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                break;
            case R.id.login:
                boolean login_flag = false;
                User user = LitePal.where("name=?",name).findFirst(User.class);
                if (password.equals("12345678")) password = user.getPassword();
                else password = MD5.md5(password);
                if (user.checkPassword(password)){
                    if(cb_remember.isChecked()) {
                        user.setRemember(1);
                    }else{
                        user.setRemember(0);
                    }
                    user.update(user.getId());
                    LoginUser.getInstance().login(user);
                    Intent intent1 = new Intent(Login.this, MainActivity.class);
                    startActivity(intent1);
                    login_flag = true;
                    toastUtils.showShort(Login.this,"Account "+user.getName()+" login successful");
                    break;
                }else {
                    user.setRemember(0);
                }

                if(login_flag == false){
                    toastUtils.showShort(Login.this,"Login failed ");
                }
                break;
            case R.id.iv_eye:
                if(passwordVisible){
                    iv_eye.setSelected(false);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordVisible = false;
                }else {
                    iv_eye.setSelected(true);
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordVisible = true;
                }
                break;
            case R.id.iv_more_accout:
                List<User> users1 = LitePal.findAll(com.s3lab.easynote.db.model.User.class);
                for(User u:users1) Log.d("food",""+u.toString());
                break;
        }
    }

}
