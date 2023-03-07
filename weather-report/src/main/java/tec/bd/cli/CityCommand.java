package tec.bd.cli;

import picocli.CommandLine;
import tec.bd.ApplicationContext;

@CommandLine.Command(name = "city", description = "Get weather forecast for city")
public class CityCommand implements Runnable {

    private static ApplicationContext APP_CONTEXT = ApplicationContext.init();

    @CommandLine.Parameters(paramLabel = "<city name>", description = "city name to be resolved")
    private String cityName;

    @Override
    public void run() {

        var weatherService = APP_CONTEXT.getWeatherService();
        var report = weatherService.getByCity(cityName);

        System.out.println("Lugar: " + report.getLocation());
        System.out.println("Temperatura: " + report.getTemperature());
        System.out.println("Temperatura Mínima: " + report.getMinTemperature());
        System.out.println("Temperatura Máxima: " + report.getMaxTemperature());
        System.out.println("Presión: " + report.getPressure());
        System.out.println("Humedad: " + report.getHumidity());
    }
}
