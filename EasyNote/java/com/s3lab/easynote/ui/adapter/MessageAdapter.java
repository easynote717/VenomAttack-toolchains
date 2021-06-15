package com.s3lab.easynote.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.s3lab.easynote.R;
import com.s3lab.easynote.db.model.Message;
import com.s3lab.easynote.widget.RoundImageView;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    private List<Message> mMessageList;
    private OnMyItemClickListener listener;

    public interface OnMyItemClickListener {
        void myClick(View v, int position);
    }

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        this.listener = onMyItemClickListener;
    }

    public MessageAdapter(List<Message> messageList){
        mMessageList = messageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_layout,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        Message message = mMessageList.get(position);
        holder.header.setImageResource(R.mipmap.default_header);
        holder.title.setText(message.getTitle());
        holder.text.setText(message.getText());
        if(listener!=null){
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.myClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemCount(){
        return mMessageList.size();
    }

    public void addItem(Message message, int position){
        mMessageList.add(message);
        notifyItemChanged(position);
    }

    public void removeItem(int position){
        mMessageList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        RoundImageView header;
        TextView title;
        TextView text;
        Button delete;
        public ViewHolder(View view){
            super(view);
            header = (RoundImageView)view.findViewById(R.id.header);
            title = (TextView)view.findViewById(R.id.title);
            text = (TextView)view.findViewById(R.id.text);
            delete = (Button)view.findViewById(R.id.delete_item);
        }
    }
}
