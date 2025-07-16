package com.ap.ui.model;

import com.ap.GdxGame;
import com.ap.model.Gender;

public class SignupViewModel extends ViewModel {

    private Gender gender;

    public SignupViewModel(GdxGame game) {
        super(game);
    }
    
    public void submit(
            String username, String email, String password, String confPassword, String nickname
    ) {

    }

    public void femaleButtonClicked() {
        gender = Gender.Female;
    }

    public void maleButtonClicked() {
        gender = Gender.Male;

    }
}
