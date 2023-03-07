package tec.bd.openweather;

import retrofit2.Call;
import tec.bd.weather.model.Report;
import tec.bd.weather.model.ReportType;
import tec.bd.weather.service.WeatherService;

import java.util.Date;
import java.util.Map;

public class OpenWeatherProvider implements WeatherService {

    private final OpenWeatherResource openWeatherResource;
    private final OpenWeatherAPIKeyProvider openWeatherAPIKeyProvider;

    public OpenWeatherProvider(OpenWeatherResource openWeatherResource,
            OpenWeatherAPIKeyProvider openWeatherAPIKeyProvider) {
        this.openWeatherResource = openWeatherResource;
        this.openWeatherAPIKeyProvider = openWeatherAPIKeyProvider;
    }

    @Override
    public Report getByZipCode(String zipCode) {
        try {
            var options = queryStringZipOptions(String.valueOf(zipCode));
            Call<OpenWeatherReport> openWeatherReportCall = this.openWeatherResource.get(options);
            OpenWeatherReport openWeatherReport = openWeatherReportCall.execute().body();
            return mapToReportModel(openWeatherReport, ReportType.BY_ZIPCODE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error when calling open weather remote API");
        }
    }

    @Override
    public Report getByCity(String city) {
        try {
            var options = queryStringCityOptions(String.valueOf(city));
            Call<OpenWeatherReport> openWeatherReportCall = this.openWeatherResource.get(options);
            OpenWeatherReport openWeatherReport = openWeatherReportCall.execute().body();
            return mapToReportModel(openWeatherReport, ReportType.BY_CITY);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error when calling open weather remote API");
        }
    }

    private Report mapToReportModel(OpenWeatherReport openWeatherReport, ReportType reportType) {
        var report = new Report();
        report.setTemperature(openWeatherReport.getMain().getTemp());
        report.setPressure(openWeatherReport.getMain().getPressure());
        report.setHumidity(openWeatherReport.getMain().getHumidity());
        report.setMinTemperature(openWeatherReport.getMain().getTempMin());
        report.setMaxTemperature(openWeatherReport.getMain().getTempMax());
        report.setDate(new Date());
        report.setReportType(reportType);
        report.setLocation(openWeatherReport.getName());

        return report;
    }

    private Map<String, String> queryStringZipOptions(String zipCode) {
        return Map.of("zip", zipCode, "appId", this.openWeatherAPIKeyProvider.getAPIKey());
    }

    private Map<String, String> queryStringCityOptions(String city) {
        return Map.of("q", city, "appId", this.openWeatherAPIKeyProvider.getAPIKey());
    }
}
