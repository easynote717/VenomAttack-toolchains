package com.s3lab.easynote.Notepad;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.s3lab.easynote.R;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowActivity extends AppCompatActivity {

private Button btnSave;
private Button file_edit;
private TextView showTime;
private TextView showContent;
private TextView showTitle;

private Values value;
DBService myDb;


@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.activity_show);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    init();
    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_file_share:
                    Toast.makeText(ShowActivity.this, "Sorry, can not share for the time being!", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.action_file_delete:
                    Toast.makeText(ShowActivity.this, "Sorry, please delete from the project list!", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_show_menu, menu);
        return true;
    }

    public void init() {
        myDb = new DBService(this);
        file_edit = findViewById(R.id.file_edit);
        showTime = findViewById(R.id.show_time);
        showTitle = findViewById(R.id.show_title);
        showContent = findViewById(R.id.show_content);
        Intent intent = this.getIntent();
        if (intent != null) {
            value = new Values();
            value.setTime(intent.getStringExtra(DBService.TIME));
            value.setTitle(intent.getStringExtra(DBService.TITLE));
            value.setContent(intent.getStringExtra(DBService.CONTENT));
            value.setId(Integer.valueOf(intent.getStringExtra(DBService.ID)));
            showTime.setText(value.getTime());
            showTitle.setText(value.getTitle());
            showContent.setText(value.getContent());
        }

        file_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowActivity.this, com.s3lab.easynote.Notepad.EditActivity.class);
                Values value1 = new Values();
                value1.setTime(intent.getStringExtra(DBService.TIME));
                value1.setTitle(intent.getStringExtra(DBService.TITLE));
                value1.setContent(intent.getStringExtra(DBService.CONTENT));
                intent.putExtra(DBService.TITLE, showTitle.getText());
                intent.putExtra(DBService.CONTENT,showContent.getText());
                intent.putExtra(DBService.TIME,showTime.getText());
                startActivity(intent);
            }
        });
    }

    String getTime() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-" +
                "" +
                "\\ HH:mm");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
