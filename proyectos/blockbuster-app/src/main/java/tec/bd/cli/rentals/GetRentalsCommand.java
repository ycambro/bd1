package tec.bd.cli.rentals;

import picocli.CommandLine;
import tec.bd.ApplicationContext;
import tec.bd.entities.Rentals;

@CommandLine.Command(name = "loanr", description = "Get rental in catalog by id")
public class GetRentalsCommand implements Runnable {

    private static ApplicationContext applicationContext = ApplicationContext.init();
    @CommandLine.Parameters(paramLabel = "<rental id>", defaultValue = "0", description = "The rental id")
    private int rentalId;

    @Override
    public void run() {
        if (rentalId == 0) {
            var rentals = applicationContext.rentalsService.getRentals();

            System.out.println("Rentals Catalog");
            System.out.println("Id\t Rental Date\t Client Id\t Movie Id");
            for (Rentals re : rentals) {
                System.out.println(re.getRentId() + "\t" + re.getRentalDate() + "\t " + re.getClient().getClientId() 
                    + "\t " + re.getMovie().getMovieId());
            }
        } else {
            applicationContext.rentalsService.getRentalsById(rentalId).ifPresentOrElse((rental) -> {
                System.out.println("Rental Id: " + rental.getRentId());
                System.out.println("Created On: " + rental.getRentalDate());
                System.out.println("Cliend Id: " + rental.getClient().getClientId());
                System.out.println("Movie Id: " + rental.getMovie().getMovieId());
            }, () -> System.out.println("Rental id " + rentalId + " not found"));
        }
    }
}
