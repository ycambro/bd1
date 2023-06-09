package tec.bd.cli.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Command;

import picocli.CommandLine.Parameters;
import tec.bd.ApplicationContext;

import java.util.concurrent.Callable;

@Command(name = "log", description = "Show you the last activities saved in the log")
public class SeeLog implements Callable<Integer> {

    private final static Logger LOGGER = LoggerFactory.getLogger(SeeLog.class);

    private static ApplicationContext applicationContext = ApplicationContext.init();

    @Parameters(paramLabel = "<amount>", description = "The amount of last actions that you want to see")
    private int amount;

    @Override
    public Integer call() throws Exception {
        try {
            var blocbusterLog = applicationContext.blockbusterLogService.getEntry(amount);
            for (int i = 0; i <= blocbusterLog.size(); i++) {
                System.out.println("Log Id: " + blocbusterLog.get(i).getLogId());
                System.out.println("Table name: " + blocbusterLog.get(i).getTableName());
                System.out.println("Created on: " + blocbusterLog.get(i).getCreatedOn());
                System.out.println("Entry text: " + blocbusterLog.get(i).getEntryText());
            }
            return 0;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return 1;
        }
    }
}
