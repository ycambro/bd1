package tec.bd.cli;

import picocli.CommandLine;

import tec.bd.ApplicationContext;

import tec.bd.weather.WeatherReport;

@CommandLine.Command(name = "imn-zip", description = "Get weather forecast by zip code")
public class IMNZipCodeCommand implements Runnable {

    private static ApplicationContext APP_CONTEXT = ApplicationContext.init();

    @CommandLine.Parameters(paramLabel = "<zip code>", description = "zip code to be resolved")
    private String zipCode;

    @Override
    public void run() {
        var openWeatherService = APP_CONTEXT.openImnProvider;
        var weatherReport = new WeatherReport(openWeatherService);
        var report = weatherReport.getByZipCode(Integer.valueOf(zipCode));
        System.out.println(report.getTemperature());
        System.out.println(report.getTempMin());
        System.out.println(report.getTempMax());
        System.out.println(report.getPressure());
        System.out.println(report.getHumidity());
    }

}