package com.s3lab.easynote.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.s3lab.easynote.R;
import com.s3lab.easynote.db.LoginUser;
import com.s3lab.easynote.util.ActivityCollector;

public class Setting extends AppCompatActivity implements View.OnClickListener {
    private Button exit;
    private com.lqr.optionitemview.OptionItemView iv_person_info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_setting);
        exit = (Button) findViewById(R.id.exit);
        iv_person_info = (com.lqr.optionitemview.OptionItemView) findViewById(R.id.iv_person_info);
        exit.setOnClickListener(this);
        iv_person_info.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.exit:
                ActivityCollector.finishAll();
                LoginUser.logout();
                Intent intent = new Intent(Setting.this, Login.class);
                startActivity(intent);
                break;
            case R.id.iv_person_info:
                Intent intent1 = new Intent(Setting.this, PersonInfo.class);
                startActivity(intent1);
                break;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
