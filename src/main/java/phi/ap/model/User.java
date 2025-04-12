package phi.ap.model;

import phi.ap.model.enums.Gender;

public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Gender gender;
    private int gamePlayed;
    private int maxGold;
    private SecurityQuestion securityQuestion;


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public int getGamePlayed() {
        return gamePlayed;
    }

    public int getMaxGold() {
        return maxGold;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setMaxGold(int maxGold) {
        this.maxGold = maxGold;
    }

    public void increaseGamePlayed() {
        gamePlayed++;
    }

    public SecurityQuestion getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(SecurityQuestion securityQuestion) {
        this.securityQuestion = securityQuestion;
    }
}
