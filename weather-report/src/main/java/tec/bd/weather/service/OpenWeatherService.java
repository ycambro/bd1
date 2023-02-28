package tec.bd.weather.service;

import retrofit2.Call;

import tec.bd.weather.Report;

import java.util.Map;

public class OpenWeatherService implements WeatherService {

    private final static String API_KEY = "c559e941a0da745aa0139aef272bf16c";

    private final OpenWeatherResource openWeatherResource;

    public OpenWeatherService(OpenWeatherResource openWeatherResource) {
        this.openWeatherResource = openWeatherResource;
    }

    public float getTemperature(int zipCode) {
        try {
            var options = queryStringZipOptions(String.valueOf(zipCode));
            Call<OpenWeatherReport> openWeatherReportCall = this.openWeatherResource.get(options);
            OpenWeatherReport openWeatherReport = openWeatherReportCall.execute().body();
            return openWeatherReport.getMain().getTemp();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error when calling open weather remote API");
        }
    }

    private Map<String, String> queryStringZipOptions(String zipCode) {
        return Map.of("zip", zipCode, "appId", API_KEY);
    }

    public Report byZipCode(int zipCode) {
        try {
            var options = queryStringZipOptions(String.valueOf(zipCode));
            Call<OpenWeatherReport> openWeatherReportCall = this.openWeatherResource.get(options);
            OpenWeatherReport openWeatherReport = openWeatherReportCall.execute().body();
            var system = openWeatherReport.getMain();
            Report report = new Report();
            report.setTemperature(system.getTemp());
            report.setHumidity(system.getHumidity());
            report.setPressure(system.getPressure());
            report.setTempMax(system.getTempMax());
            report.setTempMin(system.getTempMin());
            return report;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error when calling open weather remote API");
        }
    }

    private Map<String, String> queryStringCityOptions(String cityName) {
        return Map.of("q", cityName, "appId", API_KEY);
    }

    public Report byCity(String city) {
        return new Report();
    }
}
