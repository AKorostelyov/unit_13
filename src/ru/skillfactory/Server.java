package ru.skillfactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static ArrayList<Client> clients = new ArrayList<>();
    private ServerSocket serverSocket;

    public Server(int port) throws IOException {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    /**
     * отправка уведомление о присоединении клиента к чату
     *
     * @param clientName имя клиента
     */
    public static void sendWelcomeMsg(String clientName) {
        sendMsg("[ROOM]: " + clientName + " is joined room");
    }

    /**
     * Отправка сообщения от клиента в чат
     *
     * @param msg        сообщение
     * @param clientName имя клиента
     */
    public static void sendMsgToAll(String msg, String clientName) {
        sendMsg("[" + clientName + "]: " + msg);
    }

    /**
     * Отправка уведомления о выходе клиента из чата
     *
     * @param clientName имя клиента
     */
    public static void sendExitMsg(String clientName) {
        sendMsg("[ROOM]: Client " + clientName + " leaving room");

    }

    /**
     * Отправка сообщения всем в чате
     *
     * @param msg сообщение
     */
    private static void sendMsg(String msg) {
        for (Client client : clients) {
            client.sendMsg(msg);
        }
    }

    /**
     * Запуск сервера
     *
     * @throws IOException
     */
    public void run() throws IOException {
        while (true) {
            System.out.println("Waiting...");
            // ждем клиента из сети
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");
            // создаем клиента на своей стороне
            Client client = new Client(socket);
            // запускаем поток
            clients.add(client);
            new Thread(client).start();
        }
    }
}
