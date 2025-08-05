package com.ap.global;

import com.ap.global.model.Gender;
import com.ap.global.model.RoommateLobbyInfo;
import com.ap.global.requests.*;
import com.ap.global.responses.*;
import com.esotericsoftware.kryo.Kryo;

public class Registrator {
    public static void register(Kryo kryo) {
        kryo.register(IntroductionRequest.class);
        kryo.register(IntroductionResponse.class);
        
        kryo.register(RoomInfo.class);
        kryo.register(RoomInfo[].class);
        kryo.register(RoomsListResponse.class);
        kryo.register(RoomsListRequest.class);

        kryo.register(CreateRoomRequest.class);

        kryo.register(Gender.class);
        kryo.register(SignupUserRequest.class);
        kryo.register(SignupUserResponse.class);

        kryo.register(LoginRequest.class);
        kryo.register(LoginResponse.class);

        kryo.register(GetSecurityIdRequest.class);
        kryo.register(GetSecurityIdResponse.class);

        kryo.register(SecurityQPassRequest.class);
        kryo.register(SecurityQPassResponse.class);

        kryo.register(ChangePasswordRequest.class);
        kryo.register(ChangePasswordResponse.class);

        kryo.register(GetUserInfoRequest.class);
        kryo.register(GetUserInfoResponse.class);

        kryo.register(ChangeAvatarRequest.class);
        kryo.register(ChangeAvatarResponse.class);

        kryo.register(ChangeUsernameRequest.class);
        kryo.register(ChangeUsernameResponse.class);

        kryo.register(ChangeNicknameRequest.class);
        kryo.register(ChangeNicknameResponse.class);

        kryo.register(ChangeEmailRequest.class);
        kryo.register(ChangeEmailResponse.class);

        kryo.register(ChangePasswordViaTokenRequest.class);
        kryo.register(ChangePasswordViaTokenResponse.class);

        kryo.register(RoommateLobbyInfo.class);
        kryo.register(RoommateLobbyInfo[].class);
        kryo.register(RoommatesInfoLobbyRequest.class);
        kryo.register(RoommatesInfoLobbyResponse.class);

        kryo.register(JoinRoomRequest.class);
        kryo.register(JoinRoomResponse.class);

        kryo.register(AmIHostRequest.class);
        kryo.register(AmIHostResponse.class);

        kryo.register(StartGameRequest.class);
        kryo.register(StartGameResponse.class);
    }
}
