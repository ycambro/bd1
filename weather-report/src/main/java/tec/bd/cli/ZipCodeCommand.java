package tec.bd.cli;

import picocli.CommandLine;
import tec.bd.ApplicationContext;

@CommandLine.Command(name = "zip", description = "Get weather forecast by zip code")
public class ZipCodeCommand implements Runnable {

    private static ApplicationContext APP_CONTEXT = ApplicationContext.init();

    @CommandLine.Parameters(paramLabel = "<zip code>", description = "zip code to be resolved")
    private String zipCode;

    @Override
    public void run() {
        var weatherService = APP_CONTEXT.getWeatherService();
        var report = weatherService.getByZipCode(zipCode);

        System.out.println("Lugar: " + report.getLocation());
        System.out.println("Temperatura: " + report.getTemperature());
        System.out.println("Temperatura Mínima: " + report.getMinTemperature());
        System.out.println("Temperatura Máxima: " + report.getMaxTemperature());
        System.out.println("Presión: " + report.getPressure());
        System.out.println("Humedad: " + report.getHumidity());

    }

}
