
import java.io.Serializable;

/**
 * Chatting program
 *
 * @author Junseok Oh
 * @author Javed Hashim
 * @version 11-15-18
 */

final class ChatMessage implements Serializable {
    private static final long serialVersionUID = 6898543889087L;
    private String message;
    private String recipient;
    private int types;

    public ChatMessage(String message, int types, String recipient) {
        this.message = message;
        this.types = types;
        this.recipient = recipient;
    }

    public ChatMessage() {

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

    public String getRecipient() {
        return recipient;
    }

    public ChatMessage(String message, int types) {
        this.message = message;
        this.types = types;

    }    // Here is where you should implement the chat message object.
    // Variables, Constructors, Methods, etc.
}
