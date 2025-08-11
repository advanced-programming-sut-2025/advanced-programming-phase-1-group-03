package com.ap.requests;

import com.ap.model.Gender;

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
