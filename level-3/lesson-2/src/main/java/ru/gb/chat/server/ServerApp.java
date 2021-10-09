package ru.gb.chat.server;

import static ru.gb.chat.server.JdbcApp.*;

public class ServerApp {

    public static void main(String[] args) {
        try {
            connect();
            new Server();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
    }
}
