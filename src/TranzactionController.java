
public class TranzactionController {

    private Tranzaction model;
    private TranzactionView view;

    public TranzactionController(Tranzaction model, TranzactionView view) {
        this.model = model;
        this.view = view;
    }

    public String getSender() {
        return model.getSender();
    }

    public void setSender(String sender) {
        model.setSender(sender);
    }

    public String getRecipient() {
        return model.getRecipient();
    }

    public void setRecipient(String recipient) {
        model.setRecipient(recipient);
    }

    public String getInformation() {
        return model.getInformation();
    }

    public void setInformation(String information) {
        model.setInformation(information);
    }

    public void updateView() {
        view.showTransaction(getSender(), getRecipient(), getInformation());
    }

}
