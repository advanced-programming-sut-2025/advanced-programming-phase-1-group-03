package com.ap.notifiers;

public class RemoveEntityNotifier {
    public int engineId;
    public int entityId;

    public RemoveEntityNotifier() {
    }

    public RemoveEntityNotifier(int engineId, int entityId) {
        this.engineId = engineId;
        this.entityId = entityId;
    }
}
