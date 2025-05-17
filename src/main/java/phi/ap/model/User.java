package phi.ap.model;

import phi.ap.model.enums.Gender;

import java.util.regex.Pattern;

public class  User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private Gender gender;
    private int gamePlayed = 0;
    private int maxGold = 0;
    private SecurityQuestion securityQuestion;
    private Integer gameJoinedId = null;

    public void setGamePlayed(int gamePlayed) {
        this.gamePlayed = gamePlayed;
    }

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

    public boolean setUsername(String username) {
        if(!User.usernameValidity(username))
            return false;
        this.username = username;
        return true;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean setEmail(String email) {
        if(!User.emailValidity(email))
            return false;
        this.email = email;
        return true;
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
    public static boolean usernameValidity(String username){
        String regex = "[A-Za-z0-9\\-]*";
        return Pattern.compile(regex).matcher(username).matches();
    }
    public static boolean emailValidity(String email){
        String regex = "^(?![?><,\"';:\\\\\\/|\\]\\[}{+=)(*&^%$#!])(?!.*\\.\\.)(?=[a-zA-Z0-9])[a-zA-Z0-9.\\-_]" +
                "*[a-zA-Z0-9]@(?=[a-zA-Z0-9])[a-zA-Z0-9\\-]*[a-zA-Z0-9]\\.[a-zA-Z]{2,}$";
        return Pattern.compile(regex).matcher(email).matches();
    }
    public static Result<String> passwordValidity(String password){
        if(password.length() < 8)
            return new Result<>(false, "password must be at least 8 characters");
        if(!password.matches(".*\\d.*"))
            return new Result<>(false, "password must contain at least one digit");
        if(!password.matches(".*[A-Z].*"))
            return new Result<>(false, "password must contain at least one uppercase letter");
        if(!password.matches(".*[a-z].*"))
            return new Result<>(false, "password must contain at least one lowercase letter");
        if(!password.matches(".*[?><,\"';:\\\\/|}{+=)(*&^%@$#!].*"))
            return new Result<>(false, "password must contain at least one special character");
        return new Result<>(true, "");
    }

    public Integer getGameJoinedId() {
        return gameJoinedId;
    }

    public void setGameJoinedId(Integer gameJoinedId) {
        this.gameJoinedId = gameJoinedId;
    }

    @Override
    public boolean equals(Object obj) {
        return ((User)obj).getUsername().equals(username);
    }
}
