package com.ap;

import com.ap.asset.AtlasAsset;
import com.ap.asset.MapAsset;
import com.ap.asset.MusicAsset;
import com.ap.asset.SoundAsset;
import com.ap.component.*;
import com.ap.model.*;
import com.ap.notifiers.*;
import com.ap.packet.PlayerInfo;
import com.ap.packet.TradeRoomStarter;
import com.ap.packet.VoiceNetData;
import com.ap.requests.*;
import com.ap.responses.*;
import com.ap.rmi.Ask;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.rmi.ObjectSpace;

public class Registrator {
    public static void register(Kryo kryo) {

        ObjectSpace.registerClasses(kryo);

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

        kryo.register(ReactionRequest.class);

        kryo.register(ChangeSeasonNotifier.class);

        kryo.register(ChatRequest.class);
        kryo.register(ChatResponse.class);
        kryo.register(ChatNotifier.class);
        kryo.register(com.ap.packet.LeaderBoardInfo.class);

        kryo.register(LeaderBoardResponse.class);
        kryo.register(LeaderBoardRequest.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(VoteRequest.class);

        kryo.register(RoommatesInfoRequest.class);
        kryo.register(RoommatesInfoResponse.class);
        kryo.register(PlayerInfo.class);

        kryo.register(PopupNotifier.class);
        kryo.register(VoteNotifier.class);
        kryo.register(InventoryMoveRequest.class);
        kryo.register(AnswerResponse.class);

        kryo.register(addCookingItem.class);
        kryo.register(addCookingRequest.class);
        kryo.register(CookingRecipeRequest.class);
        kryo.register(IngredientRequest.class);
        kryo.register(InventoryMoveRequest.class);
        kryo.register(IsFoodRequest.class);
        kryo.register(ReduceIngredientRequest.class);

        kryo.register(GetActiveTradeRequest.class);
        kryo.register(GetActiveTradeResponse.class);
        kryo.register(TradeStartRequest.class);
        kryo.register(TradeRoomStarter.class);

        kryo.register(TradeRoomStarterNotifier.class);
        kryo.register(TradeCommandRequest.class);
        kryo.register(TradeCommandNotifier.class);
        kryo.register(TradeStarterCancel.class);
        kryo.register(TradeStarterReject.class);

        kryo.register(TradeHistory.class);
        kryo.register(TradeHistoryRequest.class);
        kryo.register(TradeHistoryResponse.class);

        kryo.register(short[].class);
        kryo.register(VoiceNetData.class);

        kryo.register(QuitRoomRequest.class);
        kryo.register(SendGoldNotifier.class);

        kryo.register(StoreMenuOpenOrExitNotifier.class);
        kryo.register(Menus.class);
        kryo.register(BuyItemRequest.class);
        kryo.register(BuyItemResponse.class);

        kryo.register(PlaceCarrierRequest.class);
        kryo.register(Carrier.class);

        kryo.register(Ask.class);
        kryo.register(EnergyNotifier.class);

        kryo.register(RemoveItemInventoryRequest.class);

        kryo.register(Shadow.class);
    }

}
