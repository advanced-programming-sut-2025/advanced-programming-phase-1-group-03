package com.ap.global;

import com.ap.global.requests.IntroductionRequest;
import com.ap.global.responses.IntroductionResponse;
import com.esotericsoftware.kryo.Kryo;

public class Registrator {
    public static void register(Kryo kryo) {
        kryo.register(IntroductionRequest.class);
        kryo.register(IntroductionResponse.class);
    }
}
