package com.ap.global.requests;

import com.ap.global.model.Gender;

public class SignupUserRequest {
    public String username;
    public String email;
    public String password;
    public String confPassword;
    public String nickname;
    public int securityQuestionId;
    public String securityQuestion;
    public Gender gender;
}
