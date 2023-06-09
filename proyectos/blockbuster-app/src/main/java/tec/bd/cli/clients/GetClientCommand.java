package tec.bd.cli.clients;

import picocli.CommandLine;
import tec.bd.ApplicationContext;
import tec.bd.entities.Client;

@CommandLine.Command(name = "clir", description = "Get category in catalog by id")
public class GetClientCommand implements Runnable {

    private static ApplicationContext applicationContext = ApplicationContext.init();
    @CommandLine.Parameters(paramLabel = "<client id>", defaultValue = "0", description = "The client id")
    private int clientId;

    @Override
    public void run() {
        if (clientId == 0) {
            var clients = applicationContext.clientService.getClients();

            System.out.println("Clients Catalog");
            System.out.println("Id\t Client Name\t Client Lastname\t Email\t Phone Number");
            for (Client cl : clients) {
                System.out.println(cl.getClientId() + "\t " + cl.getName() + "\t " + cl.getLastname() + "\t " + cl.getEmail()+ "\t " + cl.getPhoneNumber());
            }
        } else {
            applicationContext.clientService.getClientById(clientId).ifPresentOrElse((client) -> {
                System.out.println("Client Id: " + client.getClientId());
                System.out.println("Client Name: " + client.getName());
                System.out.println("Client Lastname: " + client.getLastname());
                System.out.println("Client Email: " + client.getEmail());
                System.out.println("Client Phone Number: " + client.getPhoneNumber());
            }, () -> System.out.println("Client id " + clientId + " not found"));
        }
    }
}
