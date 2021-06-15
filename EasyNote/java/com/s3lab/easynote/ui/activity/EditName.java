package com.s3lab.easynote.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.s3lab.easynote.R;
import com.s3lab.easynote.db.LoginUser;
import com.s3lab.easynote.util.ActivityCollector;
import com.s3lab.easynote.widget.TitleLayout;


public class EditName extends AppCompatActivity {
    private LoginUser loginUser = LoginUser.getInstance();
    private TitleLayout tl_title;
    private EditText edit_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_edit_name);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edit_name = (EditText) findViewById(R.id.et_edit_name);
        edit_name.setText(loginUser.getName());

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_file_save:
                        loginUser.setName(edit_name.getText().toString());
                        setResult(RESULT_OK);
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.people_info_menu, menu);
        return true;
    }
}
