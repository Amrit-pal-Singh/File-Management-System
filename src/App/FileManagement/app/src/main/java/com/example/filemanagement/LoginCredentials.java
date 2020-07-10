package com.example.filemanagement;

import com.google.gson.annotations.SerializedName;

public class LoginCredentials {

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("token")
    private String token;

    @SerializedName("email")
    private String email;

    @SerializedName("admin_permissions")
    private Boolean admin_permissions = false;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("roles")
    private Object object;

    public LoginCredentials (String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public String getEmail() {
        return email;
    }

    public boolean isAdmin_permissions() {
        return admin_permissions;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public Object getObject() {
        return object;
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
