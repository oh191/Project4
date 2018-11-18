

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

final class ChatServer {
    private static int uniqueId = 0;
    private final List<ClientThread> clients = new ArrayList<>();
    private final int port;
    String file;
    Scanner s = new Scanner(System.in);
    String pattern = "HH:mm:ss";
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    String time = sdf.format(new Date(System.currentTimeMillis())) + " ";

    ChatFilter cf;

    private ChatServer(int port, String path) {
        this.port = port;
    }

    private ChatServer() {
        port = 1500;
    }

    /*
     * This is what starts the ChatServer.
     * Right now it just creates the socketServer and adds a new ClientThread to a list to be handled
     */
    private void start() {
        System.out.println(time + "Server waiting for Clients on port " + port);
        Socket socket;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                socket = serverSocket.accept();
                Runnable r = new ClientThread(socket, uniqueId++);
                Thread t = new Thread(r);
                clients.add((ClientThread) r);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCF(String path) {
        file = path;
        cf = new ChatFilter(path);
    }

    /*
     *  > java ChatServer
     *  > java ChatServer portNumber
     *  If the port number is not specified 1500 is used
     */

    public static void main(String[] args) {
        ChatServer server;
        Scanner scanner = new Scanner(System.in);
        if (args.length == 2) {
            server = new ChatServer(Integer.parseInt(args[0]), args[1]);
            System.out.println("Banned Words File: " + args[1]);
            System.out.println("Banned Words: ");
            server.setCF(args[1]);
            System.out.println();
        } else {
            server = new ChatServer();
        }
        server.start();
    }


    /*
     * This is a private class inside of the ChatServer
     * A new thread will be created to run this every time a new client connects.
     */
    private final class ClientThread implements Runnable {
        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;
        int id;
        String username;
        ChatMessage cm;

        private void directMessage(String message, String user) {
            for (int i = 0; i < clients.size(); i++) {
                if (user.equals(clients.get(i).username)) {
                    try {
                        sOutput.writeObject(time + username + " -> " + user + ": " + message + "\n");
                        clients.get(i).sOutput.writeObject(time + username + " -> " + user + ":" + message + "\n");
                        System.out.println(time + username + " -> " + user + ": " + message + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private ClientThread(Socket socket, int id) {
            time = sdf.format(new Date(System.currentTimeMillis())) + " ";
            this.id = id;
            this.socket = socket;
            try {
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
                username = (String) sInput.readObject();
                System.out.println(time + username + " just connected.");
                System.out.println(time + "Server waiting for Clients on port " + port);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        /*
         * This is what the client thread actually runs.
         */
        @Override
        public void run() {
            while (true) {
                time = sdf.format(new Date(System.currentTimeMillis())) + " ";
                try {
                    cm = (ChatMessage) sInput.readObject();
                    String messageCreated = cm.getMessage();
                    time = sdf.format(new Date(System.currentTimeMillis())) + " ";
                    if (cm.getRecipient() != null) {
                        directMessage(cm.getMessage(), cm.getRecipient());
                    } else if (cm.getMessage().equals("/list")) {
                        String name = "";
                        for (int i = 0; i < clients.size(); i++) {
                            name += clients.get(i).username + "\n";
                        }
                        sOutput.writeObject(name);
                    } else if (cm.getTypes() == 1) {
                        broadcast(username + ": " + messageCreated);
                        remove(id);
                    } else {
                        broadcast(username + ": " + messageCreated);
                    }
                    if (clients.size() == 0) {
                        System.out.println("Server has closed the connection");
                        break;
                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                // Send message back to the client
            }
        }

        private synchronized void broadcast(String message) {
            String newMessage = cf.filter(message);
            writeMessage(time + newMessage);
            System.out.println(time + newMessage);
        }

        public synchronized void remove(int id) {
            clients.remove(id);
        }

        private boolean writeMessage(String msg) {
            try {
                if (socket.isConnected()) {
                    for (int i = 0; i < clients.size(); i++) {
                        clients.get(i).sOutput.writeObject(msg + "\n");
                    }

                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
