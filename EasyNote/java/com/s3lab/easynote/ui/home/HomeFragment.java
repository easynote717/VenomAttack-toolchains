package com.s3lab.easynote.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.s3lab.easynote.Notepad.DBService;
import com.s3lab.easynote.Notepad.ShowActivity;
import com.s3lab.easynote.Notepad.Values;
import com.s3lab.easynote.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends ListFragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private HomeViewModel homeViewModel;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> adapter1;
    DBService myDb;
    private Button mBtnAdd;
    private ListView lv_note;
    private Button button;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        mBtnAdd = (Button)root.findViewById(R.id.add_note);
        Log.e("button:", String.valueOf(mBtnAdd));
        lv_note = (ListView)root.findViewById(android.R.id.list);
        Log.e("lv_note : ", String.valueOf(lv_note));

        List<String> data1 = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            data1.add("smyh" + i);
        }

        adapter1 = new ArrayAdapter<String>(getContext(),
                R.layout.note_item, data1);
        lv_note.setAdapter(adapter1);

        init();
        lv_note.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeFragment.super.onListItemClick((ListView) parent,view,position,id);
                Intent intent = new Intent(getActivity(), ShowActivity.class);
                Values values = (Values) lv_note.getItemAtPosition(position);

                intent.putExtra(DBService.TITLE,values.getTitle().trim());
                intent.putExtra(DBService.CONTENT,values.getContent().trim());
                intent.putExtra(DBService.TIME,values.getTime().trim());
                intent.putExtra(DBService.ID,values.getId().toString().trim());
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        myDb = new DBService(this.getContext());
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            data.add("smyh" + i);
        }
        adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.note_editor, data);
        setListAdapter(adapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater inflater) {
        inflater.inflate(R.menu.home_toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        Toast.makeText(getActivity(), "index is && menu text is "+item.getTitle(), 1000).show();
        return super.onOptionsItemSelected(item);
    }
    public void init(){
        List<Values> valuesList = new ArrayList<>();
        SQLiteDatabase db = myDb.getReadableDatabase();
        Cursor cursor = db.query(DBService.TABLE,null,null,
                null,null,null,null);
        if(cursor.moveToFirst()){
            Values values;
            while (!cursor.isAfterLast()){
                values = new Values();
                values.setId(Integer.valueOf(cursor.getString(cursor.getColumnIndex(DBService.ID))));
                values.setTitle(cursor.getString(cursor.getColumnIndex(DBService.TITLE)));
                values.setContent(cursor.getString(cursor.getColumnIndex(DBService.CONTENT)));
                values.setTime(cursor.getString(cursor.getColumnIndex(DBService.TIME)));
                valuesList.add(values);
                cursor.moveToNext();
            }
        }
        cursor.close();
        db.close();
        final MyBaseAdapter myBaseAdapter = new MyBaseAdapter(valuesList,getContext(),R.layout.note_item);
        setListAdapter(myBaseAdapter);
        lv_note.setAdapter(myBaseAdapter);
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.s3lab.easynote.Notepad.EditActivity.class);
                startActivity(intent);
            }
        });
        lv_note.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeFragment.super.onListItemClick((ListView) parent,view,position,id);
                Intent intent = new Intent(getActivity(), ShowActivity.class);
                Values values = (Values) lv_note.getItemAtPosition(position);
                intent.putExtra(DBService.TITLE,values.getTitle().trim());
                intent.putExtra(DBService.CONTENT,values.getContent().trim());
                intent.putExtra(DBService.TIME,values.getTime().trim());
                intent.putExtra(DBService.ID,values.getId().toString().trim());

                startActivity(intent);
            }
        });
        lv_note.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final Values values = (Values) lv_note.getItemAtPosition(position);
                new AlertDialog.Builder(getActivity())
                        .setTitle("Tip")
                        .setMessage("Do you want to delete it?")
                        .setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SQLiteDatabase db = myDb.getWritableDatabase();
                                        db.delete(DBService.TABLE,DBService.ID+"=?",new String[]{String.valueOf(values.getId())});
                                        db.close();
                                        myBaseAdapter.removeItem(position);
                                        lv_note.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                myBaseAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                })
                        .setNegativeButton("no",null).show();
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(getActivity(), ShowActivity.class);
        Values values = (Values) lv_note.getItemAtPosition(position);
        intent.putExtra(DBService.TITLE,values.getTitle().trim());
        intent.putExtra(DBService.CONTENT,values.getContent().trim());
        intent.putExtra(DBService.TIME,values.getTime().trim());
        intent.putExtra(DBService.ID,values.getId().toString().trim());
        startActivity(intent);
    }

    public class MyBaseAdapter extends BaseAdapter {
        private List<Values> valuesList;
        private Context context;
        private LayoutInflater layoutInflater;
        public MyBaseAdapter(List<Values> valuesList, Context context, int layoutId) {
            this.valuesList = valuesList;
            this.context = context;
            this.layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (valuesList != null && valuesList.size() > 0)
                return valuesList.size();
            else
                return 0;
        }

        @Override
        public Object getItem(int position) {
            if (valuesList != null && valuesList.size() > 0)
                return valuesList.get(position);
            else
                return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(
                        getActivity()).inflate(R.layout.note_item, parent,
                        false);
                viewHolder = new ViewHolder();
                viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.content = convertView.findViewById(R.id.tv_content);
                viewHolder.time = (TextView) convertView.findViewById(R.id.tv_time);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String title = valuesList.get(position).getTitle();
            String content = valuesList.get(position).getContent();
            viewHolder.title.setText(title);
            viewHolder.content.setText(content);
            viewHolder.time.setText(valuesList.get(position).getTime());
            return convertView;
        }

        public void removeItem(int position){
            this.valuesList.remove(position);
        }

    }
    class ViewHolder{
        TextView title;
        TextView content;
        TextView time;
    }
}
