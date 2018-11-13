
import java.io.Serializable;

final class ChatMessage implements Serializable {
    private static final long serialVersionUID = 6898543889087L;
    private String message;
    private int types;

    public ChatMessage(){

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getMessage() {
        return message;
    }

    public int getTypes() {
        return types;
    }

//    public ChatMessage(String message, int types){
//        this.message = message;
//        this.types = types;
//
//  }    // Here is where you should implement the chat message object.
    // Variables, Constructors, Methods, etc.
}
