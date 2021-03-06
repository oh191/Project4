import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Chatting program
 *
 * @author Junseok Oh
 * @author Javed Hashim
 * @version 11-15-18
 */

final class ChatClient {
    private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    private Socket socket;

    private final String server;
    private final String username;
    private final int port;
    private boolean isTrue = true;

    private ChatClient(String server, int port, String username) {
        this.server = server;
        this.port = port;
        this.username = username;
    }

    private ChatClient(String username, int port) {
        this.server = "localHost";
        this.port = port;
        this.username = username;
    }

    private ChatClient(String username) {
        this.server = "localHost";
        this.port = 1500;
        this.username = username;
    }

    private ChatClient() {
        username = "Anonymous";
        port = 1500;
        server = "localHost";
    }
    /*
     * This starts the Chat Client
     */
    private boolean start() {
        // Create a socket
        try {
            socket = new Socket(server, port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create your input and output streams
        try {
            sInput = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // This thread will listen from the server for incoming messages

        Runnable r = new ListenFromServer();
        Thread t = new Thread(r);
        t.start();

        // After starting, send the clients username to the server.
        try {
            sOutput.writeObject(username);
        } catch (Exception e) {
            close();
            return false;
        }

        return true;
    }

    private void close(){
        try {
            if (sInput != null)
            sInput.close();
            if (sOutput != null)
            sOutput.close();
            if (socket != null)
            socket.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    /*
     * This method is used to send a ChatMessage Objects to the server
     */
    private void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
     * To start the Client use one of the following command
     * > java ChatClient
     * > java ChatClient username
     * > java ChatClient username portNumber
     * > java ChatClient username portNumber serverAddress
     *
     * If the portNumber is not specified 1500 should be used
     * If the serverAddress is not specified "localHost" should be used
     * If the username is not specified "Anonymous" should be used
     */


    public static void main(String[] args) throws IOException {

        Scanner s = new Scanner(System.in);
        // Get proper arguments and override defaults
        // Create your client and start it
        ChatClient client;

        if (args.length == 3) {
            client = new ChatClient(args[2], Integer.parseInt(args[1]), args[0]);
        } else if (args.length == 2) {
            client = new ChatClient(args[0], Integer.parseInt(args[1]));
        } else if (args.length == 1) {
            client = new ChatClient(args[0]);
        } else {
            client = new ChatClient();
        }
        client.start();

        while (true) {
            String string = s.nextLine();
            if (string.equals("/list")){
               client.sendMessage(new ChatMessage("/list", 0));
            } else if (string.contains("/msg")){
                String[] temp = string.split(" ");
                String username = temp[1];
                String message = "";
                for (int i = 2; i < temp.length; i++) {
                    message += temp[i] + " ";
                }
                message = message.substring(0, message.length() - 1);
                ChatMessage cm = new ChatMessage(message, 0, username);
                client.sendMessage(cm);
            } else if (string.equalsIgnoreCase("/logout")) {
                System.out.println("Server has closed the connection.");
                client.sendMessage(new ChatMessage("logout", 1));
                break;

            } else {
                client.sendMessage(new ChatMessage(string, 0));
                client.sOutput.flush();
            }
        }
    }


    /*
     * This is a private class inside of the ChatClient
     * It will be responsible for listening for messages from the ChatServer.
     * ie: When other clients send messages, the server will relay it to the client.
     */
    private final class ListenFromServer implements Runnable {
        public void run() {
            while (isTrue) {
                try {
                    String msg = (String) sInput.readObject();
                    System.out.print(msg);
                } catch (Exception e) {
                    close();
                    isTrue = false;
                }
            }
        }
    }
}
