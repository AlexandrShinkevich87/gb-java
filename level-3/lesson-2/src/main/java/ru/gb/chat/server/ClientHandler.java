package ru.gb.chat.server;

import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;

import static ru.gb.chat.server.JdbcApp.isFree;

@Slf4j
public class ClientHandler {
    private Server server;
    private Socket socket;
    private String username;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    authentication(server);
                    readMessage(server);
                } catch (IOException | SQLException e) {
                    log.error(e.getMessage(), e);
                } finally {
                    closeConnection();
                }
            }).start();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void authentication(Server server) throws IOException, SQLException {
        /*
        // Поток контроля, что пользователь авторизуется за заданное время
        Thread authorityControlThread = new Thread(() -> {
            try {
                Thread.sleep(120000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
//            if (this.nick.equals("")) {
//                sendMessage("You were disconnected due the idleness\n");
//                isIdle = true;
//            }
        });
        authorityControlThread.setDaemon(true);
        authorityControlThread.start();
        */
        while (true) {
            String inputMessage = in.readUTF();
            log.info(inputMessage);
            if (inputMessage.startsWith("/auth ")) {
                username = inputMessage.split("\\s+", 2)[1];
                log.info(this.username);
                if (isFree(username) == 0) {
                    if (server.subscribe(this, username)) {
                        sendMessage("/authok");
                        break;
                    } else if (isFree(username) == 1) {
                        sendMessage("/authNok" + username);
                        log.info(" NOK ");
                    }
                } else {
                    sendMessage("/authNok");
                }
            } else {
                sendMessage("SERVER: Вам необходимо авторизоваться");
            }
        }
    }

    private void readMessage(Server server) throws IOException {
        while (true) {
            String inputMessage = in.readUTF();
            if (inputMessage.startsWith("//")) {
                continue;
            } else if (inputMessage.startsWith("/off")) {
                log.info(inputMessage);
                username = inputMessage.split("\\s+", 2)[1];
                server.unsubscribe(this, username);
                log.info("{} отключился ", username);
                return;
            }
            server.broadcastMessage(username + ": " + inputMessage);
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void closeConnection() {
        server.unsubscribe(this, username);
        try {
            in.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        try {
            out.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        try {
            socket.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
