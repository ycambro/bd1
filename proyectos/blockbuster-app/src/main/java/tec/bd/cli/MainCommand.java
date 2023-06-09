package tec.bd.cli;

import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;
import tec.bd.cli.movies.*;
import tec.bd.cli.clients.*;
import tec.bd.cli.categories.*;
import tec.bd.cli.rentals.*;
import tec.bd.cli.reviews.*;
import tec.bd.cli.log.*;

@Command(
        name = "Blockbuster App",
        subcommands = {
                CreateMovieCommand.class,
                DeleteMovieCommand.class,
                GetMovieCommand.class,
                UpdateMovieCommand.class,

                CreateCategoryCommand.class,
                DeleteCategoryCommand.class,
                GetCategoryCommand.class,
                UpdateCategoryCommand.class,

                CreateClientCommand.class,
                DeleteClientCommand.class,
                GetClientCommand.class,
                UpdateClientCommand.class,

                CreateRentalsCommand.class,
                DeleteRentalsCommand.class,
                GetRentalsCommand.class,
                UpdateRentalsCommand.class,

                CreateReviewCommand.class,
                DeleteReviewCommand.class,
                GetReviewCommand.class,
                UpdateReviewCommand.class,

                SeeLog.class,

                HelpCommand.class
        },
        description = "Blockbuster Catalog")
public class MainCommand implements Runnable {

    @Override
    public void run() {

    }
}
