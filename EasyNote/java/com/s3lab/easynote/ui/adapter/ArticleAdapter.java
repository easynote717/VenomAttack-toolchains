package com.s3lab.easynote.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import com.s3lab.easynote.R;
import com.s3lab.easynote.db.model.Article;
import com.s3lab.easynote.widget.RoundImageView;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private List<Article> mArticleList;
    private OnMyItemClickListener listener;

    public interface OnMyItemClickListener {
        void myClick(View v, int position);
    }

    public void setOnMyItemClickListener(OnMyItemClickListener onMyItemClickListener) {
        this.listener = onMyItemClickListener;
    }

    public ArticleAdapter(List<Article> articleList){
        mArticleList = articleList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_layout, parent,false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        Article article = mArticleList.get(position);
        holder.header.setImageResource(R.mipmap.default_header);
        holder.text.setText(article.getText());
    }

    @Override
    public int getItemCount(){
        return mArticleList.size();
    }

    public void addItem(Article message, int position){
        mArticleList.add(message);
        notifyItemChanged(position);
    }

    public void removeItem(int position){
        mArticleList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        RoundImageView header;
        TextView author, time, text;
        ImageView share,comment,like;
        public ViewHolder(View view){
            super(view);
            header = (RoundImageView)view.findViewById(R.id.header);
            author = (TextView)view.findViewById(R.id.title);
            time = (TextView)view.findViewById(R.id.time);
            text = (TextView)view.findViewById(R.id.text);
            share = (ImageView) view.findViewById(R.id.share);
            comment = (ImageView) view.findViewById(R.id.comment);
            like = (ImageView) view.findViewById(R.id.like);
        }
    }

}