package com.ap.notifiers;

public class ChatNotifier {
    public String message;
    public boolean isPrivate;
    public String senderName;

    public ChatNotifier(String message, boolean isPrivate, String senderName) {
        this.message = message;
        this.isPrivate = isPrivate;
        this.senderName = senderName;
    }

    public ChatNotifier() {
    }
}
