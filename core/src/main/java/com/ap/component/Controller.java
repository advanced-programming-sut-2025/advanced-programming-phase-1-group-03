package com.ap.component;

import com.ap.input.Command;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.ComponentMapper;

import java.util.ArrayList;
import java.util.List;

public class Controller implements Component {
    // We use mapper for get components from entity in O(1) instead of O(log)
    public final static ComponentMapper<Controller> mapper = ComponentMapper.getFor(Controller.class);

    private final List<Command> pressedCommands;
    private final List<Command> releasedCommands;

    public Controller() {
        pressedCommands = new ArrayList<>();
        releasedCommands = new ArrayList<>();
    }


    public List<Command> getPressedCommands() {
        return pressedCommands;
    }

    public List<Command> getReleasedCommands() {
        return releasedCommands;
    }
}
