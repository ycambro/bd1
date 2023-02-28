package tec.bd.cli;

import picocli.CommandLine;

import tec.bd.ApplicationContext;

import tec.bd.weather.WeatherReport;

@CommandLine.Command(name = "imn-city", description = "Get weather forecast by the city name")
public class IMNCityCommand implements Runnable {

    private static ApplicationContext APP_CONTEXT = ApplicationContext.init();

    @CommandLine.Parameters(paramLabel = "<city name>", description = "city name to be resolved")
    private String city;

    @Override
    public void run() {
        var openWeatherService = APP_CONTEXT.openImnProvider;
        var weatherReport = new WeatherReport(openWeatherService);
        var report = weatherReport.getByCity(city);
        System.out.println(report.getTemperature());
        System.out.println(report.getTempMin());
        System.out.println(report.getTempMax());
        System.out.println(report.getPressure());
        System.out.println(report.getHumidity());
    }

}