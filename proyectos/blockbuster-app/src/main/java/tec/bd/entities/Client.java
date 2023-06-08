package tec.bd.entities;

public class Client {
    private int clientId;
    private String clientName;
    private String clientLastname;
    private String clientEmail;
    private String clientPhoneNumber;

    public Client() {

    }

    public Client(int clientId, String clientName, String clientLastname, String clientEmail,
            String clientPhoneNumber) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.clientLastname = clientLastname;
        this.clientEmail = clientEmail;
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public Client(int clientId, String clientName) {
        this.clientId = clientId;
        this.clientName = clientName;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return clientName;
    }

    public void setName(String clientName) {
        this.clientName = clientName;
    }

    public String getLastname() {
        return clientLastname;
    }

    public void setLastname(String clientLastname) {
        this.clientLastname = clientLastname;
    }

    public String getEmail() {
        return clientEmail;
    }

    public void setEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }
}
