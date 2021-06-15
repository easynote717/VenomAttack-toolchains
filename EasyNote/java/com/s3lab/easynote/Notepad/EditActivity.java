package com.s3lab.easynote.Notepad;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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


import com.s3lab.easynote.MainActivity;
import com.s3lab.easynote.R;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    DBService myDb;
    private Button btnCancel;
    private Button btnSave;
    private EditText titleEditText;
    private EditText contentEditText;
    private TextView timeTextView;

    private Values value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.note_editor);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        if(timeTextView.getText().length()==0)
            timeTextView.setText(getTime());

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_file_save:
                        file_save();
                        break;
                    case R.id.action_file_delete:
                        file_cancel();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_edit_menu, menu);
        return true;
    }

    private void init() {
        myDb = new DBService(this);
        SQLiteDatabase db = myDb.getReadableDatabase();
        titleEditText = findViewById(R.id.et_title);
        contentEditText = findViewById(R.id.et_content);
        timeTextView = findViewById(R.id.edit_time);
        Intent intent = this.getIntent();
        if (intent != null) {
            value = new Values();
            value.setTime(intent.getStringExtra(DBService.TIME));
            value.setTitle(intent.getStringExtra(DBService.TITLE));
            value.setContent(intent.getStringExtra(DBService.CONTENT));

            timeTextView.setText(value.getTime());
            titleEditText.setText(value.getTitle());
            contentEditText.setText(value.getContent());
        }else{
            Log.e("intent:","is not null");
        }
    }

    private String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String str = sdf.format(date);
        return str;
    }

    public void file_save() {
        SQLiteDatabase db = myDb.getWritableDatabase();
        ContentValues values = new ContentValues();
        String title= titleEditText.getText().toString();
        String content=contentEditText.getText().toString();
        String time= timeTextView.getText().toString();
        if("".equals(titleEditText.getText().toString())){
            Toast.makeText(EditActivity.this,"Headings cannot be empty",Toast.LENGTH_LONG).show();
            return;
        }
        if("".equals(contentEditText.getText().toString())) {
            Toast.makeText(EditActivity.this,"The content cannot be empty",Toast.LENGTH_LONG).show();
            return;
        }
        values.put(DBService.TITLE,title);
        values.put(DBService.CONTENT,content);
        values.put(DBService.TIME,time);
        db.insert(DBService.TABLE,null,values);
        Toast.makeText(EditActivity.this,"Save success",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(EditActivity.this, MainActivity.class);
        startActivity(intent);
        db.close();
    }

    public void file_cancel() {
        Intent intent = new Intent(EditActivity.this, com.s3lab.easynote.MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

}
