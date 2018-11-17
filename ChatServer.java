

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

final class ChatServer {
    private static int uniqueId = 0;
    private final List<ClientThread> clients = new ArrayList<>();
    private final int port;

    String pattern = "HH:mm:ss";
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    String time = sdf.format(new Date(System.currentTimeMillis())) + " ";
    ArrayList<String> censoring = new ArrayList<>();

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

    /*
     *  > java ChatServer
     *  > java ChatServer portNumber
     *  If the port number is not specified 1500 is used
     */
    public void setCensoring(String link) {
        String line;
        try {
            FileReader fr = new FileReader(link);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                censoring.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String isCensoring(String a){
        String target;
        int lengthDifference;
        String censored;
        String passageFixed = a;
        for (int i = 0; i < censoring.size(); i++) {
            target = censoring.get(i);
            lengthDifference = a.length() - target.length();
            censored = "";
            for (int j = 0; j < lengthDifference; j++) {
                censored += "*";
            }
            for (int j = 0; j < lengthDifference; j++) {
                String checker = a.substring(i, i + target.length());
                if (checker.equals(target)){
                    passageFixed = passageFixed.substring(0, i) +
                            censored + passageFixed.substring(i + target.length());
                }
            }
        }
        return passageFixed;
    }

    public static void main(String[] args) {
        ChatServer server;
        if (args.length == 2) {
            server = new ChatServer(Integer.parseInt(args[0]), args[1]);
            server.setCensoring(args[1]);

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
                    messageCreated = isCensoring(messageCreated);
                    time = sdf.format(new Date(System.currentTimeMillis())) + " ";
                    if (cm.getTypes() == 1) {
                        broadcast(username + ": " + messageCreated);
                        remove(id);
                    } else {
                        broadcast(username + ": " + messageCreated);
                    }
                    if (clients.size() == 0) {
                        return;
                    }

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                // Send message back to the client
            }
        }

        private synchronized void broadcast(String message) {

            writeMessage(time + message);
            System.out.println(time + message);
        }

        public synchronized void remove(int id) {
            clients.remove(id);
        }

        private boolean writeMessage(String msg) {
            try {
                if (socket.isConnected()) {
                    sOutput.writeObject(msg + "\n");
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
}
