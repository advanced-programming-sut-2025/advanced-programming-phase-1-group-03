package com.ap;

import com.ap.asset.AtlasAsset;
import com.ap.asset.MapAsset;
import com.ap.asset.MusicAsset;
import com.ap.asset.SoundAsset;
import com.ap.component.Facing;
import com.ap.component.Graphic;
import com.ap.component.Player;
import com.ap.component.Transform;
import com.ap.model.*;
import com.ap.notifiers.*;
import com.ap.requests.*;
import com.ap.responses.*;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
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

        kryo.register(Season.class);
        kryo.register(Time.class);
        kryo.register(TimeNotifier.class);

        kryo.register(MapAsset.class);
        kryo.register(SetMapRequest.class);
        kryo.register(SetMapResponse.class);

        kryo.register(Weather.class);
        kryo.register(WeatherNotifier.class);

        kryo.register(AtlasAsset.class);
        kryo.register(Color.class);
        kryo.register(Graphic.class);
        kryo.register(Vector2.class);
        kryo.register(Player.class);
        kryo.register(Component.class);
        kryo.register(Component[].class);
        kryo.register(Facing.FacingDirection.class);
        kryo.register(Facing.class);
        kryo.register(Transform.class);
        kryo.register(ItemNotifier.class);

        kryo.register(Animation.PlayMode.class);
        kryo.register(AnimationNotifier.class);

        kryo.register(MapAsset.class);
        kryo.register(ChangeMapNotifier.class);

        kryo.register(CreateMapNotifier.class);

        kryo.register(MovePlayerRequest.class);

        kryo.register(MusicAsset.class);
        kryo.register(SoundAsset.class);
        kryo.register(PlayMusicNotifier.class);
        kryo.register(PlaySoundNotifier.class);

        kryo.register(RemoveEntityNotifier.class);
        kryo.register(ShowMessageNotifier.class);

        kryo.register(NetworkItemStack.class);
        kryo.register(NetworkItemStack[].class);
        kryo.register(InventoryNotifier.class);

        kryo.register(ApplyItemRequest.class);
        kryo.register(BuildGreenhouseMsgNotifier.class);

        kryo.register(BuildGreenhouseMsgNotifier.class);
        kryo.register(BuildGreenhouseRequest.class);
    }
}
