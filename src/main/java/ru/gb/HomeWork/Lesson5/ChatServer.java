package ru.gb.HomeWork.Lesson5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.request.StepRequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {
    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
        try (ServerSocket server = new ServerSocket(8888)) {
            System.out.println("Сервер запущен");

            while (true) {
                System.out.println("Ждем клиентского подключения");
                Socket client = server.accept();
                ClientHandler clientHandler = new ClientHandler(client, clients);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Ошибка во время работы сервера: " + e.getMessage());
        }
    }

    public static class ClientHandler implements Runnable {

        private final Socket client;
        private final Scanner in;
        private final PrintWriter out;
        private final Map<String, ClientHandler> clients;
        private String clientLogin;

        public ClientHandler(Socket client, Map<String, ClientHandler> clients) throws IOException {
            this.client = client;
            this.clients = clients;
            this.in = new Scanner(client.getInputStream());
            this.out = new PrintWriter(client.getOutputStream(), true);
        }

        @Override
        public void run() {
            System.out.println("подключен новый клиент");
            try {
                String loginRequest = in.nextLine();
                LoginRequest request = objectMapper.reader().readValue(loginRequest, LoginRequest.class);
                this.clientLogin = request.getLogin();
            } catch (IOException e) {
                System.err.println("не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
                String unsuccessfulResponse = createLoginResponse(false);
                out.println(unsuccessfulResponse);
                doClose();
                return;
            }
            System.out.println("Запрос от клиента: " + clientLogin);
            if (clients.containsKey(clientLogin)) {
                String unsuccessfulResponse = createLoginResponse(false);
                out.println(unsuccessfulResponse);
                doClose();
                return;
            }
            clients.put(clientLogin, this);
            String successfulLoginResponse = createLoginResponse(true);
            out.println(successfulLoginResponse);
            while (true) {
                String msgFromClient = in.nextLine();
                final String type;
                try {
                    AbstractRequest request = objectMapper.reader().readValue(msgFromClient, AbstractRequest.class);
                    type = request.getType();
                } catch (IOException e) {
                    System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
                    sendMessage("Не удалось прочитать сообщение: " + e.getMessage());
                    continue;
                }
                if (SendMessageRequest.TYPE.equals(type)) {
                    final SendMessageRequest request;
                    try {
                        request = objectMapper.reader().readValue(msgFromClient, SendMessageRequest.class);
                        ClientHandler clientHandler = clients.get(request.getRecipient());
                        if (clientHandler == null) {
                            sendMessage("Клиент с логином [" + request.getRecipient() + "] не найден");
                        } else {
                            clientHandler.sendMessage(request.getMessage());
                        }
                    } catch (IOException e) {
                        System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
                        sendMessage("Не удалось прочитать сообщение SendMessageRequest: " + e.getMessage());
//                        continue;
                    }

                } else if (BroadcastRequest.TYPE.equals(type)) {
                    final BroadcastRequest request;
                    try {
                        request = objectMapper.reader().readValue(msgFromClient, BroadcastRequest.class);
                        ClientHandler clientHandler = clients.get(request.getSender());
                        if (clientHandler == null) {
                            sendMessage("Клиент с логином [" + request.getSender() + "] не найден");
                        } else {
                            for (String client : clients.keySet()) {
                                if (!client.equals(request.getSender())) {
                                    clients.get(client).sendMessage(request.getMessage());
                                }
                            }
                        }
                    } catch (IOException e) {
                        System.err.println("Не удалось прочитать сообщение от клиента [" + clientLogin + "]: " + e.getMessage());
                        sendMessage("Не удалось прочитать сообщение SendMessageRequest: " + e.getMessage());
//                        continue;
                    }
                } else if (DisconnectRequest.TYPE.equals(type)) {
                    clients.remove(clientLogin);
                    String sendMsgRequest = "Клиент [" + clientLogin + "] отключился";
                    System.out.println(sendMsgRequest);

                    for (String client : clients.keySet()) {
                        clients.get(client).sendMessage(sendMsgRequest);
                    }
                    doClose();


                }
            }

        }


        private String createLoginResponse(boolean success) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setConnected(success);
            try {
                return objectMapper.writer().writeValueAsString(loginResponse);

            } catch (JsonProcessingException e) {
                throw new RuntimeException("Не удалось создать loginResponse: " + e.getMessage());
            }
        }

        private void doClose() {
            try {
                in.close();
                out.close();
                client.close();
            } catch (IOException e) {
                System.err.println("Ошибка во время отключения клиента: " + e.getMessage());
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }
}
