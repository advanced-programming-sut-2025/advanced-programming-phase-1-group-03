package com.ap.input;

import com.badlogic.gdx.Input;

public enum Command {
    Right(Input.Keys.RIGHT, Input.Keys.D),
    Left(Input.Keys.LEFT, Input.Keys.A),
    Down(Input.Keys.DOWN, Input.Keys.S),
    Up(Input.Keys.UP, Input.Keys.W),
    OpenInventory(Input.Keys.E),
    OpenCrafting(Input.Keys.B),
    OpenCooking(Input.Keys.C),
    Click(),
    OpenCheatCode(Input.Keys.F3),
    Place(Input.Keys.SPACE),
    Talk(Input.Keys.P),
    OpenLeaderBoard(Input.Keys.L),
    OpenTradeStarter(Input.Keys.T),
    ;

    private final int[] correspondingKeys;
    Command(int... correspondingKeys) {
        this.correspondingKeys = correspondingKeys;
    }

    public int[] getCorrespondingKeys() {
        return correspondingKeys;
    }
}
