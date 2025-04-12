package phi.ap.model;

public class SecurityQuestion {
    private final String question;
    private final String answer;

    public SecurityQuestion(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
