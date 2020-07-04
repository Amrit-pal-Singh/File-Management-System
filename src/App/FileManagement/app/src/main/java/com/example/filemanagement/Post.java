package com.example.filemanagement;

import com.google.gson.annotations.SerializedName;

public class Post{
    private String username;

    private String password;

    public Post(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

//public class Post {
//
//    private int userId;
//
//    private Integer id;
//
//    private String title;
//
//    @SerializedName("body")
//    private String text;
//
//    public Post(int userId, String title, String text) {
//        this.userId = userId;
//        this.title = title;
//        this.text = text;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public String getText() {
//        return text;
//    }
//}
