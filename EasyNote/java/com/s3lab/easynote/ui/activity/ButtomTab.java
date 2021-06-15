package com.s3lab.easynote.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.s3lab.easynote.R;
import com.s3lab.easynote.util.ActivityCollector;
import com.s3lab.easynote.util.DataGenerator;

public class ButtomTab extends AppCompatActivity {
    private TabLayout tab_layout;
    private Fragment[] framensts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.buttom_tab);
        framensts = DataGenerator.getFragments();
        initView();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    private void initView() {
        tab_layout = (TabLayout) findViewById(R.id.bottom_tab_layout);
        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==2){
                    Intent intent = new Intent(ButtomTab.this, AddArticle.class);
                    startActivity(intent);
                }else {
                    onTabItemSelected(tab.getPosition());
                    for (int i = 0; i < tab_layout.getTabCount(); i++) {
                        if(i==2) continue;
                        View view = tab_layout.getTabAt(i).getCustomView();
                        ImageView icon = (ImageView) view.findViewById(R.id.tab_content_image);
                        TextView text = (TextView) view.findViewById(R.id.tab_content_text);

                        if (i == tab.getPosition()) {
                            icon.setImageResource(DataGenerator.mTabResPressed[i]);
                            text.setTextColor(getResources().getColor(android.R.color.black));
                        } else {
                            icon.setImageResource(DataGenerator.mTabRes[i]);
                            text.setTextColor(getResources().getColor(android.R.color.darker_gray));
                        }
                    }
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        for(int i = 0; i<5; i++){
            tab_layout.addTab(tab_layout.newTab().setCustomView(DataGenerator.getTabView(this,i)));
        }
        tab_layout.getTabAt(0).select();
    }

    private void onTabItemSelected(int position){
        Fragment frag = null;
        switch (position){
            case 0:
                frag = framensts[0];
                break;
            case 1:
                frag = framensts[1];
                break;

            case 3:
                frag = framensts[2];
                break;
            case 4:
                frag = framensts[3];
                break;
        }
        if(frag!=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,frag).commit();
        }
    }

    @Override
    public void onBackPressed(){
        moveTaskToBack(true);
    }
}

