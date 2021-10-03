package ru.gb.chat.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.*;

import static ru.gb.chat.server.JdbcApp.*;

@Slf4j
public class Server {
    private List<ClientHandler> clients;

    public Server() {
        try {
            this.clients = new ArrayList<>();
            try (ServerSocket serverSocket = new ServerSocket(8189)) {
                log.info("Сервер запущен. Ожидаем подключение клиентов..");
                while (true) {
                    Socket socket = serverSocket.accept();
                    log.info("Подключился новый клиент");
                    new ClientHandler(this, socket);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    public synchronized boolean subscribe(ClientHandler c, String username) {
        try {
            setActive(username);
            clients.add(c);
            this.broadcastMessages(" Пользователь " + username + " вошёл в чат ");
            return true;
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public synchronized void unsubscribe(ClientHandler c, String username) {
        try {
            setInactive(username);
            clients.remove(c);
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        this.broadcastMessages(" Пользователь " + username + " вышел из чата ");
    }

    public synchronized void broadcastMessage(String message) {
        String username = message.substring(0, message.indexOf(':'));
        for (ClientHandler c : clients) {
            try {
                addMessage(username, message);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            c.sendMessage(message);
        }
    }

    public synchronized void broadcastMessages(String message) {
        for (ClientHandler c : clients) {
            c.sendMessage(message);
        }
    }
}
