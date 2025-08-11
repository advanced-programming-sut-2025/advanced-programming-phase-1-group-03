package com.ap.network;

import com.ap.Configuration;
import com.ap.asset.MapAsset;
import com.ap.requests.*;
import com.ap.responses.*;
import com.ap.model.Gender;
import com.ap.utils.Crypto;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Sender {
    private Client client;
    public Sender(Client client) {
        this.client = client;
    }

    public void requestRoomsList() {
        var request = new RoomsListRequest();
        client.sendTCP(request);
    }

    public void createRoom(String name, String password, boolean isVisible) {
        var request = new CreateRoomRequest();
        request.name = name;
        request.password = password;
        request.isVisible = isVisible;
        client.sendTCP(request);
    }

    public SignupUserResponse signup(String username, String email,
                       String password, String confPassword,
                       String nickname, int securityQuestionId,
                       String securityQuestion, Gender gender) {

        var request = new SignupUserRequest();
        request.username = username;
        request.email = email;
        request.password = password;
        request.confPassword = confPassword;
        request.nickname = nickname;
        request.securityQuestionId = securityQuestionId;
        request.securityQuestion = securityQuestion;
        request.gender = gender;

        return sendMessageAndWaitForResponse(request, SignupUserResponse.class);
    }

    public <T> T sendMessageAndWaitForResponse(Object message, Class<T> responseType) {
        BlockingQueue<T> queue = new ArrayBlockingQueue<>(1);

        Listener tempListener = new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (responseType.isInstance(object)) {
                    queue.offer(responseType.cast(object));
                    client.removeListener(this);
                }
            }
        };

        client.addListener(tempListener);
        client.sendTCP(message);

        try {
            return queue.poll(Configuration.TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }

    public LoginResponse login(String username, String password, boolean stayLoggedIn) {
        var request = new LoginRequest();
        request.password = Crypto.hash(password);
        request.username = username;
        request.stayLoggedIn = stayLoggedIn;

        return sendMessageAndWaitForResponse(request, LoginResponse.class);
    }

    public GetSecurityIdResponse getSecurityQuestion(String username) {
        var request = new GetSecurityIdRequest(username);
        return sendMessageAndWaitForResponse(request, GetSecurityIdResponse.class);
    }

    public SecurityQPassResponse securityQPass(String securityQuestionAnswer, String username) {
        var request = new SecurityQPassRequest(username, securityQuestionAnswer);
        return sendMessageAndWaitForResponse(request, SecurityQPassResponse.class);
    }

    public ChangePasswordResponse changePassword(String username, String password, String secQAns) {
        var request = new ChangePasswordRequest(username, password, secQAns);
        return sendMessageAndWaitForResponse(request, ChangePasswordResponse.class);
    }

    public GetUserInfoResponse userInfo(String token) {
        var request = new GetUserInfoRequest(token);
        return sendMessageAndWaitForResponse(request, GetUserInfoResponse.class);
    }

    public ChangeAvatarResponse changeAvatar(String token, int i) {
        var request = new ChangeAvatarRequest(token, i);
        return sendMessageAndWaitForResponse(request, ChangeAvatarResponse.class);
    }

    public ChangeUsernameResponse changeUsername(String token, String username) {
        var request = new ChangeUsernameRequest(token, username);
        return sendMessageAndWaitForResponse(request, ChangeUsernameResponse.class);
    }

    public void changeNickname(String token, String nickname) {
        var request = new ChangeNicknameRequest(token, nickname);
        sendMessageAndWaitForResponse(request, ChangeNicknameResponse.class);
    }

    public ChangeEmailResponse changeEmail(String token, String email) {
        var request = new ChangeEmailRequest(token, email);
        return sendMessageAndWaitForResponse(request, ChangeEmailResponse.class);
    }

    public ChangePasswordViaTokenResponse changePassword(String token, String password) {
        var request = new ChangePasswordViaTokenRequest(token, password);
        return sendMessageAndWaitForResponse(request, ChangePasswordViaTokenResponse.class);
    }

    public IntroductionResponse introduce(String token) {
        var request = new IntroductionRequest(token);
        return sendMessageAndWaitForResponse(request, IntroductionResponse.class);
    }

    public JoinRoomResponse joinRoom(int roomId, String password) {
        var request = new JoinRoomRequest(roomId, password);
        return sendMessageAndWaitForResponse(request, JoinRoomResponse.class);
    }

    public AmIHostResponse amIHost() {
        var request = new AmIHostRequest();
        return sendMessageAndWaitForResponse(request, AmIHostResponse.class);
    }

    public void startGame() {
        var request = new StartGameRequest();
        client.sendTCP(request);
    }

    public SetMapResponse setMap(MapAsset map) {
        var request = new SetMapRequest(map);
        return sendMessageAndWaitForResponse(request, SetMapResponse.class);
    }

    public void sendMove(float dx, float dy, boolean isKeyDown) {
        var request = new MovePlayerRequest(dx, dy, isKeyDown);
        client.sendTCP(request);
    }

    public void applyItem(int index, int x, int y) {
        var request = new ApplyItemRequest(index, x, y);
        client.sendTCP(request);
    }

    public void buildGreenhouse() {
        client.sendTCP(new BuildGreenhouseRequest());
    }

    public ChatResponse sendChat(String user, String message) {
        var request = new ChatRequest(message,user);
        return sendMessageAndWaitForResponse(request, ChatResponse.class);
    }

}
