package ru.gb.HomeWork.Lesson5;

public class BroadcastRequest extends AbstractRequest{
    public static final String TYPE = "BroadcastRequest";
    private String sender;
    private String message;

    public BroadcastRequest() {
        setType(TYPE);

    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
