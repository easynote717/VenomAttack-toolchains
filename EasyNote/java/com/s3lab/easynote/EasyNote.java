package com.s3lab.easynote;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.s3lab.easynote.ui.activity.Login;
import com.s3lab.easynote.util.ToastUtils;
import org.litepal.LitePal;

public class EasyNote extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean delete = false;
        if (!delete){
            Intent intent = new Intent(EasyNote.this, Login.class);
            intent.putExtra("first_in", true);
            startActivity(intent);
        }else{
            LitePal.deleteDatabase("food_db");
            (new ToastUtils()).showShort(this,"data set is deleted");
        }
    }
}
