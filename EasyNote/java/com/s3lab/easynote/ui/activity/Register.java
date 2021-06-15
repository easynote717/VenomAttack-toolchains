package com.s3lab.easynote.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.s3lab.easynote.R;
import com.s3lab.easynote.db.model.User;
import com.s3lab.easynote.util.ActivityCollector;
import com.s3lab.easynote.util.MD5;
import com.s3lab.easynote.util.PhotoUtils;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText et_account_name,et_password;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_register);

        et_account_name = (EditText) findViewById(R.id.et_account_name);
        et_password = (EditText) findViewById(R.id.et_password);
        register = (Button)findViewById(R.id.register);
        Connector.getDatabase();
        register.setOnClickListener(this);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register:
                String name = et_account_name.getText().toString();
                String password = et_password.getText().toString();
                password = MD5.md5(password);
                List<User> users = LitePal.where("name==?", name).find(User.class);
                Toast mToast = Toast.makeText(this, null, Toast.LENGTH_SHORT);

                if (!users.isEmpty()) {
                    mToast.setText("The user name already exists");
                    mToast.show();
                }
                else {
                    User user = new User();
                    user.setName(name);
                    user.setPassword(password);
                    user.setPortrait((new PhotoUtils()).file2byte(this ,"default_portrait.jpg"));
                    user.setRemember(0);
                    user.save();
                    mToast.setText("Registered successfully");
                    mToast.show();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                }
        }
    }

}
