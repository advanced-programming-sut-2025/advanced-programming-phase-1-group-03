package com.ap.global;

import com.ap.global.requests.CreateRoomRequest;
import com.ap.global.requests.IntroductionRequest;
import com.ap.global.requests.RoomsListRequest;
import com.ap.global.responses.IntroductionResponse;
import com.ap.global.responses.RoomsListResponse;
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
    }
}
