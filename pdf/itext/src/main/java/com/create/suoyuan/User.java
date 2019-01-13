package com.create.suoyuan;

/**
 * 
 * @author cck
 */
public class User {
    
    // 昵称
    private String nickname;
    // 签名档
    private String signature;
    // 头像
    private String avatar;
    
    public User(String nickname, String signature, String avatar) {
        super();
        this.nickname = nickname;
        this.signature = signature;
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
