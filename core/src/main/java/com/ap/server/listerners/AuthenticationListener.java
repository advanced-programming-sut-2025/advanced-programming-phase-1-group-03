package com.ap.server.listerners;

import com.ap.global.requests.*;
import com.ap.global.responses.*;
import com.ap.global.utils.RegistrationValidator;
import com.ap.server.database.SqliteConnection;
import com.ap.server.database.UserLoader;
import com.ap.server.utils.TokenManager;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class AuthenticationListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        if(object instanceof SignupUserRequest request) {
            var result = UserLoader.signupUser(request.username, request.nickname, request.email, request.password,
                    request.confPassword, request.gender, request.securityQuestionId, request.securityQuestion);
            connection.sendTCP(new SignupUserResponse(result.isSuccess(), result.getData()));
        } else if(object instanceof LoginRequest request) {
            var validator = new RegistrationValidator();
            LoginResponse response;
            if(validator.checkLogin(request.username, request.password, SqliteConnection.instance)) {
                response = new LoginResponse("Login was successful", true,
                        TokenManager.generateToken(request.username, request.stayLoggedIn));
            } else {
                response = new LoginResponse("Invalid username or password", false, "");
            }
            connection.sendTCP(response);
        } else if(object instanceof GetSecurityIdRequest request) {
            var response = new GetSecurityIdResponse(UserLoader.getSecurityQuestionId(request.username));
            connection.sendTCP(response);
        } else if(object instanceof SecurityQPassRequest request) {
            var response = new SecurityQPassResponse(UserLoader.passSecurityQ(request.username, request.securityQuestionAnswer));
            connection.sendTCP(response);
        } else if(object instanceof ChangePasswordRequest request) {
            var result = UserLoader.changePassword(request.username, request.newPassword, request.secQAns);
            var response = new ChangePasswordResponse(result.getData(), result.isSuccess());
            connection.sendTCP(response);
        } else if(object instanceof GetUserInfoRequest request) {
            String username = TokenManager.verifyAndGetUsername(request.token);
            GetUserInfoResponse response;
            if(username == null) {
                response = new GetUserInfoResponse(false);
            } else {
                response = new GetUserInfoResponse(true, username,
                        UserLoader.getUserMaximumCoin(username),
                        UserLoader.getUserGamesCount(username),
                        UserLoader.getUserEmail(username),
                        UserLoader.getUserNickname(username),
                        UserLoader.getUserAvatarIndex(username));
            }
            connection.sendTCP(response);
        } else if(object instanceof ChangeAvatarRequest request) {
            String username = TokenManager.verifyAndGetUsername(request.token);
            if(username == null) {
                return;
            }
            UserLoader.changeAvatarIndex(username, request.newAvatarIndex);
            connection.sendTCP(new ChangeAvatarResponse());
        } else if(object instanceof ChangeUsernameRequest request) {
            String username = TokenManager.verifyAndGetUsername(request.token);
            var result = UserLoader.changeUsername(username, request.newUsername);
            connection.sendTCP(new ChangeUsernameResponse(result.getData(), result.isSuccess()));
        }
    }
}
