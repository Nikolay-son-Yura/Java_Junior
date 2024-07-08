package ru.gb.HomeWork.Lesson5;

public class DisconnectRequest extends AbstractRequest{
    public static final String TYPE="DisconnectRequest";
    private String recipient;

    public DisconnectRequest() {
        setType(TYPE);
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
}
