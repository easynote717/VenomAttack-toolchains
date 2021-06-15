package com.s3lab.easynote.db;

import android.app.Application;

import com.s3lab.easynote.db.model.Article;
import com.s3lab.easynote.db.model.User;
import org.litepal.LitePal;
import java.util.List;

public class LoginUser extends Application {
    private static LoginUser login_user = new LoginUser();
    private static User _user;
    private long id;
    private String name;
    private byte[] portrait;
    private String region;
    private String gender;
    private String brithday;
    public static LoginUser getInstance(){
        return login_user;
    }
    public User getUser(){
        return _user;
    }

    public void update(){
        if(_user.getId()==this.id){
            _user.setName(this.name);
            _user.setPortrait(this.portrait);
            _user.setGender(this.gender);
            _user.setRegion(this.region);
            _user.setBrithday(this.brithday);
            _user.update(_user.getId());
        }
    }

    public void reinit(){
        login_user.id = _user.getId();
        login_user.name = _user.getName();
        login_user.portrait = _user.getPortrait();
        login_user.region = _user.getRegion();
        login_user.gender = _user.getGender();
        login_user.brithday = _user.getBrithday();
    }

    public boolean login(User user) {
        _user = user;
        login_user.id = user.getId();
        login_user.name = user.getName();
        login_user.portrait = user.getPortrait();
        login_user.region = user.getRegion();
        login_user.gender = user.getGender();
        login_user.brithday = user.getBrithday();
        return true;
    }

    public static boolean logout(){
        if(login_user.id == -1) return false;
        login_user.id = -1;
        login_user.name = null;
        login_user.portrait = null;
        login_user.region = null;
        login_user.gender = null;
        login_user.brithday = null;
        return true;
    }

    public List<Article> getArticleListFromLitePal(){
        return LitePal.where("user_id=?",String.valueOf(this.id)).find(Article.class);
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", portrait ='" + portrait + '\'' +
                ", region='" + region + '\'' +
                ", gender='" + gender + '\'' +
                ", brithday='" + brithday + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPortrait() {
        return portrait;
    }

    public void setPortrait(byte[] portrait) {
        this.portrait = portrait;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }
}
