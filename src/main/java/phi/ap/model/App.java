package phi.ap.model;

public class App {
    private static User stayLoggedInUser = null;


    public static User getStayLoggedInUser() {
        return stayLoggedInUser;
    }

    public static void setStayLoggedInUser(User stayLoggedInUser) {
        App.stayLoggedInUser = stayLoggedInUser;
    }
}
