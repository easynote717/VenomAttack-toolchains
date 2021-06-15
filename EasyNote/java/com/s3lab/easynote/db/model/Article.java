package com.s3lab.easynote.db.model;

import org.litepal.crud.LitePalSupport;

public class Article extends LitePalSupport {
    private long id;
    private String title;
    private String text;
    private int state;
    private User author;
    private long author_id;

    public Article(String title, String text, int state, User author, long author_id) {
        this.title = title;
        this.text = text;
        this.state = state;
        this.author = author;
        this.author_id = author_id;
    }

    public Article(String title, String text, int state, User author) {
        this.title = title;
        this.text = text;
        this.state = state;
        this.author = author;
        this.author_id = author.getId();
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", state=" + state +
                ", author_id=" + author_id +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(long author_id) {
        this.author_id = author_id;
    }
}
