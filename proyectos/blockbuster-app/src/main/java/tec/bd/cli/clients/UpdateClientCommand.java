package tec.bd.cli.clients;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import tec.bd.ApplicationContext;
import tec.bd.entities.Client;

import java.util.concurrent.Callable;

@Command(name = "catu", description = "Update category data in catalog ")
public class UpdateClientCommand implements Callable<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateClientCommand.class);

    private static ApplicationContext applicationContext = ApplicationContext.init();

    @Parameters(paramLabel = "<client name>", description = "The client name")
    private String clientName;

    @Parameters(paramLabel = "<client lastname>", description = "The client lastname")
    private String clientLastname;

    @Parameters(paramLabel = "<client email>", description = "The client email")
    private String clientEmail;

    @Parameters(paramLabel = "<client phone number>", defaultValue = "No number", description = "The client phone number")
    private String clientPhoneNumber;

    @Override
    public Integer call() throws Exception {

        var client = new Client();

        try {
            var updatedClient = applicationContext.clientService.updateClient(client);

            System.out.println("Client Id: " + updatedClient.getClientId());
            System.out.println("Client Name: " + updatedClient.getName());
            System.out.println("Client Lastname: " + updatedClient.getLastname());
            System.out.println("Client Email: " + updatedClient.getEmail());
            System.out.println("Client Phone Number: " + updatedClient.getPhoneNumber());
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 1;
        }
    }
}
