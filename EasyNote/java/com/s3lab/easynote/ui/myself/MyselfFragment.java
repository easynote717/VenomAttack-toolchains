package com.s3lab.easynote.ui.myself;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.s3lab.easynote.Notepad.DBService;
import com.s3lab.easynote.R;
import com.s3lab.easynote.db.LoginUser;
import com.s3lab.easynote.ui.activity.PersonInfo;
import com.s3lab.easynote.ui.activity.Setting;
import com.s3lab.easynote.util.PhotoUtils;
import com.s3lab.easynote.widget.RoundImageView;

import java.util.ArrayList;
import java.util.List;

public class MyselfFragment extends Fragment implements View.OnClickListener{

    private MyselfViewModel MyselfViewModel;
    private ImageView setting;
    private LinearLayout info;
    private TextView info_name,info_account;
    private RoundImageView portrait;
    private LoginUser loginUser = LoginUser.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MyselfViewModel =
                ViewModelProviders.of(this).get(MyselfViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myself, container, false);
        info = (LinearLayout)root.findViewById(R.id.info);
        info_name = (TextView)root.findViewById(R.id.info_name);
        portrait = (RoundImageView)root.findViewById(R.id.portrait);
        info.setOnClickListener(this);
        initInfo();
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume(){
        super.onResume();
        initInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info:
                Intent intent1 = new Intent(getActivity(), PersonInfo.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.myself_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getActivity(), Setting.class);
        getActivity().startActivity(intent);
        // TODO Auto-generated method stub
        return super.onOptionsItemSelected(item);
    }

    private void initInfo(){
        info_name.setText(loginUser.getName());
        portrait.setImageBitmap((new PhotoUtils()).byte2bitmap(loginUser.getPortrait()));
    }

}

