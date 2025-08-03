package com.ap.server;

public class Server {
    public static void main(String[] args) {
        GameServer server = new GameServer();
        try {
            server.start();
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
