public class Tranzaction {

    public Tranzaction(String sender, String recipient, String information) {

        this.sender = sender;
        this.recipient = recipient;
        this.information = information;
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public String getInformation() {
        return information;
    }
    public void setInformation(String information) {
        this.information = information;
    }

    private String sender;
    private String recipient;
    private String information;
}
